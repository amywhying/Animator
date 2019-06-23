package cs3500.animator.model.shape;

import java.awt.Color;

/**
 * An interface to represent a read-only version of a Shape. A IReadOnlyMyShape only has the
 * functionality to return the different values of a shape. A shape be able to represent a height,
 * width, color, x and y position (on a normal coordinate plane), and its enumerated shape type.
 */
public interface IReadOnlyMyShape {

  /**
   * Gets the height of this shape.
   *
   * @return the height of the shape
   */
  int getHeight();

  /**
   * Gets the width of this shape.
   *
   * @return the width of the shape
   */
  int getWidth();

  /**
   * Gets the color of this shape. Uses java's built-in Color implementation.
   *
   * @return the color of the shape
   */
  Color getColor();

  /**
   * Gets the type of this shape.
   *
   * @return an enum of the type of shape
   */
  ShapeType getShapeType();

  /**
   * Gets the x position of this shape. X and Y are on a normal coordinate plane.
   *
   * @return the x position of the shape
   */
  int getX();

  /**
   * Gets the y position of this shape. X and Y are on a normal coordinate plane.
   *
   * @return the y position of the shape
   */
  int getY();

  /**
   * Gets the name of this shape.
   *
   * @return the name of the shape.
   */
  String getName();

  /**
   * Determines whether the shape is equivalent to the given object.
   *
   * @param o represents an object of unknown type to be compared with the shape.
   * @return true if the shape is equivalent to the given object.
   */
  boolean equals(Object o);

  /**
   * Returns the hashCode for the shape.
   * @return the hashCode for the shape.
   */
  int hashCode();
}
