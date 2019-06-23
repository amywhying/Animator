package cs3500.animator.model.shape;

import java.awt.Color;
import java.util.Objects;

/**
 * A class to represent a Triangle shape.
 */
public class Triangle extends MyShapeAbstract {
  // Height is calculated from the top of this triangle straight down to the base of it.

  /**
   * Creates a Triangle shape. The height is calculated from the top most point of the triangle to
   * the base. The width of the triangle is the length of its base side.
   *
   * @param height the height from the top of the triangle to the base
   * @param width  the length of the base of the triangle
   * @param color  the color of the triangle
   * @param x      the x coordinate of the center of the triangle
   * @param y      the y coordinate of the center of the triangle
   * @throws IllegalArgumentException if the width or height are less than 0
   * @throws IllegalArgumentException if the color is null
   */
  public Triangle(int height, int width, Color color, int x, int y, String name)
          throws IllegalArgumentException {
    super(height, width, color, x, y, ShapeType.triangle, name);
  }

  /**
   * Creates a default Triangle with the given name and all values set to 0.
   *
   * @param name the name of this Triangle
   */
  public Triangle(String name) {
    super(name, ShapeType.triangle);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Triangle)) {
      return false;
    }
    Triangle shape = (Triangle) o;

    return this.red == shape.red
            && this.green == shape.green
            && this.blue == shape.blue
            && this.x == shape.x
            && this.y == shape.y
            && this.width == shape.width
            && this.height == shape.height
            && this.shapeType.equals(shape.shapeType)
            && this.name.equals(shape.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.x, this.y, this.width, this.height,
            this.shapeType, this.name);
  }
}
