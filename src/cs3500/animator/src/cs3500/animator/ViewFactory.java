package cs3500.animator;

import cs3500.animator.view.EditVisualView;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;

/**
 * A factor class for creating the correct view based on a given string view type.
 */
public class ViewFactory {

  /**
   * Creates the correct view based on the given view type. "text" = TextView, "svg" = SVGView,
   * "visual" = VisualView, and "edit" = EditVisualView.
   *
   * @param type the type of view to be created
   * @return an IView of the given view type
   * @throws IllegalArgumentException if an invalid view type is given.
   */
  public static IView create(String type) throws IllegalArgumentException {
    switch (type) {
      case "text":
        return new TextView();
      case "svg":
        return new SVGView();
      case "visual":
        return new VisualView();
      case "edit":
        return new EditVisualView();
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }
}
