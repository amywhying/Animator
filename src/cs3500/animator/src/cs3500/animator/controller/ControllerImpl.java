package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;

import cs3500.animator.model.Model;
import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.view.EditVisualView;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFeatures;

/**
 * A class for a controller for a simple animation.
 */
public class ControllerImpl implements IController, IViewFeatures {
  private final IView view;
  private final Model model;
  private Timer timer;
  private int tick = 0;
  private int tickSpeed = 1;
  private int maxEnd;
  private boolean loop = false;

  /**
   * Creates a ControllerImpl with the given non-null view and model.
   *
   * @param view  the view for the controller to pass information to
   * @param model the model for the controller to grab data from
   * @throws IllegalArgumentException if the view or model is null
   */
  public ControllerImpl(IView view, Model model) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.view = view;
    this.model = model;
    this.maxEnd = model.findLastKeyFrameTime();
  }

  @Override
  public void execute() throws IOException {
    this.view.setShapes(model.getAllShapes());
    this.view.setOperations(model.getAllOperations());
    int x = this.model.getX();
    int y = this.model.getY();
    int height = this.model.getHeight();
    int width = this.model.getWidth();
    this.view.setBoundaries(x, y, width, height);
    if (view.isVisualView()) {
      timer = new Timer((int) ((1.0 / tickSpeed) * 1000), new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          List<IReadOnlyMyShape> shapesToRender = model.getShapesAtTick(tick++);
          view.render(shapesToRender);
        }
      });
      timer.start();
    } else if (view.isEditView()) {
      this.view.addListener(this);
      this.timer = new Timer((int) ((1.0 / tickSpeed) * 1000), new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (tick >= maxEnd) {
            timer.stop();
            tick = 0;
            if (loop) {
              timer.start();
            }
          }
          List<IReadOnlyMyShape> shapesToRender = model.getShapesAtTickKeyFrame(tick++);
          view.render(shapesToRender);
        }
      });
    } else {
      this.view.outputsAnimation();
    }
  }

  @Override
  public void setSpeed(int speed) {
    this.tickSpeed = speed;
    this.view.setTickSpeed(speed);
  }

  @Override
  public void setOutput(String output) {
    this.view.setOutput(output);
  }

  @Override
  public void paused() {
    timer.stop();
  }

  @Override
  public void play() {
    if (tick == 0) {
      timer.start();
    }
  }

  @Override
  public void rewind() {
    tick = 0;
    timer.start();
  }

  @Override
  public void changeSpeed(int value) {
    timer.setDelay((int) ((1.0 / value) * 1000));
  }

  @Override
  public void addShape(String name, boolean rect) {
    if (rect) {
      this.model.declareShape(name, "rectangle");
    } else {
      this.model.declareShape(name, "ellipse");
    }
    ((EditVisualView) view).setJListShapes(model.getAllShapes());
  }

  @Override
  public void removeShape() {
    String selectedValue = view.getJListShapes().getSelectedValue();
    Scanner scan = new Scanner(selectedValue);
    String name = scan.next();
    model.removeShape(name);
    ((EditVisualView) view).setJListShapes(model.getAllShapes());
  }

  @Override
  public void loop(boolean value) {
    loop = value;
    if (value && tick > maxEnd) {
      timer.stop();
      tick = 0;
    }
  }

  @Override
  public void resume() {
    if (tick > 0 && !timer.isRunning()) {
      timer.start();
    }
  }

  @Override
  public void addKeyFrame(String name, String time) {
    this.model.insertKeyFrame(name, Integer.parseInt(time));
    this.model.sortingKf();
    ((EditVisualView) view).setJListKeyFrames(this.model.getKeyFrames(), name);
  }

  @Override
  public void removeKeyFrame() {
    String selectedKf = view.getKeyFrames().getSelectedValue();
    Scanner scan = new Scanner(selectedKf);
    String shape = scan.next();
    String name = scan.next();
    String t = scan.next();
    int time = scan.nextInt();
    model.removeKeyFrame(name, time);
    ((EditVisualView) view).setJListKeyFrames(this.model.getKeyFrames(), name);
  }

  @Override
  public void editKeyFrame(String position, String dimensions, int red, int green, int blue) {
    String selectedKf = view.getKeyFrames().getSelectedValue();
    Scanner scan = new Scanner(selectedKf);
    String shape = scan.next();
    String name = scan.next();
    String t = scan.next();
    int time = scan.nextInt();
    scan = new Scanner(position);
    int x = scan.nextInt();
    int y = scan.nextInt();
    scan = new Scanner(dimensions);
    int width = scan.nextInt();
    int height = scan.nextInt();
    this.model.editKeyFrame(name, time, x, y, width, height, red, green, blue);
    this.model.sortingKf();
    ((EditVisualView) view).setJListKeyFrames(this.model.getKeyFrames(), name);
  }

  @Override
  public List<IReadOnlyMyShape> getModelShapes() {
    return this.model.getAllShapes();
  }

  @Override
  public List<IKeyFrame> getModelKeyFrames() {
    return this.model.getKeyFrames();
  }
}
