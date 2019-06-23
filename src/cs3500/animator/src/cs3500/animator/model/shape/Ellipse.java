package cs3500.animator.model.shape;

import java.awt.Color;
import java.util.Objects;

/**
 * A class to represent an Oval shape. The height and width of the oval represent the diameter.
 */
public class Ellipse extends MyShapeAbstract {

  /**
   * Creates an Oval shape. The height and width of the oval are twice the given radius.
   *
   * @param vertRadius  the vertical radius
   * @param horizRadius the horizontal radius
   * @param color       the color of the oval
   * @param x           the x position of the center
   * @param y           the y position of the center
   * @throws IllegalArgumentException if the width or height are less than 0
   * @throws IllegalArgumentException if the color is null
   */
  public Ellipse(int vertRadius, int horizRadius, Color color, int x, int y, String name)
          throws IllegalArgumentException {
    super(2 * vertRadius, 2 * horizRadius, color, x, y, ShapeType.ellipse, name);
  }

  /**
   * Creates a default Ellipse with the given name and all values set to 0.
   *
   * @param name the name of this Ellipse
   */
  public Ellipse(String name) {
    super(name, ShapeType.ellipse);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Ellipse)) {
      return false;
    }
    Ellipse shape = (Ellipse) o;

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
