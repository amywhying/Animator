package cs3500.animator.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * A view to show the animation through a pop-up window using Java Swing.
 */
public class VisualView extends JFrame implements IView {

  public DrawingPanel panel;
  JScrollPane scrollBar;

  private int x;
  private int y;
  private int width;
  private int height;
  private int tickSpeed = 1;

  /**
   * Creates a VisualView.
   */
  public VisualView() {
    super();
    this.panel = new DrawingPanel();
    this.scrollBar = new JScrollPane(this.panel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(scrollBar);
    setVisible(true);
    pack();
  }

  @Override
  public void outputsAnimation() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not output to a file or System.out");
  }

  @Override
  public void setOutput(String output) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a specified output");
  }

  @Override
  public void setTickSpeed(int speed) throws IllegalArgumentException {
    if (speed < 1) {
      throw new IllegalArgumentException("Tick speed must be at least 1 tick per second");
    }
    this.tickSpeed = speed;
  }

  /**
   * This method does nothing, since the visual view does not need to initialize shapes. It receives
   * its shapes through render().
   *
   * @param shapes the shapes to be used in the animation
   */
  @Override
  public void setShapes(List<IReadOnlyMyShape> shapes) {
    return;
  }

  /**
   * This method does nothing, since this view does not need to initialize operations. It receives
   * the shapes it needs to animate through render(), which handles applying the operations to the
   * shapes.
   *
   * @param ops the operations on shapes to be used in the animation
   */
  @Override
  public void setOperations(List<Operation> ops) {
    return;
  }

  @Override
  public void setBoundaries(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.panel.setMinimumSize(this.getMinimumSize());
    this.panel.setPreferredSize(this.getPreferredSize());
    this.setSize(this.getPreferredSize().width, this.getPreferredSize().height);
  }

  @Override
  public void render(List<IReadOnlyMyShape> shapesToRender) {
    this.panel.draw(shapesToRender);
  }

  @Override
  public boolean isVisualView() {
    return true;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.width, this.height);
  }

  @Override
  public Dimension getMinimumSize() {
    return new Dimension(this.x, this.y);
  }

  @Override
  public List<Operation> getOperations() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a list of operations");
  }

  @Override
  public List<IReadOnlyMyShape> getShapes() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a list of shapes");
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
  public int getTickSpeed() {
    return this.tickSpeed;
  }

  @Override
  public JList<String> getJListShapes() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a JList of shapes");
  }

  @Override
  public JList<String> getKeyFrames() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a JList of keyframes");
  }

  @Override
  public void addListener(IViewFeatures listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not need a listener");
  }

  @Override
  public void setJListShapes(List<IReadOnlyMyShape> shapes) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a JList of shapes");
  }

  @Override
  public void setJListKeyFrames(List<IKeyFrame> keyframes, String name)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Visual view does not have a JList of key frames");
  }

  @Override
  public boolean isEditView() {
    return false;
  }
}
