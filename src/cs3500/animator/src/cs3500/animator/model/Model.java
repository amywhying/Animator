package cs3500.animator.model;

import java.util.List;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An interface to represent the model of a simple animation. The model keeps track of all created
 * shapes, all operations to be applied to shapes, the leftmost x and y values of the animation, and
 * the width and height of the animation. Each operation acts on a single shape over a set interval,
 * and gradually changes one value of the shape over the interval. The model should be able to get a
 * list of read-only versions of all shapes, get a specific shape by its name, get a list of all
 * operations, get the x, y, width, and height values, and get a list of read-only versions of all
 * shapes at a given tick. Shapes and operations are added through the Model builder.
 */
public interface Model {

  /**
   * Returns a read-only version of the shape with the given name.
   *
   * @param name the name of the shape to retrieve
   * @return a read only version of ths shape with the given name
   * @throws IllegalArgumentException if no shape with that name exists
   */
  IReadOnlyMyShape getShape(String name) throws IllegalArgumentException;

  /**
   * Returns a list of read-only shapes as they existed at the beginning of the animation.
   *
   * @return a list of shapes at the beginning of the animation
   */
  List<IReadOnlyMyShape> getAllShapes();

  /**
   * Gets a list of all operations of the shapes.
   *
   * @return a list of all operations
   */
  List<Operation> getAllOperations();

  /**
   * Gets the leftmost x value of the animation.
   *
   * @return the leftmost x value
   */
  int getX();

  /**
   * Gets the leftmost y value of the animation.
   *
   * @return the leftmost y value
   */
  int getY();

  /**
   * Gets the total width of the animation.
   *
   * @return the width of the animation
   */
  int getWidth();

  /**
   * Gets the total height of the animation.
   *
   * @return the height of the animation
   */
  int getHeight();

  /**
   * Returns a list of read only versions of all shapes at the given tick.
   *
   * @return a list of all shapes at the tick
   */
  List<IReadOnlyMyShape> getShapesAtTick(int i);


  List<IReadOnlyMyShape> getShapesAtTickKeyFrame(int tick);

  /**
   * Inserts a KeyFrame for the given shape name at the given time. The inserted KeyFrame will not
   * change the shape at that time. To edit the inserted KeyFrame and change the shape, use
   * editKeyFrame after inserting a KeyFrame.
   * <p>
   * If the inserted KeyFrame is the only KeyFrame for a shape, a shape will not animate unless
   * there is at least 1 other KeyFrame at a different time. A shape needs at least 2 KeyFrames to
   * animate.
   * </p>
   * <p>
   * If the inserted KeyFrame is before the first KeyFrame, the shape's animation now starts at the
   * inserted KeyFrame.
   * </p>
   * <p>
   * If the inserted KeyFrame is after the last KeyFrame, the shape's animation now ends at the
   * inserted KeyFrame.
   * </p>
   *
   * @param name the name of the shape to add a KeyFrame to
   * @param t    the time to add the KeyFrame
   * @throws IllegalArgumentException if no shape with that name exists
   * @throws IllegalArgumentException if the KeyFrame time is less than 0
   */
  void insertKeyFrame(String name, int t) throws IllegalArgumentException;
  //TODO: decide what should happen if the inserted KeyFrame time already has a KeyFrame at that
  // time
  // Maybe don't do anything. Don't override it because if the point of inserting a KeyFrame is
  // that you can edit it later, then there is no point in overriding a KeyFrame that already
  // exists. You can just edit the already existing one

  /**
   * Edit a KeyFrame for the given shape name at the given time. This allows you to edit the
   * animation of shapes after inserting a KeyFrame.
   *
   * @param name the name of the shape's KeyFrame's to edit
   * @param t    the time of the KeyFrame to edit
   * @param x    the x value the shape should be at that time
   * @param y    the y value the shape should be at that time
   * @param w    the width the shape should be at that time
   * @param h    the height value the shape should be at that time
   * @param r    the red color value the shape should be at that time
   * @param g    the green color value the shape should be at that time
   * @param b    the blue color value the shape should be at that time
   * @throws IllegalArgumentException if no shape with that name exists
   * @throws IllegalArgumentException if no KeyFrame for the given shape at that time exists
   */
  void editKeyFrame(String name, int t, int x, int y, int w, int h, int r, int g, int b)
          throws IllegalArgumentException;

  /**
   * Removes a KeyFrame for the given shape name at the given time. When a KeyFrame is removed, the
   * shape will now animate between the previous and next KeyFrame.
   * <p>
   * If the KeyFrame to be removed is the first KeyFrame of a shape, the shape's animation will now
   * start at the next KeyFrame's time.
   * </p>
   * <p>
   * If the KeyFrame to be removed is the last KeyFrame of a shape, the shape's animation will now
   * stop at the previous KeyFrame's time.
   * </p>
   *
   * @param name the name of the shape to remove the KeyFrame
   * @param t    the time of the KeyFrame to remove
   * @throws IllegalArgumentException if no shape with that name exists
   * @throws IllegalArgumentException if no KeyFrame at that time for the given shape exists
   */
  void removeKeyFrame(String name, int t) throws IllegalArgumentException;

  /**
   * Gets the list of keyframes from the model.
   *
   * @return the list of keyframes from the model.
   */
  List<IKeyFrame> getKeyFrames();

  /**
   * Finds and gets the last keyframe time from the model.
   *
   * @return the last keyframe time from the model.
   */
  int findLastKeyFrameTime();

  /**
   * Finds and gets the first keyframe time from the model.
   *
   * @return the first keyframe time from the model.
   */
  int findFirstKeyFrameTime();

  /**
   * Modifies the shape list of the model by removing the shape of the given name.
   *
   * @param name represents the name of the shape to be removed.
   * @throws IllegalArgumentException if a shape of the given name cannot be found.
   */
  void removeShape(String name) throws IllegalArgumentException;

  /**
   * Modifies the shape list of the model by adding a new shape of the given name and type.
   *
   * @param name represents the name of the shape to be added into the shape list.
   * @param type represents the type of the shape to be added into the shape list.
   */
  void declareShape(String name, String type);

  /**
   * Modifies the keyframe list of the model by sorting it according to keyframe's time in ascending
   * order.
   */
  void sortingKf();

}