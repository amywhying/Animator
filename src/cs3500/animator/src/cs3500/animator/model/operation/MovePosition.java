package cs3500.animator.model.operation;

import java.util.Objects;

import cs3500.animator.model.shape.MyShape;

/**
 * An Operation function object to change the position of a shape over time.
 */
public class MovePosition extends OperationAbstract {
  private final int x;
  private final int y;

  /**
   * Creates a MovePosition operation.
   *
   * @param start the start time of the operation
   * @param end   the end time of the operation
   * @param shape the shape this operation acts on
   * @param x     the amount to change the x value of the shape
   * @param y     the amount to change the y value of the shape
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public MovePosition(int start, int end, MyShape shape, int x, int y)
          throws IllegalArgumentException {
    super(start, end, shape);
    this.x = x;
    this.y = y;
  }

  /**
   * Creates a MovePosition operation.
   *
   * @param start  the start time of the operation
   * @param end    the end time of the operation
   * @param shape  the shape this operation acts on
   * @param states the state changes of the shape over the Operation's interval
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public MovePosition(int start, int end, MyShape shape, int[] states)
          throws IllegalArgumentException {
    super(start, end, shape);
    this.states = states;
    this.x = states[1] - states[0];
    this.y = states[3] - states[2];
  }

  @Override
  public void execute() {
    double tickDiff = this.end - this.start;
    double incrementX = this.x / tickDiff;
    double incrementY = this.y / tickDiff;
    this.shape.changePosition(incrementX, incrementY);
  }

  @Override
  public boolean sameOp(Operation operation) {
    return operation.isMovePosition();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AdjustHeight)) {
      return false;
    }
    MovePosition command = (MovePosition) o;

    return this.x == command.x && this.y == command.y
            && this.start == command.start
            && this.end == command.end
            && this.shape.getName().equals(command.shape.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, x, y, shape.getName());
  }

  @Override
  public boolean isMovePosition() {
    return true;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public int[] getStates() {
    return this.states;
  }
}
