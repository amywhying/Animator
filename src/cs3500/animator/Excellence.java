package cs3500.animator;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.controller.IController;
import cs3500.animator.model.Model;
import cs3500.animator.model.ModelImpl;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;

/**
 * The main class and method to run the simple animation.
 */
public final class Excellence {

  /**
   * The main method for running the simple animation. It takes in command line arguments for
   * specifying the type of animation.
   * <p>
   * To specify the view type, use "-view" as part of the command line arguments followed by either
   * "svg", "text", or "visual" to create a SVG, Text, or Visual view. A type of view must be given
   * or an error will be shown.
   * </p>
   * <p>
   * To specify the input file for the animation to parse, use "-in" followed by the file name/file
   * path. An input file must be given. If the input file is invalid or cannot be read from, an
   * error will be shown.
   * </p>
   * <p>
   * Optionally, to specify a tick speed (in ticks per second) for the animation, use "-speed"
   * followed by a positive integer at least 1. If no tick speed is specified, the default is 1 tick
   * per second. If the given tick speed is less than 1, an error will be shown. If a use specifies
   * a tick speed for a view that doesn't require a tick speed (like a text view) , an error will be
   * shown.
   * </p>
   * <p>
   * Optionally, to specify the file to output the animation to, use "-out" followed by the output
   * file name/file path. If a user specified an output file for a view that doesn't support
   * outputting to a file, an error will be shown. If a view requires an output file, but none is
   * given, the default will be System.out. If a specified output file is invalid or cannot be
   * written to, an error will be shown.
   * </p>
   * <p>
   * Any exceptions or errors that are encountered when running the animation will be displayed
   * using a pop-up window with JOptionPane.
   * </p>
   *
   * @param args the user's command line arguments
   */
  public static void main(String[] args) {
    String viewType = findViewType(args);
    IView view;
    try {
      view = ViewFactory.create(viewType);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Alert!", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
      return;
    }

    String inputFile = findInputFile(args);
    Readable readableFile;
    try {
      readableFile = new FileReader(inputFile);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Alert!", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
      return;
    }

    AnimationBuilder builder = new ModelImpl.Builder();
    AnimationReader reader = new AnimationReader();
    Model model = (Model) AnimationReader.parseFile(readableFile, builder);
    IController controller = new ControllerImpl(view, model);
    String outputFile = findOutputFile(args);
    if (outputFile != null) {
      controller.setOutput(outputFile);
    }
    Integer tickSpeed = findTickSpeed(args);
    if (tickSpeed != null) {
      controller.setSpeed(tickSpeed);
    }

    try {
      controller.execute();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Alert!", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
  }

  private static String findViewType(String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("-view")) {
        return args[i + 1];
      }
    }
    throw new IllegalArgumentException("View type must be given");
  }

  private static String findInputFile(String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("-in")) {
        return args[i + 1];
      }
    }
    throw new IllegalArgumentException("Input file must be given");
  }

  private static String findOutputFile(String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("-out")) {
        return args[i + 1];
      }
    }
    return null;
  }

  private static Integer findTickSpeed(String[] args) {
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("-speed")) {
        int speed;
        try {
          speed = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException e) {
          throw e;
        }
        return speed;
      }
    }
    return null;
  }
}