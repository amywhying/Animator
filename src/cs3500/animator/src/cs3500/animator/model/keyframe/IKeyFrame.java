package cs3500.animator.model.keyframe;

import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An interface that represents a keyframe with a shape, name, time, position, dimension, and color
 * values.
 */
public interface IKeyFrame {

  /**
   * Gets the shape of the keyframe.
   *
   * @return an IReadOnlyMyShape type of shape from the keyframe.
   */
  IReadOnlyMyShape getShape();

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape of the keyframe.
   */
  String getName();

  /**
   * Gets the time of the keyframe.
   *
   * @return the time of the keyframe.
   */
  int getTime();

  /**
   * Gets the x position of the keyframe. X and Y are on a normal coordinate plane.
   *
   * @return the x position of the keyframe.
   */
  int getX();

  /**
   * Gets the y position of the keyframe. X and Y are on a normal coordinate plane.
   *
   * @return the y position of the keyframe.
   */
  int getY();

  /**
   * Gets the width of the keyframe.
   *
   * @return the width of the keyframe.
   */
  int getWidth();

  /**
   * Gets the height of the keyframe.
   *
   * @return the height of the keyframe.
   */
  int getHeight();

  /**
   * Gets the red color value of the keyframe.
   *
   * @return the red color value of the keyframe.
   */
  int getRed();

  /**
   * Gets the green color value of the keyframe.
   *
   * @return the  green color value of the keyframe.
   */
  int getGreen();

  /**
   * Gets the blue color value of the keyframe.
   *
   * @return the blue color value of the keyframe.
   */
  int getBlue();

  /**
   * Determines if the keyframe is equivalent to the given object.
   *
   * @param o represents another object of unknown type.
   * @return true if the keyframe is equivalent to the given object.
   */
  boolean equals(Object o);

  /**
   * Returns the hashCode of the keyframe.
   *
   * @return the hashCode of the keyframe.
   */
  int hashCode();
}
