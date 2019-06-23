package cs3500.animator.model.shape;

import java.awt.Color;

/**
 * An abstract class for a shape. The default minimum height and width for a shape is 0.
 */
abstract class MyShapeAbstract implements MyShape {
  protected final String name;
  protected double red;
  protected double green;
  protected double blue;
  protected double x;
  protected double y;
  protected double width;
  protected double height;
  protected ShapeType shapeType;

  /**
   * Creates a MyShapeAbstract and sets the values of a MyShapeAbstract to the given values.
   *
   * @param height the height of this shape
   * @param width  the width of this shape
   * @param color  the color of this shape
   * @param x      the x coordinate of this shape
   * @param y      the y coordinate of this shape
   * @param st     the enumerated type of this shape
   * @throws IllegalArgumentException if the width or height are less than 0
   * @throws IllegalArgumentException if the color is null
   */
  protected MyShapeAbstract(int height, int width, Color color, int x, int y, ShapeType st,
                            String name)
          throws IllegalArgumentException {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Width and height cannot be less than 0");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null.");
    }
    this.height = height;
    this.width = width;
    this.x = x;
    this.y = y;
    this.red = color.getRed();
    this.green = color.getGreen();
    this.blue = color.getBlue();
    this.shapeType = st;
    this.name = name;
  }

  /**
   * Creates a default MyShapeAbstract with all values equal to 0, except for the given name and
   * enumerated ShapeType.
   *
   * @param name the name of this shape
   * @param st   the enumerated ShapeType of this shape
   */
  protected MyShapeAbstract(String name, ShapeType st) {
    this.name = name;
    this.width = 0;
    this.height = 0;
    this.x = 0;
    this.y = 0;
    this.red = 0;
    this.green = 0;
    this.blue = 0;
    this.shapeType = st;
  }


  @Override
  public void changeHeight(double height) {
    if (this.height + height < 0) {
      this.height = 0;
    }
    this.height += height;
  }

  @Override
  public void changeWidth(double width) {
    if (this.width + width < 0) {
      this.width = 0;
    }
    this.width += width;
  }

  @Override
  public void changeColor(double red, double green, double blue) throws IllegalArgumentException {
    double checkRed = Math.round(this.red + red);
    double checkGreen = Math.round(this.green + green);
    double checkBlue = Math.round(this.blue + blue);
    if (checkRed < 0 || checkRed > 255) {
      throw new IllegalArgumentException("Invalid red value.");
    }
    if (checkGreen < 0 || checkGreen > 255) {
      throw new IllegalArgumentException("Invalid green value.");
    }
    if (checkBlue < 0 || checkBlue > 255) {
      throw new IllegalArgumentException("Invalid blue value.");
    }
    this.red = this.red + red;
    this.green = this.green + green;
    this.blue = this.blue + blue;
  }

  @Override
  public void changePosition(double x, double y) {
    double newX = this.x + x;
    double newY = this.y + y;
    this.x = newX;
    this.y = newY;
  }

  @Override
  public int getHeight() {
    return (int) Math.round(this.height);
  }

  @Override
  public int getWidth() {
    return (int) Math.round(this.width);
  }

  @Override
  public Color getColor() {
    int red = (int) Math.round(this.red);
    int green = (int) Math.round(this.green);
    int blue = (int) Math.round(this.blue);
    return new Color(red, green, blue);
  }

  @Override
  public int getX() {
    return (int) Math.round(this.x);
  }

  @Override
  public int getY() {
    return (int) Math.round(this.y);
  }

  @Override
  public ShapeType getShapeType() {
    return this.shapeType;
  }

  @Override
  public String getName() {
    return this.name;
  }
}
