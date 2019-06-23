package cs3500.animator.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JList;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.model.shape.ShapeType;

/**
 * A view to create SVG/XML text representing the animation. This wil be either outputted to a
 * text/svg file or outputted through System.out.
 */
public class SVGView implements IView {

  private List<IReadOnlyMyShape> shapes;
  private List<Operation> operations;
  private int x;
  private int y;
  private int width;
  private int height;
  private boolean usingOutputFile = false;
  private String outputFile = null;
  private int tickSpeed = 1;

  @Override
  public void outputsAnimation() throws IOException {
    String output = this.makeSVGOutput();
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
  public void setTickSpeed(int speed) throws IllegalArgumentException {
    if (speed < 1) {
      throw new IllegalArgumentException("Tick speed must be at least 1 tick per second");
    }
    this.tickSpeed = speed;
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
  public void setBoundaries(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Override
  public void render(List<IReadOnlyMyShape> shapesToRender) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("SVG view can not render shapes");
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
  public int getTickSpeed() {
    return this.tickSpeed;
  }

  @Override
  public JList<String> getJListShapes() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("SVG view does not have a JList of shapes");
  }

  @Override
  public JList<String> getKeyFrames() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("SVG view does not have a JList of keyframes");
  }

  @Override
  public void addListener(IViewFeatures listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("SVG view does not need a listener");
  }

  @Override
  public void setJListShapes(List<IReadOnlyMyShape> shapes) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("SVG view does not have a JList of shapes");
  }

  @Override
  public void setJListKeyFrames(List<IKeyFrame> keyframes, String name)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("SVG view does not have a JList of keyframes");
  }

  @Override
  public boolean isEditView() {
    return false;
  }

  private String makeSVGOutput() {
    StringBuilder output = new StringBuilder();
    output.append(addBeginningWrapper());
    for (IReadOnlyMyShape s : this.shapes) {
      output.append(this.createShapeSVG(s));
    }
    output.append(addEndingWrapper());
    return output.toString();
  }

  private String addBeginningWrapper() {
    StringBuilder beginning = new StringBuilder();
    beginning.append(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" "
                    + "xmlns=\"http://www.w3.org/2000/svg\">\n", this.width + this.x,
            this.height + this.y));
    return beginning.toString();
  }

  private String addEndingWrapper() {
    StringBuilder ending = new StringBuilder();
    ending.append("</svg>\n");
    return ending.toString();
  }

  private String createShapeSVG(IReadOnlyMyShape s) {
    StringBuilder shapeSVG = new StringBuilder();
    if (s.getShapeType().equals(ShapeType.rectangle)) {
      shapeSVG.append(this.createShapesSVG(s, "rect", "x", "y", "width", "height"));
    } else {
      shapeSVG.append(this.createShapesSVG(s, "ellipse", "cx", "cy", "rx", "ry"));
    }
    for (Operation o : this.operations) {
      if (o.getName().equals(s.getName())) {
        String shapeType = s.getShapeType().toString();
        shapeSVG.append(this.createOperationSVG(o, shapeType));
      }
    }
    if (s.getShapeType().equals(ShapeType.rectangle)) {
      shapeSVG.append("</rect>\n");
    } else {
      shapeSVG.append("</ellipse>\n");
    }
    return shapeSVG.toString();
  }

  private String createShapesSVG(IReadOnlyMyShape s, String type, String x, String y, String width,
                                 String height) {
    StringBuilder shapeSVG = new StringBuilder();
    shapeSVG.append(String.format("<%s id=\"%s\" %s=\"%d\" %s=\"%d\" %s=\"%d\" %s=\"%d\" "
                    + "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
            type, s.getName(), x, s.getX(), y, s.getY(), width, s.getWidth(), height,
            s.getHeight(), s.getColor().getRed(), s.getColor().getGreen(), s.getColor().getBlue()));
    return shapeSVG.toString();
  }

  private String createOperationSVG(Operation o, String shapeType) {
    StringBuilder opSVG = new StringBuilder();
    if (o.isAdjustHeight()) {
      String type = "height";
      if (shapeType.equals("ellipse")) {
        type = "ry";
      }
      opSVG.append(this.createAnimateSVG(o, type, o.getStates()[6], o.getStates()[7]));
    } else if (o.isAdjustWidth()) {
      String type = "width";
      if (shapeType.equals("ellipse")) {
        type = "rx";
      }
      opSVG.append(this.createAnimateSVG(o, type, o.getStates()[4], o.getStates()[5]));
    } else if (o.isMovePosition()) {
      int xFrom = o.getStates()[0];
      int xTo = o.getStates()[1];
      int yFrom = o.getStates()[2];
      int yTo = o.getStates()[3];
      if (xFrom - xTo != 0) {
        String type = "x";
        if (shapeType.equals("ellipse")) {
          type = "cx";
        }
        opSVG.append(this.createAnimateSVG(o, type, xFrom, xTo));
      }
      if (yFrom - yTo != 0) {
        String type = "y";
        if (shapeType.equals("ellipse")) {
          type = "cy";
        }
        opSVG.append(this.createAnimateSVG(o, type, yFrom, yTo));
      }
    } else {
      opSVG.append(this.createChangeColorSVG(o, shapeType));
    }
    return opSVG.toString();
  }

  private String createChangeColorSVG(Operation o, String type) {
    StringBuilder changeColorSVG = new StringBuilder();
    int redFrom = o.getStates()[8];
    int redTo = o.getStates()[9];
    int greenFrom = o.getStates()[10];
    int greenTo = o.getStates()[11];
    int blueFrom = o.getStates()[12];
    int blueTo = o.getStates()[13];
    double realSpeed = 100 / this.tickSpeed;
    double begin = (double) o.getStart() * realSpeed;
    double duration = (double) (o.getEnd() - o.getStart()) * realSpeed;
    String fromColor = String.format("rbg(%s,%s,%s)", redFrom, greenFrom, blueFrom);
    String toColor = String.format("rbg(%s,%s,%s)", redTo, greenTo, blueTo);
    changeColorSVG.append(String.format("<animate attributeType=\"xml\" begin=\"%fms\" "
                    + "dur=\"%fms\" "
                    + "attributeName=\"%s\" from=\"%s\" to=\"%s\" fill=\"freeze\" />\n",
            begin, duration, "fill", fromColor, toColor));
    return changeColorSVG.toString();
  }

  private String createAnimateSVG(Operation o, String type, int from, int to) {
    StringBuilder animateSVG = new StringBuilder();
    double realSpeed = 100 / this.tickSpeed;
    double begin = (double) o.getStart() * realSpeed;
    double duration = (double) (o.getEnd() - o.getStart()) * realSpeed;
    animateSVG.append(String.format("<animate attributeType=\"xml\" begin=\"%fms\" dur=\"%fms\" "
                    + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            begin, duration, type, from, to));
    return animateSVG.toString();
  }
}