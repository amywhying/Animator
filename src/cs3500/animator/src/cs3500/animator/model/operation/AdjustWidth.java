package cs3500.animator.model.operation;

import java.util.Objects;

import cs3500.animator.model.shape.MyShape;

/**
 * An Operation function object to change the width of a shape over time.
 */
public class AdjustWidth extends OperationAbstract {
  private final int width;

  /**
   * Creates a AdjustWidth operation.
   *
   * @param start the start time of the operation
   * @param end   the end time of the operation
   * @param shape the shape this operation acts on
   * @param width the amount to change the width of the shape
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public AdjustWidth(int start, int end, MyShape shape, int width) {
    super(start, end, shape);
    this.width = width;
  }

  /**
   * Creates a AdjustWidth operation.
   *
   * @param start  the start time of the operation
   * @param end    the end time of the operation
   * @param shape  the shape this operation acts on
   * @param states the state changes of the shape over the Operation's interval
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public AdjustWidth(int start, int end, MyShape shape, int[] states) {
    super(start, end, shape);
    this.states = states;
    this.width = states[5] - states[4];
  }

  @Override
  public void execute() {
    double tickDiff = this.end - this.start;
    double increment = this.width / tickDiff;
    this.shape.changeWidth(increment);
  }

  @Override
  public boolean sameOp(Operation operation) {
    return operation.isAdjustWidth();
  }

  @Override
  public int[] getStates() {
    return this.states;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AdjustWidth)) {
      return false;
    }
    AdjustWidth command = (AdjustWidth) o;

    return this.width == command.width
            && this.start == command.start
            && this.end == command.end
            && this.shape.getName().equals(command.shape.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, width, shape.getName());
  }


  @Override
  public boolean isAdjustWidth() {
    return true;
  }

  public int getWidth() {
    return this.width;
  }
}
