package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An interface to represent the functionality that EditVisualView supports, which modifies the
 * animation.
 */
public interface IViewFeatures {

  /**
   * Pauses the animation.
   */
  void paused();

  /**
   * Plays the animation.
   */
  void play();

  /**
   * Rewinds the animation back to the start.
   */
  void rewind();

  /**
   * Changes the tick speed of the animation to the given value.
   *
   * @param value the tick speed to be set to.
   */
  void changeSpeed(int value);

  /**
   * Adds a shape of the given name and shape type to the animation.
   *
   * @param name represents the name of the shape.
   * @param rect true if the shape type is a rectangle, else an ellipse.
   */
  void addShape(String name, boolean rect);

  /**
   * Removes the shape of the animation.
   */
  void removeShape();

  /**
   * Allows the animation to loop.
   *
   * @param value true if the animation is allowed to loop.
   */
  void loop(boolean value);

  /**
   * Resumes the animation if it was paused.
   */
  void resume();

  /**
   * Adds a keyframe of the given name and time to the animation.
   *
   * @param name represents the name of the shape that the keyframe works on.
   * @param time represents the time when this keyframe takes action.
   */
  void addKeyFrame(String name, String time);

  /**
   * Removes a keyframe from the animation.
   */
  void removeKeyFrame();

  /**
   * Modifies a keyframe's value to the given values.
   *
   * @param position   represents the (x,y) position of a shape.
   * @param dimensions represents the width and height of a shape.
   * @param red        represents the red color value of a shape.
   * @param green      represents the green color value of a shape.
   * @param blue       represents the blue color value of a shape.
   */
  void editKeyFrame(String position, String dimensions, int red, int green, int blue);

  /**
   * Gets a list of IReadOnlyMyShapes from the model.
   *
   * @return a list of IReadOnlyMyShapes from the model.
   */
  List<IReadOnlyMyShape> getModelShapes();

  /**
   * Gets a list of IKeyFrame from the model.
   *
   * @return a list of IKeyFrame from the model.
   */
  List<IKeyFrame> getModelKeyFrames();
}
