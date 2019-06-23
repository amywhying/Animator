package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An interface to represent a drawing panel, which draws the Visual View shapes using Java Swing.
 */
public interface IDrawingPanel {

  /**
   * Draw the given list of shapes using Java swing.
   *
   * @param shapes the shapes to draw
   */
  void draw(List<IReadOnlyMyShape> shapes);
}
