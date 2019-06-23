package cs3500.animator.model.operation;

import java.util.Objects;

import cs3500.animator.model.shape.MyShape;

/**
 * An Operation function object to change the height of a shape over an interval.
 */
public class AdjustHeight extends OperationAbstract {
  private final int height;

  /**
   * Creates a AdjustHeight operation.
   *
   * @param start  the start time of the operation
   * @param end    the end time of the operation
   * @param shape  the shape this operation acts on
   * @param height the amount to change the height of the shape
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public AdjustHeight(int start, int end, MyShape shape, int height)
          throws IllegalArgumentException {
    super(start, end, shape);
    this.height = height;
  }

  /**
   * Creates a AdjustHeight operation.
   *
   * @param start  the start time of the operation
   * @param end    the end time of the operation
   * @param shape  the shape this operation acts on
   * @param states the state changes of the shape over the Operation's interval
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public AdjustHeight(int start, int end, MyShape shape, int[] states)
          throws IllegalArgumentException {
    super(start, end, shape);
    this.states = states;
    this.height = states[7] - states[6];
  }

  @Override
  public void execute() {
    double tickDiff = this.end - this.start;
    double increment = this.height / tickDiff;
    this.shape.changeHeight(increment);
  }

  @Override
  public boolean sameOp(Operation o) {
    return o.isAdjustHeight();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AdjustHeight)) {
      return false;
    }
    AdjustHeight command = (AdjustHeight) o;

    return this.height == command.height
            && this.start == command.start
            && this.end == command.end
            && this.shape.getName().equals(command.shape.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, height, shape.getName());
  }


  @Override
  public boolean isAdjustHeight() {
    return true;
  }

  public int getHeight() {
    return this.height;
  }

  @Override
  public int[] getStates() {
    return this.states;
  }

}
