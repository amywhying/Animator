package cs3500.animator.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * A view to output the animation as a text representation through System.out or a specified text
 * file.
 */
public class TextView implements IView {

  private List<IReadOnlyMyShape> shapes;
  private List<Operation> operations;
  private int x;
  private int y;
  private int width;
  private int height;
  private boolean usingOutputFile = false;
  private String outputFile = null;

  @Override
  public void outputsAnimation() throws IOException {
    String output = this.animationChart();
    if (this.usingOutputFile) {
      FileWriter file = new FileWriter(this.outputFile);
      BufferedWriter writer = new BufferedWriter(file);
      writer.write(output);
      writer.close();
    } else {
      System.out.println(output);
    }
  }

  @Override
  public void setOutput(String output) {
    this.usingOutputFile = true;
    this.outputFile = output;
  }

  @Override
  public void setTickSpeed(int speed) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not have a tick speed");
  }

  @Override
  public void setShapes(List<IReadOnlyMyShape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void setOperations(List<Operation> ops) {
    this.operations = ops;
  }

  @Override
  public void render(List<IReadOnlyMyShape> shapesToRender) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not render shapes");
  }

  @Override
  public boolean isVisualView() {
    return false;
  }

  @Override
  public List<Operation> getOperations() {
    return this.operations;
  }

  @Override
  public List<IReadOnlyMyShape> getShapes() {
    return this.shapes;
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
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getTickSpeed() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not have a tick speed");
  }

  @Override
  public JList<String> getJListShapes() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not have a JList of shapes");
  }

  @Override
  public JList<String> getKeyFrames() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not have a JList of keyframes");
  }

  @Override
  public void addListener(IViewFeatures listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not need a listener");
  }

  @Override
  public void setJListShapes(List<IReadOnlyMyShape> shapes) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not have a JList of shapes");
  }

  @Override
  public void setJListKeyFrames(List<IKeyFrame> keyframes, String name)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Text view does not have a JList of key frames");
  }

  @Override
  public boolean isEditView() {
    return false;
  }

  @Override
  public void setBoundaries(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  private String animationChart() {
    StringBuilder base = new StringBuilder();
    StringBuilder acc = new StringBuilder();
    // Sort the operations by their starting time.
    List<Operation> sortedOps = this.sortOperations(this.operations);
    base.append(String.format("canvas %d %d %d %d\n", this.x, this.y, this.width, this.height));

    for (IReadOnlyMyShape shape : this.shapes) {
      base.append("shape\t" + shape.getName() + "\t" + shape.getShapeType().toString()
              + "\n");
      for (Operation o : sortedOps) {

        // if the operation corresponds to the current shape,
        // Append to the accumulator
        if (shape.getName().equals(o.getName())) {
          acc.append("motion\t" + shape.getName() + "\t" + o.getStart() + "\t"
                  + shape.getX() + "\t"
                  + shape.getY() + "\t"
                  + shape.getWidth() + "\t"
                  + shape.getHeight() + "\t"
                  + shape.getColor().getRed() + "\t"
                  + shape.getColor().getGreen() + "\t"
                  + shape.getColor().getBlue()
                  + "\t\t\t\t");
          for (int i = o.getStart(); i < o.getEnd(); i++) {
            o.execute();
          }
          acc.append(o.getEnd() + "\t"
                  + shape.getX() + "\t"
                  + shape.getY() + "\t"
                  + shape.getWidth() + "\t"
                  + shape.getHeight() + "\t"
                  + shape.getColor().getRed() + "\t"
                  + shape.getColor().getGreen() + "\t"
                  + shape.getColor().getBlue() + "\n");
        }
        // Before the each outer loop ends, append the accumulator to the builder
        // Reset the accumulator back to empty
      }
      base.append(acc + "\n");
      acc = new StringBuilder();
    }
    return base.toString();
  }

  /**
   * Sort this list of operation's by the operation starting time.
   */
  private List<Operation> sortOperations(List<Operation> ops) {
    List<Operation> sortedOps = new ArrayList<Operation>();
    sortedOps.addAll(ops);
    Comparator<Operation> byStartTime = Comparator.comparingInt(Operation::getStart);
    sortedOps.sort(byStartTime);
    return sortedOps;
  }
}