package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import javax.swing.JList;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An interface to represent Views to be used in the simple animation. A view should be able to
 * create their animation, set the shapes it uses, set the operations it uses, and set the
 * boundaries of the animation. Optionally, it should be able to set its output file, tick speed of
 * the animation, and render/display a list of shapes at a given tick. If the optional functionality
 * is not supported, an UnsupportedOperationException should be thrown.
 */
public interface IView {

  /**
   * Create and output this view's animation to the correct output (either a file or System.out). If
   * the view does not output to either a file or System.out, an UnsupportedOperationException is
   * thrown.
   *
   * @throws IOException                   if the (optionally) given output file is invalid or
   *                                       cannot be written to
   * @throws UnsupportedOperationException if the view does not output to a file or System.out
   */
  void outputsAnimation() throws IOException, UnsupportedOperationException;

  /**
   * Sets this view's output file to the given string file name/path. If no output is specified, the
   * default is System.out. If this functionality is not supported by the view, an
   * UnsupportedOperationException is thrown.
   *
   * @param output the output file's name/filepath
   * @throws UnsupportedOperationException if the view does not support specifying an output path
   */
  void setOutput(String output) throws UnsupportedOperationException;

  /**
   * Sets this view's tick (in ticks per second) to the given string file name/path. If no tick
   * speed is specified, the default is 1 tick per second. If this functionality is not supported by
   * the view, an UnsupportedOperationException is thrown.
   *
   * @param speed the speed in ticks per second of the animation
   * @throws UnsupportedOperationException if the view does not support specifying a tick speed
   * @throws IllegalArgumentException      if the tick speed is less than 1
   */
  void setTickSpeed(int speed) throws UnsupportedOperationException, IllegalArgumentException;

  /**
   * Sets the view's shapes used in the animation to the given shapes. The shapes should be as they
   * exist at the beginning of the animation.
   *
   * @param shapes the shapes to be used in the animation
   */
  void setShapes(List<IReadOnlyMyShape> shapes);

  /**
   * Sets the view's operations to the given operations to be used in the animation.
   *
   * @param ops the operations on shapes to be used in the animation
   */
  void setOperations(List<Operation> ops);

  /**
   * Set the boundaries of the animation.
   *
   * @param x      the leftmost x value
   * @param y      the leftmost y value
   * @param width  the width of the animation
   * @param height the height of the animation
   */
  void setBoundaries(int x, int y, int width, int height);

  /**
   * Renders the given shapes in the view and displays them. If a view outputs to a file or System
   * .out, this method will not be used and an UnsupportedOperationException will be thrown.
   *
   * @param shapesToRender the shapes to animate
   * @throws UnsupportedOperationException if the view does not use this method (if it outputs to a
   *                                       file or System.out)
   */
  void render(List<IReadOnlyMyShape> shapesToRender) throws UnsupportedOperationException;

  /**
   * Is this view a type of view that doesn't output to a file or System.out.? This is used to
   * determine which methods the controller uses when handling the view.
   *
   * @return true if this view is a view that doesn't use an output file or System.out
   */
  boolean isVisualView();

  /**
   * Gets the list of operation of this view.
   *
   * @return the list of operation of the view.
   */
  List<Operation> getOperations();

  /**
   * Gets the list of IReadOnlyMyShapes of this view.
   *
   * @return the list of IReadOnlyMyShape of the view.
   */
  List<IReadOnlyMyShape> getShapes();

  /**
   * Gets the leftmost x value of the view.
   *
   * @return the leftmost x value
   */
  int getX();

  /**
   * Gets the leftmost y value of the view.
   *
   * @return the leftmost y value
   */
  int getY();

  /**
   * Gets the total width of the view.
   *
   * @return the width of the view.
   */
  int getWidth();

  /**
   * Gets the total height of the view.
   *
   * @return the height of the view.
   */
  int getHeight();


  /**
   * Gets the tick speed of the view.
   *
   * @return the tick speed of the view.
   */
  int getTickSpeed();

  /**
   * Gets the JList shapes of the view.
   *
   * @return the JList shapes of the view.
   * @throws UnsupportedOperationException if the view does not use this method.
   */
  JList<String> getJListShapes() throws UnsupportedOperationException;

  /**
   * Gets the JList keyframes of the view.
   *
   * @return the JList keyframes of the view.
   * @throws UnsupportedOperationException if the view does not use this method.
   */
  JList<String> getKeyFrames() throws UnsupportedOperationException;

  /**
   * Adds the given listener to the view.
   *
   * @param listener represents the listener that listens the view and modifies its animation.
   * @throws UnsupportedOperationException if the view does not use this method.
   */
  void addListener(IViewFeatures listener) throws UnsupportedOperationException;

  /**
   * Sets the given shapes to the JList shapes of the view.
   *
   * @param shapes represents the JList shapes to be set to the view.
   * @throws UnsupportedOperationException if the view does not use this method.
   */
  void setJListShapes(List<IReadOnlyMyShape> shapes) throws UnsupportedOperationException;

  /**
   * Sets the given keyframes to the JList keyframes of the view.
   *
   * @param keyframes represents the keyframes to be set to the view.
   * @param name      represents a shape's name.
   * @throws UnsupportedOperationException if the view does not use this method.
   */
  void setJListKeyFrames(List<IKeyFrame> keyframes, String name)
          throws UnsupportedOperationException;

  /**
   * Determines whether the view is EditVisualView.
   *
   * @return true if the view is EditVisualView.
   */
  boolean isEditView();
}
