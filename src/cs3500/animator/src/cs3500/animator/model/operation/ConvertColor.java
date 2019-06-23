package cs3500.animator.model.operation;

import java.util.Objects;

import cs3500.animator.model.shape.MyShape;

/**
 * An Operation function object to change the color of a shape over time.
 */
public class ConvertColor extends OperationAbstract {
  private final int changeR;
  private final int changeG;
  private final int changeB;

  /**
   * Creates a ConvertColor operation.
   *
   * @param start   the start time of the operation
   * @param end     the end time of the operation
   * @param shape   the shape this operation acts on
   * @param changeR the amount to change the red color value
   * @param changeG the amount to change the green color value
   * @param changeB the amount to change the blue color value
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public ConvertColor(int start, int end, MyShape shape, int changeR, int changeG, int changeB)
          throws IllegalArgumentException {
    super(start, end, shape);
    this.changeR = changeR;
    this.changeG = changeG;
    this.changeB = changeB;
  }

  /**
   * Creates a ConvertColor operation.
   *
   * @param start  the start time of the operation
   * @param end    the end time of the operation
   * @param shape  the shape this operation acts on
   * @param states the state changes of the shape over the Operation's interval
   * @throws IllegalArgumentException if the start time is greater than the end time
   * @throws IllegalArgumentException if the start time is less than 0
   * @throws IllegalArgumentException if the shape is null
   */
  public ConvertColor(int start, int end, MyShape shape, int[] states)
          throws IllegalArgumentException {
    super(start, end, shape);
    this.states = states;
    this.changeR = states[9] - states[8];
    this.changeG = states[11] - states[10];
    this.changeB = states[13] - states[12];
  }

  @Override
  public void execute() {
    double tickDiff = this.end - this.start;
    double incrementR = this.changeR / tickDiff;
    double incrementG = this.changeG / tickDiff;
    double incrementB = this.changeB / tickDiff;
    this.shape.changeColor(incrementR, incrementG, incrementB);
  }

  @Override
  public boolean sameOp(Operation operation) {
    return operation.isConvertColor();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConvertColor)) {
      return false;
    }
    ConvertColor command = (ConvertColor) o;

    return this.changeR == command.changeR && this.changeG == command.changeG
            && this.changeB == command.changeB
            && this.start == command.start
            && this.end == command.end
            && this.shape.getName().equals(command.shape.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, changeR, changeG, changeB, shape.getName());
  }

  @Override
  public boolean isConvertColor() {
    return true;
  }

  public int getRed() {
    return this.changeR;
  }

  public int getBlue() {
    return this.changeB;
  }

  public int getGreen() {
    return this.changeG;
  }

  @Override
  public int[] getStates() {
    return this.states;
  }


}
