package cs3500.animator.model.shape;

import java.awt.Color;
import java.util.Objects;

/**
 * A class to represent a rectangle shape.
 */
public class Rectangle extends MyShapeAbstract {

  /**
   * Creates a Rectangle.
   *
   * @param height the height of the rectangle
   * @param width  the width of the rectangle
   * @param color  the color of the rectangle
   * @param x      the x coordinate of the center of the rectangle
   * @param y      the y coordinate of the center of the rectangle
   * @throws IllegalArgumentException if the width or height are less than 0
   * @throws IllegalArgumentException if the color is null
   */
  public Rectangle(int height, int width, Color color, int x, int y, String name)
          throws IllegalArgumentException {
    super(height, width, color, x, y, ShapeType.rectangle, name);
  }

  /**
   * Creates a default Rectangle with the given name and all values set to 0.
   *
   * @param name the name of this Rectangle
   */
  public Rectangle(String name) {
    super(name, ShapeType.rectangle);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Rectangle)) {
      return false;
    }
    Rectangle shape = (Rectangle) o;

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
