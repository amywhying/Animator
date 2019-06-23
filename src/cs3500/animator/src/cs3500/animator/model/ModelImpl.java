package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.keyframe.KeyFrame;
import cs3500.animator.model.operation.AdjustHeight;
import cs3500.animator.model.operation.AdjustWidth;
import cs3500.animator.model.operation.ConvertColor;
import cs3500.animator.model.operation.MovePosition;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.model.shape.MyShape;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Triangle;
import cs3500.animator.util.AnimationBuilder;

/**
 * Creates an implementation of the model. Shapes and operations are kept as lists.
 */
public final class ModelImpl implements Model {
  private final List<MyShape> shapes;
  private final List<Operation> operations;
  private final List<IKeyFrame> keyFrames;
  private final int x;
  private final int y;
  private final int width;
  private final int height;

  /**
   * Creates a ModelImpl with the given list of shapes, list of operations, leftmost x and y, width,
   * and height.
   *
   * @param shapes the shapes of this model
   * @param ops    the operations of this model
   * @param x      the leftmost x value
   * @param y      the leftmost y value
   * @param width  the with of the animation
   * @param height the height of the animation
   * @throws IllegalArgumentException if the list of shapes or operations is null
   */
  private ModelImpl(List<MyShape> shapes, List<Operation> ops, List<IKeyFrame> keyFrames,
                    int x, int y, int width, int height)
          throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("List of shapes cannot be null");
    }
    if (ops == null) {
      throw new IllegalArgumentException("List of operations cannot be null");
    }
    if (keyFrames == null) {
      throw new IllegalArgumentException("List of key frames cannot be null");
    }
    this.shapes = shapes;
    this.operations = ops;
    this.keyFrames = keyFrames;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  private static MyShape findShape(String name, List<MyShape> shapes) {
    for (MyShape s : shapes) {
      if (name.equals(s.getName())) {
        return s;
      }
    }
    return null;
  }

  @Override
  public IReadOnlyMyShape getShape(String name) throws IllegalArgumentException {
    for (MyShape shape : this.shapes) {
      if (shape.getName().equals(name)) {
        return shape;
      }
    }
    throw new IllegalArgumentException("No shape exists with that name");
  }

  @Override
  public List<IReadOnlyMyShape> getAllShapes() {
    List<IReadOnlyMyShape> shapes = new ArrayList<IReadOnlyMyShape>();
    shapes.addAll(this.shapes);
    return shapes;
  }

  @Override
  public List<Operation> getAllOperations() {
    List<Operation> ops = new ArrayList<Operation>();
    ops.addAll(this.operations);
    return ops;
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
  public List<IReadOnlyMyShape> getShapesAtTick(int tick) {
    List<IReadOnlyMyShape> shapesToReturn = new ArrayList<>();
    List<MyShape> shapesCopy = this.copyShapes(this.shapes);

    for (Operation op : this.operations) {
      if (op.containsTick(tick)) {
        double frac1 = ((double) (op.getEnd() - tick))
                / (op.getEnd() - op.getStart());
        double frac2 = ((double) (tick - op.getStart()))
                / (op.getEnd() - op.getStart());
        for (MyShape shape : shapesCopy) {
          if (op.getName().equals(shape.getName())) {
            int newHeight = (int) (op.getStates()[6] * frac1 + op.getStates()[7] * frac2);
            int newWidth = (int) (op.getStates()[4] * frac1 + op.getStates()[5] * frac2);

            int newX = (int) (op.getStates()[0] * frac1 + op.getStates()[1] * frac2);
            int newY = (int) (op.getStates()[2] * frac1 + op.getStates()[3] * frac2);

            int newR = (int) (op.getStates()[8] * frac1 + op.getStates()[9] * frac2);
            int newG = (int) (op.getStates()[10] * frac1 + op.getStates()[11] * frac2);
            int newB = (int) (op.getStates()[12] * frac1 + op.getStates()[13] * frac2);
            System.out.println(newG);

            shape.changeHeight(newHeight - shape.getHeight());
            shape.changeWidth(newWidth - shape.getWidth());
            shape.changePosition(newX - shape.getX(), newY - shape.getY());
            shape.changeColor(newR - shape.getColor().getRed(),
                    newG - shape.getColor().getGreen(),
                    newB - shape.getColor().getBlue());
            shapesToReturn.add(shape);
          }
        }
      }
    }
    return shapesToReturn;
  }

  @Override
  public List<IReadOnlyMyShape> getShapesAtTickKeyFrame(int tick) {
    List<IReadOnlyMyShape> shapesToReturn = new ArrayList<>();
    List<MyShape> shapesCopy = this.copyShapes(this.shapes);

    for (MyShape s : shapesCopy) {
      List<IKeyFrame> shapeKeyFrames = this.findKeyFramesForShape(s.getName(), this.keyFrames);
      List<IKeyFrame> sortedShapeKeyFrames = this.sortKeyFrames(shapeKeyFrames);
      int size = sortedShapeKeyFrames.size();
      if (size >= 2) {
        IKeyFrame first = sortedShapeKeyFrames.get(0);
        IKeyFrame last = sortedShapeKeyFrames.get(size - 1);
        if (!(tick < first.getTime() || tick > last.getTime())) {
          for (int i = 0; i < sortedShapeKeyFrames.size(); i++) {
            IKeyFrame kf = sortedShapeKeyFrames.get(i);
            if (tick >= kf.getTime() && i + 1 < sortedShapeKeyFrames.size()) {
              IKeyFrame nextKf = sortedShapeKeyFrames.get(i + 1);
              int startTime = kf.getTime();
              int endTime = nextKf.getTime();
              double width = this.getKeyFrameTweening(startTime, endTime, tick, kf.getWidth(),
                      nextKf.getWidth());
              double height = this.getKeyFrameTweening(startTime, endTime, tick, kf.getHeight(),
                      nextKf.getHeight());
              double x = this.getKeyFrameTweening(startTime, endTime, tick, kf.getX(), nextKf.getX());
              double y = this.getKeyFrameTweening(startTime, endTime, tick, kf.getY(), nextKf.getY());
              double red = this.getKeyFrameTweening(startTime, endTime, tick, kf.getRed(), nextKf.getRed());
              double green = this.getKeyFrameTweening(startTime, endTime, tick, kf.getGreen(), nextKf.getGreen());
              double blue = this.getKeyFrameTweening(startTime, endTime, tick, kf.getBlue(), nextKf.getBlue());
              s.changeWidth(width - s.getWidth());
              s.changeHeight(height - s.getHeight());
              s.changePosition(x - s.getX(), y - s.getY());
              Color c = s.getColor();
              try {
                s.changeColor(red - c.getRed(), green - c.getGreen(), blue - c.getBlue());
              } catch (IllegalArgumentException e) {
                // do nothing
              } finally {
                shapesToReturn.add(s);
              }
            }
          }
        }
      }
    }
    return shapesToReturn;
  }

  @Override
  public void insertKeyFrame(String name, int t) throws IllegalArgumentException {
    MyShape shape = findShape(name, this.shapes);
    if (shape == null) {
      throw new IllegalArgumentException("No shape exists with that name");
    }
    if (t < 0) {
      throw new IllegalArgumentException("Time must start at least at tick 0");
    }
    if (this.findKeyFrame(name, t) != null) {
      // if a KeyFrame with that name and time already exists, do nothing
      return;
    }

    List<IKeyFrame> shapeKeyFrames = this.findKeyFramesForShape(name, this.keyFrames);
    List<IKeyFrame> sortedShapeKeyFrames = this.sortKeyFrames(shapeKeyFrames);
    int size = sortedShapeKeyFrames.size();
    if (size == 0) {
      // there are no existing KeyFrames for this shape
      this.keyFrames.add(new KeyFrame(shape, t));
    } else if (size == 1) {
      // there is only one existing KeyFrame for this shape
      this.keyFrames.add(new KeyFrame(shape, t, sortedShapeKeyFrames.get(0))); // copies KF values
    } else {
      // there are at least 2 existing KeyFrames for this shape
      IKeyFrame start = sortedShapeKeyFrames.get(0);
      IKeyFrame end = sortedShapeKeyFrames.get(sortedShapeKeyFrames.size() - 1);
      if (t < start.getTime()) {
        this.keyFrames.add(new KeyFrame(shape, t, start));
      } else if (t > end.getTime()) {
        this.keyFrames.add(new KeyFrame(shape, t, end));
      } else {
        List<IReadOnlyMyShape> shapesAt = this.getShapesAtTickKeyFrame(t);
        IReadOnlyMyShape currentShape = this.findReadOnlyShape(name, shapesAt);
        Color c = currentShape.getColor();
        IKeyFrame newKF = new KeyFrame(shape, t, currentShape.getX(), currentShape.getY(),
                currentShape.getWidth(), currentShape.getHeight(), c.getRed(), c.getGreen(),
                c.getBlue());
        this.keyFrames.add(newKF);
      }
    }
  }

  @Override
  public void editKeyFrame(String name, int t, int x, int y, int w, int h, int r, int g, int b)
          throws IllegalArgumentException {
    MyShape shape = findShape(name, this.shapes);
    IKeyFrame kf = this.findKeyFrame(name, t);
    if (shape == null) {
      throw new IllegalArgumentException("No shape exists with that name");
    }
    if (kf == null) {
      throw new IllegalArgumentException("No KeyFrame with that shape and time exists");
    }
    int index = this.keyFrames.indexOf(kf);
    IKeyFrame newKeyFrame = new KeyFrame(shape, t, x, y, w, h, r, g, b);
    this.keyFrames.set(index, newKeyFrame);
  }

  @Override
  public void removeKeyFrame(String name, int t) throws IllegalArgumentException {
    if (findShape(name, this.shapes) == null) {
      throw new IllegalArgumentException("No shape exists with that name");
    }
    IKeyFrame kf = this.findKeyFrame(name, t);
    if (kf == null) {
      throw new IllegalArgumentException("No KeyFrame with that shape and time exists");
    }
    this.keyFrames.remove(kf);
  }

  @Override
  public List<IKeyFrame> getKeyFrames() {
    List<IKeyFrame> keyFrames = new ArrayList<>();
    keyFrames.addAll(this.keyFrames);
    return keyFrames;
  }

  @Override
  public int findLastKeyFrameTime() {
    List<IKeyFrame> sortedKeyFrames = this.sortKeyFrames(this.keyFrames);
    if (sortedKeyFrames.size() == 0) {
      return 0;
    } else {
      return sortedKeyFrames.get(sortedKeyFrames.size() - 1).getTime();
    }
  }

  @Override
  public int findFirstKeyFrameTime() {
    List<IKeyFrame> sortedKeyFrames = this.sortKeyFrames(this.keyFrames);
    if (sortedKeyFrames.size() == 0) {
      return 0;
    } else {
      return sortedKeyFrames.get(0).getTime();
    }
  }

  @Override
  public void removeShape(String name) throws IllegalArgumentException {
    for (IReadOnlyMyShape shape : this.shapes) {
      if (shape.getName().equals(name)) {
        this.shapes.remove(shape);
        return;
      }
    }
    throw new IllegalArgumentException("Shape with the given name does not exist");
  }

  @Override
  public void declareShape(String name, String type) {
    if (findShape(name, this.shapes) != null) {
      throw new IllegalArgumentException("A shape with that name already exists");
    }
    switch (type) {
      case "rectangle":
        this.shapes.add(new Rectangle(name));
        break;
      case "ellipse":
        this.shapes.add(new Ellipse(name));
        break;
      case "triangle":
        this.shapes.add(new Triangle(name));
        break;
      default:
        throw new IllegalArgumentException("Given shape type cannot be identified.");
    }
  }

  @Override
  public void sortingKf() {
    Comparator<IKeyFrame> byStartTime = Comparator.comparingInt(IKeyFrame::getTime);
    this.keyFrames.sort(byStartTime);
  }

  private List<MyShape> copyShapes(List<MyShape> shapes) {
    List<MyShape> copiedShapes = new ArrayList<>();
    for (MyShape s : shapes) {
      switch (s.getShapeType()) {
        case ellipse:
          copiedShapes.add(new Ellipse(s.getHeight(), s.getWidth(), s.getColor(), s.getX(),
                  s.getY(), s.getName()));
          break;
        case rectangle:
          copiedShapes.add(new Rectangle(s.getHeight(), s.getWidth(), s.getColor(), s.getX(),
                  s.getY(), s.getName()));
          break;
        case triangle:
          copiedShapes.add(new Triangle(s.getHeight(), s.getWidth(), s.getColor(), s.getX(),
                  s.getY(), s.getName()));
          break;
        default:
          break;
      }
    }
    return copiedShapes;
  }

  private List<IKeyFrame> findKeyFramesForShape(String name, List<IKeyFrame> keyFrames) {
    if (this.getShape(name) == null) {
      throw new IllegalArgumentException("A shape with that name does not exist");
    }
    List<IKeyFrame> foundKeyFrames = new ArrayList<>();
    for (IKeyFrame kf : keyFrames) {
      if (name.equals(kf.getName())) {
        foundKeyFrames.add(kf);
      }
    }
    return foundKeyFrames;
  }

  private int findMaxEndTick() {
    int maxEnd = 0;
    for (Operation op : this.operations) {
      maxEnd = Math.max(op.getEnd(), maxEnd);
    }
    return maxEnd;
  }

  private List<IKeyFrame> sortKeyFrames(List<IKeyFrame> keyFrames) {
    List<IKeyFrame> sortedKeyFrames = new ArrayList<>();
    sortedKeyFrames.addAll(keyFrames);
    Comparator<IKeyFrame> byStartTime = Comparator.comparingInt(IKeyFrame::getTime);
    sortedKeyFrames.sort(byStartTime);
    return sortedKeyFrames;
  }

  private double getKeyFrameTweening(int startTime, int endTime, int tick, int value1, int value2) {
    double timeDiff = endTime - startTime;
    double prevTime = endTime - tick;
    double currentTime = tick - startTime;
    double value = (value1 * (prevTime / timeDiff)) + (value2 * (currentTime / timeDiff));
    return value;
  }

  private IKeyFrame findKeyFrame(String name, int t) {
    for (IKeyFrame kf : this.keyFrames) {
      if (name.equals(kf.getName()) && t == kf.getTime()) {
        return kf;
      }
    }
    return null;
  }

  private IReadOnlyMyShape findReadOnlyShape(String name, List<IReadOnlyMyShape> shapes) {
    for (IReadOnlyMyShape s : shapes) {
      if (name.equals(s.getName())) {
        return s;
      }
    }
    return null;
  }


  /**
   * A builder class to construct a ModelImpl.
   */
  public static final class Builder implements AnimationBuilder<Model> {

    private List<MyShape> shapes = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();
    private List<IKeyFrame> keyFrames = new ArrayList<>();
    private int x;
    private int y;
    private int width;
    private int height;

    @Override
    public ModelImpl build() {
      return new ModelImpl(this.shapes, this.operations, this.keyFrames, x, y, width, height);
    }

    @Override
    public AnimationBuilder<Model> setBounds(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<Model> declareShape(String name, String type)
            throws IllegalArgumentException {
      if (findShape(name, this.shapes) != null) {
        throw new IllegalArgumentException("A shape with that name already exists");
      }
      switch (type) {
        case "rectangle":
          this.shapes.add(new Rectangle(name));
          break;
        case "ellipse":
          this.shapes.add(new Ellipse(name));
          break;
        case "triangle":
          this.shapes.add(new Triangle(name));
          break;
        default:
          throw new IllegalArgumentException("Given shape type cannot be identified.");
      }
      return this;
    }

    @Override
    public AnimationBuilder<Model> addMotion(String name,
                                             int t1, int x1, int y1, int w1, int h1,
                                             int r1, int g1, int b1,
                                             int t2, int x2, int y2, int w2, int h2,
                                             int r2, int g2, int b2)
            throws IllegalArgumentException {
      List<Operation> addedOps = new ArrayList<>();
      int[] states = new int[14];
      states[0] = x1;
      states[1] = x2;
      states[2] = y1;
      states[3] = y2;
      states[4] = w1;
      states[5] = w2;
      states[6] = h1;
      states[7] = h2;
      states[8] = r1;
      states[9] = r2;
      states[10] = g1;
      states[11] = g2;
      states[12] = b1;
      states[13] = b2;
      MyShape shape = findShape(name, this.shapes);
      if (shape == null) {
        throw new IllegalArgumentException("A shape of that name does not exist");
      }
      if (!this.hasExistingMotion(shape)) {
        this.setStartingFields(shape, x1, y1, w1, h1, r1, g1, b1);
      }

      if ((x2 - x1 != 0) || (y2 - y1 != 0)) {
        addedOps.add(new MovePosition(t1, t2, shape, states));
      }
      if (w2 - w1 != 0) {
        addedOps.add(new AdjustWidth(t1, t2, shape, states));
      }
      if (h2 - h1 != 0) {
        addedOps.add(new AdjustHeight(t1, t2, shape, states));
      }
      if ((r2 - r1 != 0) || (g2 - g1 != 0) || (b2 - b1 != 0)) {
        addedOps.add(new ConvertColor(t1, t2, shape, states));
      }
      // if there is no change over the interval, just set a dummy operation that doesn't change
      // anything
      if (addedOps.size() == 0) {
        this.checkOperation(new MovePosition(t1, t2, shape, states));
      }

      for (Operation o : addedOps) {
        this.checkOperation(o);
      }
      this.addKeyframe(name, t1, x1, y1, w1, h1, r1, g1, b1);
      this.addKeyframe(name, t2, x2, y2, w2, h2, r2, g2, b2);
      return this;
    }

    @Override
    public AnimationBuilder<Model> addKeyframe(String name, int t, int x, int y, int w, int h,
                                               int r, int g, int b) {
      MyShape shape = findShape(name, this.shapes);
      if (shape == null) {
        throw new IllegalArgumentException("A shape of that name does not exist");
      }
      if (t < 0) {
        throw new IllegalArgumentException("KeyFrame time must be at least at tick 0");
      }
      IKeyFrame kf = new KeyFrame(shape, t, x, y, w, h, r, g, b);
      this.checkKeyFrame(kf);
      return this;
    }

    private boolean hasExistingMotion(MyShape shape) {
      for (Operation o : this.operations) {
        if (o.getName().equals(shape.getName())) {
          return true;
        }
      }
      return false;
    }

    private void setStartingFields(MyShape shape, int x1, int y1, int w1, int h1,
                                   int r1, int g1, int b1) {
      shape.changeWidth(w1);
      shape.changeHeight(h1);
      shape.changePosition(x1, y1);
      shape.changeColor(r1, g1, b1);
    }

    private void checkOperation(Operation operation) throws IllegalArgumentException {
      int start = operation.getStart();
      int end = operation.getEnd();

      for (int i = 0; i < this.operations.size(); i++) {
        Operation o = this.operations.get(i);

        // if names are the same
        if (o.getName().equals(operation.getName())) {
          // if the ends are the same
          if (o.getStart() == operation.getStart() && o.getEnd() == operation.getEnd()) {
            // if they are the same type of operation throw an exception
            if (o.sameOp(operation)) {
              throw new IllegalArgumentException("Can't add the same type of operation over the "
                      + "same interval on the same shape");
            } else {
              this.operations.add(operation);
              return;
            }
          } else if (o.containsTick(start) || o.containsTick(end)) {
            throw new IllegalArgumentException("Can't have overlapping intervals");
          }
        }
      }
      this.operations.add(operation);
    }

    private void checkKeyFrame(IKeyFrame keyFrame) throws IllegalArgumentException {
      for (int i = 0; i < this.keyFrames.size(); i++) {
        IKeyFrame kf = this.keyFrames.get(i);
        if ((kf.getTime() == keyFrame.getTime()) && kf.getName().equals(keyFrame.getName())) {
          // if you add a keyFrame that already exists, override it
          // this will happen if when converting from operation to KeyFrame because Operations
          // have overlapping starts and ends
          this.keyFrames.set(i, keyFrame);
          return;
        }
      }
      this.keyFrames.add(keyFrame);
    }
  }
}