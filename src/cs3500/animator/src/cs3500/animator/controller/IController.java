package cs3500.animator.controller;

import java.io.IOException;

/**
 * An interface for a controller for the simple animation. A controller should be able to set the
 * optional tick speed and output file for the animation, as well as start the animation.
 */
public interface IController {

  /**
   * Runs and creates the animation through the view. If an output file is previously given but the
   * file is invalid or cannot be written to, an IOException is thrown.
   *
   * @throws IOException if the (optionally) given output file is invalid or cannot be written to
   */
  void execute() throws IOException;

  /**
   * Sets the tick speed of the animation. If no tick speed is given, the default is one tick per
   * second. For views that don't require a tick speed, like the text view, and a tick speed it
   * given, an UnsupportedOperationException is thrown.
   *
   * @param speed the speed of the animation in ticks per second
   * @throws UnsupportedOperationException if a tick speed is given for views that don't require a
   *                                       tick speed
   */
  void setSpeed(int speed) throws UnsupportedOperationException;

  /**
   * Sets the optional output file (represented as a string of the file name) of the view. If no
   * output file is given, the output defaults to System.out. This method does not test if the
   * output is a valid file. If an output file is given for views that don't require an output file,
   * like the visual view, an UnsupportedOperationException is thrown.
   *
   * @param output the file to output to as a string of the file name
   * @throws UnsupportedOperationException if an output file is given for views that don't require
   *                                       an output file
   */
  void setOutput(String output) throws UnsupportedOperationException;
}
