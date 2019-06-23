package cs3500.animator.view;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * A class to draw the shapes from the VisualView and EditVisualView using java swing.
 */
public class DrawingPanel extends JPanel implements IDrawingPanel {
  List<IReadOnlyMyShape> shapes = null;

  /**
   * Creates a DrawingPanel.
   */
  public DrawingPanel() {
    super();
  }

  @Override
  public void paintComponent(Graphics g) throws IllegalArgumentException {
    super.paintComponent(g);
    if (shapes != null) {
      for (IReadOnlyMyShape shape : shapes) {
        g.setColor(shape.getColor());
        switch (shape.getShapeType()) {
          case rectangle:
            g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            break;
          case ellipse:
            g.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            break;
          default:
            throw new IllegalArgumentException("Given shape type cannot be identified.");
        }
      }
    }
  }

  @Override
  public void draw(List<IReadOnlyMyShape> shapes) {
    this.shapes = shapes;
    repaint();
  }
}
