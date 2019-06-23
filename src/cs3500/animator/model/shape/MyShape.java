package cs3500.animator.model.shape;

/**
 * An interface to represent both a read and write version of a shape. MyShape implements all the
 * write methods of a shape, while extending the readable methods.
 */
public interface MyShape extends IReadOnlyMyShape {

  /**
   * Change the height of this shape by increasing it by the given amount. A given negative height
   * would decrease the height of the shape. If the height of this shape is less than the minimum
   * height (defaulted to 0), the height of the shape will be set to the default.
   *
   * @param height the height to change by (increase or decrease)
   */
  void changeHeight(double height);

  /**
   * Change the width of this shape by increasing it by the given amount. A given negative width
   * would decrease the width of the shape. If the width of this shape is less than the minimum
   * width (defaulted to 0), the width of the shape will be set to the default.
   *
   * @param width the width to change by (increase or decrease)
   */
  void changeWidth(double width);

  /**
   * Change the red, green, and blue color values of this shape by the given amounts. A given
   * negative amount would "decrease" the color values. Throws exceptions if the given amounts to
   * change the color values would cause one of the values to become negative or greater than 255.
   *
   * @param red   the amount to change the shape's red color value by
   * @param green the amount to change the shape's green color value by
   * @param blue  the amount to change the shape's blue color value by
   * @throws IllegalArgumentException if the changes would cause any color value to become negative
   *                                  or greater than 255
   */
  void changeColor(double red, double green, double blue);

  /**
   * Change the x and y coordinates (representing the center point) of this shape by the given
   * amounts. The given x and y changes can be negative.
   *
   * @param x the amount to change the shape's x coordinate by
   * @param y the amount to change the shape's y coordinate by
   */
  void changePosition(double x, double y);
}
