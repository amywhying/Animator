package cs3500.animator.model.operation;

import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An interface to represent function objects that act on shapes to change the shape's properties
 * over time. An Operation should be able to keep track of the shape it operates, the time intervals
 * (measured in ticks) that it operates on, as well as the values of the shapes that are changing.
 * An Operation should also be able to apply its operation over its interval on its shape. Its
 * operation must be applied gradually over its interval.
 */
public interface Operation {

  /**
   * Apply this operation to the its shape over the operation's interval.
   */
  void execute();

  /**
   * Does this operation's tick interval contain the given tick.? Returns true if the given tick is
   * greater than or equal to the start and less than the end. Returns false otherwise.
   *
   * @param tick the tick to check
   * @return true if the tick is within this operation's tick interval
   */
  boolean containsTick(int tick);

  /**
   * Returns the starting interval of this operation.
   *
   * @return the start tick
   */
  int getStart();

  /**
   * Returns the ending interval of this operation.
   *
   * @return the end tick
   */
  int getEnd();

  /**
   * Gets the name of the shape that this Operation operates on.
   *
   * @return the name of this operation's shape
   */
  String getName();

  /**
   * Gets a read-only version of the shape that this Operation operates on.
   *
   * @return the shape this Operation operates on
   */
  IReadOnlyMyShape getShape();

  /**
   * Gets the state of the shape as an array of int values of the shape. [0] is the initial x, [1]
   * is final x, [2] is initial y, [3] is final y, [4] is initial width, [5] is final width, [6] is
   * initial height, [7] is final height, [8] is initial red color value, [9] is final red color
   * value, [10] is initial green color values, [11] is final green color value, [12] is initial
   * blue color value, [13] is final blue color value.
   *
   * @return an array of ints of values of the shape
   */
  int[] getStates();

  /**
   * Is the given operation the same type of operation as this one.?
   *
   * @param operation the operation to test similarity
   * @return true if this operation and the given operation are the same type
   */
  boolean sameOp(Operation operation);

  /**
   * Is this operation of the type AdjustHeight.?
   *
   * @return true if this operation adjusts shape's height
   */
  boolean isAdjustHeight();

  /**
   * Is this operation of the type AdjustWidth.?
   *
   * @return true if this operation adjusts shape's width
   */
  boolean isAdjustWidth();

  /**
   * Is this operation of the type ConvertColor.?
   *
   * @return true if this operation adjusts shape's color
   */
  boolean isConvertColor();

  /**
   * Is this operation of the type MovePosition.?
   *
   * @return true if this operation adjusts shape's x and y position
   */
  boolean isMovePosition();
}
