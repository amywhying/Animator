package cs3500.animator.model.keyframe;

import java.awt.Color;
import java.util.Objects;

import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.model.shape.MyShape;

/**
 * A class to represent a KeyFrame, which is the state that a Shape should be in at one tick. Keeps
 * the shape it exists for, the time of the KeyFrame, the x, y, width, height, and Color of the
 * KeyFrame.
 */
public class KeyFrame implements IKeyFrame {

  private MyShape shape;
  private int time;
  private int x;
  private int y;
  private int width;
  private int height;
  private Color color;

  /**
   * Creates a KeyFrame with a shape, time, dimension, RGB color values, x and y position values.
   * All its fields are initialized to the given variables.
   *
   * @param shape  the non-null shape this keyframe acts on.
   * @param time   the time when this keyframe acts onto its shape.
   * @param x      the x position the shape moves to.
   * @param y      the y position the shape moves to.
   * @param width  the width that the shape changes to.
   * @param height the height that the shape changes to.
   * @param red    the red color value that the shape changes to.
   * @param green  the green color value that the shape changes to.
   * @param blue   the blue color value that the shape changes to.
   * @throws IllegalArgumentException if the shape is null.
   * @throws IllegalArgumentException if the time is negative.
   * @throws IllegalArgumentException if the width or height is negative.
   * @throws IllegalArgumentException if any of the color values are out of range.
   */
  public KeyFrame(MyShape shape, int time, int x, int y, int width, int height, int red, int green,
                  int blue) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Shape must be non-null");
    }
    if (time < 0) {
      throw new IllegalArgumentException("Time must start at at least tick 0");
    }
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Width and height must be at least 0");
    }
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("Color values must be at least 0 and no greater than 255");
    }
    this.shape = shape;
    this.time = time;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = new Color(red, green, blue);
  }

  /**
   * Creates a keyframe with a shape that it acts on and the time of when it acts.
   *
   * @param shape the non-null shape this keyframe acts on.
   * @param time  the time when this keyframe acts onto its shape.
   */
  public KeyFrame(MyShape shape, int time) {
    this.shape = shape;
    this.time = time;
    this.x = 0;
    this.y = 0;
    this.width = 0;
    this.height = 0;
    this.color = new Color(0, 0, 0);
  }

  /**
   * Creates a keyframe with a shape that it acts on, the time of when it acts, and another
   * keyframe.
   *
   * @param shape the non-null shape this keyframe acts on.
   * @param time  the time when this keyframe acts onto its shape.
   * @param kf    another keyframe that has the same width, height, color, and position values as
   *              this keyframe.
   */
  public KeyFrame(MyShape shape, int time, IKeyFrame kf) {
    this.shape = shape;
    this.time = time;
    this.x = kf.getX();
    this.y = kf.getY();
    this.width = kf.getWidth();
    this.height = kf.getHeight();
    this.color = new Color(kf.getRed(), kf.getGreen(), kf.getBlue());
  }

  @Override
  public IReadOnlyMyShape getShape() {
    return this.shape;
  }

  @Override
  public String getName() {
    return this.shape.getName();
  }

  @Override
  public int getTime() {
    return this.time;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getRed() {
    return this.color.getRed();
  }

  @Override
  public int getGreen() {
    return this.color.getGreen();
  }

  @Override
  public int getBlue() {
    return this.color.getBlue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KeyFrame)) {
      return false;
    }
    KeyFrame kf = (KeyFrame) o;

    return this.shape.getName().equals(kf.shape.getName())
            && this.time == kf.time
            && this.x == kf.x
            && this.y == kf.y
            && this.width == kf.width
            && this.height == kf.height
            && this.color.equals(kf.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shape.getName(), this.time, this.x, this.y, this.width, this.height,
            this.color);
  }
}
