package cs3500.animator.model.operation;

import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.model.shape.MyShape;

/**
 * A class to represent an Abstract Operation, which is a function object to change one value of a
 * shape over its interval.
 */
abstract class OperationAbstract implements Operation {
  protected final int start;
  protected final int end;
  protected MyShape shape;
  protected int[] states;

  /**
   * Create an AbstractOperation and instantiate its variables. Start and end times are measured in
   * ticks. The start time must be less than or equal to the end time and the start time must be at
   * least 0 ticks.
   *
   * @param start the start time of the operation (in ticks)
   * @param end   the end time of the operation (in ticks)
   * @param shape the non-null shape this operation acts on
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  protected OperationAbstract(int start, int end, MyShape shape) throws IllegalArgumentException {
    if (start > end) {
      throw new IllegalArgumentException("Start time must be less than or equals to ths end time");
    }
    if (start < 0) {
      throw new IllegalArgumentException("Start time must be at least 1");
    }
    if (shape == null) {
      throw new IllegalArgumentException("Shape cannot be null.");
    }
    this.start = start;
    this.end = end;
    this.shape = shape;
  }

  @Override
  public boolean containsTick(int tick) {
    return tick >= this.start
            && tick < this.end;
  }

  @Override
  public int getStart() {
    return start;
  }

  @Override
  public int getEnd() {
    return end;
  }

  @Override
  public String getName() {
    return this.shape.getName();
  }

  @Override
  public IReadOnlyMyShape getShape() {
    return this.shape;
  }

  @Override
  public boolean isAdjustHeight() {
    return false;
  }

  @Override
  public boolean isAdjustWidth() {
    return false;
  }

  @Override
  public boolean isConvertColor() {
    return false;
  }

  @Override
  public boolean isMovePosition() {
    return false;
  }
}

