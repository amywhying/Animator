package cs3500.animator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;

/**
 * An editable visual view class that produces an animation where users can modify it. Its
 * functionality includes play, pause, rewind, resume, change tick speed, modifying shapes and
 * keyframes. This class also has a VisualView as a delegate, so as to use its methods that have
 * same functionality as this class. This visual view also acts as one of the panels of this JFrame,
 * that plays the animation. This Frame is separated into 6 main sections (playPausePanel is used
 * within topMiddlePanel) (from left to right) Top: Shapes, animation, keyframes. Bottom: Options
 * for shapes, options for animation, options for keyframes.
 */
public class EditVisualView extends JFrame implements ActionListener, ListSelectionListener,
        ChangeListener, IView {
  private VisualView delegate;
  int x;
  int y;
  int width;
  int height;
  int tickSpeed;
  boolean createKf;

  private DrawingPanel drawingPanel;

  private JList<String> shapeList;
  private JList<String> keyFrameList;

  private DefaultListModel defaultModelShape;
  private DefaultListModel defaultModelKf;

  JLabel shapeName;
  JLabel shapeNameKf;
  JLabel timeKf;
  JLabel posKf;
  JLabel sizeKf;
  JLabel colorKf;

  JTextArea shapeNameInput;
  JTextArea shapeNameKfInput;
  JTextArea timeKfInput;
  JTextArea posKfInput;
  JTextArea sizeKfInput;

  private JRadioButton rectRadio;
  private JRadioButton loopRadio;
  private JButton selectColor;

  SpinnerNumberModel snm;

  List<IViewFeatures> listeners;

  /**
   * Creates an EditVisualView. Creates the entire animation GUI, which the user can interact with.
   */
  public EditVisualView() {
    super();
    // Title:
    setTitle("Editable Visual View");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    // size
    this.x = 200;
    this.y = 200;
    this.width = 1000;
    this.height = 1000;
    // initialize listeners and visual view
    listeners = new ArrayList<>();
    delegate = new VisualView();

    // The main left panel:
    JPanel mainLeftPanel = new JPanel();
    mainLeftPanel.setLayout(new BoxLayout(mainLeftPanel, BoxLayout.PAGE_AXIS));


    // The main middle panel:
    JPanel mainMidPanel = new JPanel();
    mainMidPanel.setLayout(new BoxLayout(mainMidPanel, BoxLayout.PAGE_AXIS));


    // The main right panel:
    JPanel mainRightPanel = new JPanel();
    mainRightPanel.setLayout(new BoxLayout(mainRightPanel, BoxLayout.PAGE_AXIS));


    // Top left panel:
    JPanel topLeftPanel = new JPanel();
    topLeftPanel.setBorder(BorderFactory.createTitledBorder("Shapes"));
    topLeftPanel.setPreferredSize(new Dimension(this.x + 25, this.y + 25));
    topLeftPanel.setMaximumSize(new Dimension(this.width, this.height * 100));
    topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.PAGE_AXIS));
    topLeftPanel.add(new JLabel("Shape->Type"));
    // Shapes list
    defaultModelShape = new DefaultListModel();
    shapeList = new JList<String>(defaultModelShape);
    shapeList.addListSelectionListener(this);
    shapeList.setFixedCellWidth(this.x);
    shapeList.setName("shapeList");
    JScrollPane scrollPane = new JScrollPane(shapeList);
    scrollPane.setHorizontalScrollBar(null);
    scrollPane.setPreferredSize(new Dimension(this.x, this.y));
    scrollPane.setMaximumSize(new Dimension(this.width, this.height));
    topLeftPanel.add(scrollPane);

    JButton deleteButton = new JButton("Remove");
    deleteButton.setActionCommand("Remove shape");
    deleteButton.addActionListener(this);
    topLeftPanel.add(deleteButton);
    JScrollPane tfScrollPane = new JScrollPane(topLeftPanel);
    mainLeftPanel.add(tfScrollPane);

    // Top middle panel:
    JPanel topMiddlePanel = new JPanel();
    topMiddlePanel.setBorder(BorderFactory.createTitledBorder("Animation"));
    topMiddlePanel.setLayout(new BoxLayout(topMiddlePanel, BoxLayout.PAGE_AXIS));
    topMiddlePanel.setPreferredSize(new Dimension(this.x, this.y));
    topMiddlePanel.setMaximumSize(new Dimension(this.width, this.height));
    // Draws the animation in this drawing panel
    drawingPanel = delegate.panel;
    drawingPanel.setPreferredSize(new Dimension(this.width, this.height));
    JScrollPane scrollBar = new JScrollPane(drawingPanel);
    topMiddlePanel.add(scrollBar);

    JPanel playPausePanel = new JPanel();
    JButton startButton = new JButton("Start");
    JButton resumeButton = new JButton("Resume");
    JButton pauseButton = new JButton("Pause");
    startButton.setActionCommand("Start animation");
    startButton.addActionListener(this);
    resumeButton.setActionCommand("Resume animation");
    resumeButton.addActionListener(this);
    pauseButton.setActionCommand("Pause animation");
    pauseButton.addActionListener(this);
    playPausePanel.add(startButton);
    playPausePanel.add(resumeButton);
    playPausePanel.add(pauseButton);
    topMiddlePanel.add(playPausePanel);
    JScrollPane tmScrollPane = new JScrollPane(topMiddlePanel);
    mainMidPanel.add(tmScrollPane);

    //  Top right panel:
    JPanel topRightPanel = new JPanel();
    // Border title.
    topRightPanel.setBorder(BorderFactory.createTitledBorder("Keyframes"));
    topRightPanel.setPreferredSize(new Dimension(this.x + 30, this.y + 50));
    topRightPanel.setMaximumSize(new Dimension(this.width, this.height));
    topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.PAGE_AXIS));
    topRightPanel.add(new JLabel("Time->Position->Size->Color"));
    // Keyframes list
    defaultModelKf = new DefaultListModel();
    keyFrameList = new JList<String>(defaultModelKf);
    keyFrameList.addListSelectionListener(this);
    keyFrameList.setName("keyFrameList");
    JScrollPane scrollPaneKf = new JScrollPane(keyFrameList);
    scrollPaneKf.setPreferredSize(new Dimension(this.x, this.y));
    scrollPaneKf.setMaximumSize(new Dimension(this.width, this.height));
    topRightPanel.add(scrollPaneKf);
    // Add remove keyframe button
    JButton deleteButtonKf = new JButton("Remove");
    deleteButtonKf.setActionCommand("Remove kf");
    deleteButtonKf.addActionListener(this);
    topRightPanel.add(deleteButtonKf);
    JScrollPane trScrollPane = new JScrollPane(topRightPanel);
    mainRightPanel.add(trScrollPane);

    // Bottom left panel:
    JPanel bottomLeftPanel = new JPanel();
    // Border title.
    bottomLeftPanel.setBorder(BorderFactory.createTitledBorder("Create Shapes"));
    // Border size.
    bottomLeftPanel.setPreferredSize(new Dimension(this.x, this.y));
    bottomLeftPanel.setMaximumSize(new Dimension(this.width, this.height));
    // Box layout.
    bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.PAGE_AXIS));
    // Add two buttons that create rectangle/ oval shapes
    ButtonGroup radioGroupShapes = new ButtonGroup();
    rectRadio = new JRadioButton("Rectangle");
    JRadioButton ellipseRadio = new JRadioButton("Ellipse");
    JPanel radioGroupPanel = new JPanel();
    radioGroupPanel.add(rectRadio);
    radioGroupPanel.add(ellipseRadio);
    radioGroupShapes = new ButtonGroup();
    radioGroupShapes.add(rectRadio);
    radioGroupShapes.add(ellipseRadio);
    rectRadio.setActionCommand("Rectangle");
    rectRadio.addActionListener(this);
    ellipseRadio.setActionCommand("Ellipse");
    ellipseRadio.addActionListener(this);
    bottomLeftPanel.add(radioGroupPanel);

    // Input shape name and type to create that shape.
    shapeNameInput = new JTextArea(1, 5);
    shapeName = new JLabel("Shape Name");
    JPanel labelShapePanel = new JPanel(new FlowLayout());
    labelShapePanel.add(shapeName);
    labelShapePanel.add(shapeNameInput);
    bottomLeftPanel.add(labelShapePanel);

    JButton enterButton = new JButton("Enter");
    enterButton.setActionCommand("Enter shape");
    enterButton.addActionListener(this);
    bottomLeftPanel.add(enterButton);
    mainLeftPanel.add(bottomLeftPanel);

    // Bottom middle panel:
    JPanel bottomMiddlePanel = new JPanel();
    // Border title.
    bottomMiddlePanel.setBorder(BorderFactory.createTitledBorder("Change Speed or Restart"));
    bottomMiddlePanel.setPreferredSize(new Dimension(this.x, this.y));
    bottomMiddlePanel.setMaximumSize(new Dimension(this.width, this.height));
    bottomMiddlePanel.setLayout(new BoxLayout(bottomMiddlePanel, BoxLayout.PAGE_AXIS));
    JLabel selectTickLabel = new JLabel("Please select the tick speed");
    bottomMiddlePanel.add(selectTickLabel);
    snm = new SpinnerNumberModel();
    snm.setMinimum(1);
    JSpinner pSpinner = new JSpinner(snm);
    // set Bounds for spinner
    pSpinner.setPreferredSize(new Dimension(this.x, this.y));
    pSpinner.setMaximumSize(new Dimension(this.width, this.height));
    pSpinner.addChangeListener(this);
    bottomMiddlePanel.add(pSpinner);
    // Restart button
    JButton restartButton = new JButton("Restart");
    restartButton.setActionCommand("Restart animation");
    restartButton.addActionListener(this);
    bottomMiddlePanel.add(restartButton);
    // Loop
    loopRadio = new JRadioButton("Loop");
    loopRadio.setActionCommand("Loop animation");
    loopRadio.addActionListener(this);
    bottomMiddlePanel.add(loopRadio);
    mainMidPanel.add(bottomMiddlePanel);

    // Bottom right panel:
    JPanel bottomRightPanel = new JPanel();
    // Border title.
    bottomRightPanel.setBorder(BorderFactory.createTitledBorder("Create or Modify Keyframes"));
    bottomRightPanel.setPreferredSize(new Dimension(this.x, this.y));
    bottomRightPanel.setMaximumSize(new Dimension(this.width, this.height));
    // Box layout.
    bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.PAGE_AXIS));
    // Create two buttons that create or delete shape.
    JRadioButton createButtonKf = new JRadioButton("Create");
    JRadioButton modifyButtonKf = new JRadioButton("Modify");
    JButton enterButtonKf = new JButton("Enter");
    createButtonKf.setActionCommand("Create kf");
    createButtonKf.addActionListener(this);
    modifyButtonKf.setActionCommand("Modify kf");
    modifyButtonKf.addActionListener(this);
    enterButtonKf.setActionCommand("Enter kf");
    enterButtonKf.addActionListener(this);
    ButtonGroup radioGroupKfs = new ButtonGroup();
    radioGroupKfs.add(createButtonKf);
    radioGroupKfs.add(modifyButtonKf);
    JPanel radioKfPanel = new JPanel();
    radioKfPanel.add(createButtonKf);
    radioKfPanel.add(modifyButtonKf);
    bottomRightPanel.add(radioKfPanel);

    // Input shape name, time, pos, size, and color for kf

    shapeNameKf = new JLabel("Shape Name");
    shapeNameKfInput = new JTextArea(1, 5);
    shapeNameKfInput.setText("insert name");
    timeKf = new JLabel("Time");
    timeKfInput = new JTextArea(1, 5);
    timeKfInput.setText("0");
    posKf = new JLabel("Position");
    posKfInput = new JTextArea(1, 5);
    posKfInput.setText("x y");
    sizeKf = new JLabel("Size");
    sizeKfInput = new JTextArea(1, 5);
    sizeKfInput.setText("0 0");
    colorKf = new JLabel("Color");

    JPanel labelKfPanel = new JPanel();
    labelKfPanel.setLayout(new GridLayout(5, 2));
    labelKfPanel.add(shapeNameKf);
    labelKfPanel.add(shapeNameKfInput);
    labelKfPanel.add(timeKf);
    labelKfPanel.add(timeKfInput);
    labelKfPanel.add(posKf);
    labelKfPanel.add(posKfInput);
    labelKfPanel.add(sizeKf);
    labelKfPanel.add(sizeKfInput);
    labelKfPanel.add(colorKf);
    selectColor = new JButton("Select Color");
    selectColor.addActionListener(this);
    selectColor.setActionCommand("Select color");
    labelKfPanel.add(selectColor);
    bottomRightPanel.add(labelKfPanel);

    bottomRightPanel.add(enterButtonKf);
    JScrollPane sp = new JScrollPane(bottomRightPanel);
    mainRightPanel.add(sp);

    add(mainLeftPanel, BorderLayout.WEST);
    add(mainMidPanel, BorderLayout.CENTER);
    add(mainRightPanel, BorderLayout.EAST);
    pack();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Color selectedForeground = selectColor.getForeground();
    switch (e.getActionCommand()) {
      case "Remove shape":
        for (IViewFeatures feature : listeners) {
          feature.removeShape();
        }
        break;
      case "Enter shape":
        for (IViewFeatures feature : listeners) {
          feature.addShape(shapeNameInput.getText(), rectRadio.isSelected());
        }
        break;
      case "Start animation":
        for (IViewFeatures feature : listeners) {
          feature.play();
        }
        break;
      case "Pause animation":
        for (IViewFeatures feature : listeners) {
          feature.paused();
        }
        break;
      case "Resume animation":
        for (IViewFeatures feature : listeners) {
          feature.resume();
        }
        break;
      case "Restart animation":
        for (IViewFeatures feature : listeners) {
          feature.rewind();
        }
        break;
      case "Loop animation":
        for (IViewFeatures feature : listeners) {
          feature.loop(loopRadio.isSelected());
        }
        break;
      case "Create kf":
        this.createKf = true;
        shapeNameKfInput.setEnabled(true);
        timeKfInput.setEnabled(true);
        sizeKfInput.setEnabled(false);
        posKfInput.setEnabled(false);
        selectColor.setEnabled(false);
        break;
      case "Remove kf":
        for (IViewFeatures feature : listeners) {
          feature.removeKeyFrame();
        }
        break;
      case "Modify kf":
        this.createKf = false;
        shapeNameKfInput.setEnabled(false);
        timeKfInput.setEnabled(false);
        sizeKfInput.setEnabled(true);
        posKfInput.setEnabled(true);
        selectColor.setEnabled(true);
        break;
      case "Select color":
        Color initialForeground = selectColor.getForeground();
        selectedForeground = JColorChooser.showDialog(null, "Change Keyframe Background",
                initialForeground);
        if (selectedForeground != null) {
          selectColor.setForeground(selectedForeground);
        }
        break;
      case "Enter kf":
        for (IViewFeatures feature : listeners) {
          if (createKf) {
            feature.addKeyFrame(shapeNameKfInput.getText(), timeKfInput.getText());
          } else {
            feature.editKeyFrame(posKfInput.getText(), sizeKfInput.getText(),
                    selectedForeground.getRed(), selectedForeground.getGreen(),
                    selectedForeground.getBlue());
          }
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      JList list = (JList) e.getSource();
      String listName = list.getName();
      if (listName.equals("shapeList")) {
        String item = (String) list.getSelectedValue();
        Scanner scan = new Scanner(item);
        String name = scan.next();
        for (IViewFeatures feat : listeners) {
          defaultModelKf.clear();
          for (IKeyFrame in : feat.getModelKeyFrames()) {
            if (name.equals(in.getName())) {
              StringBuilder sb = new StringBuilder();
              sb.append("Name: ");
              sb.append(in.getName());
              sb.append(" T: ");
              sb.append(in.getTime());
              sb.append(" X: ");
              sb.append(in.getX());
              sb.append(" Y: ");
              sb.append(in.getY());
              sb.append(" W: ");
              sb.append(in.getWidth());
              sb.append(" H: ");
              sb.append(in.getHeight());
              sb.append(" R: ");
              sb.append(in.getRed());
              sb.append(" G: ");
              sb.append(in.getGreen());
              sb.append(" B: ");
              sb.append(in.getBlue());
              defaultModelKf.addElement(sb.toString());
            }
          }
        }
      }
    }
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    for (IViewFeatures feature : listeners) {
      feature.changeSpeed((Integer) snm.getValue());
    }
  }

  @Override
  public void outputsAnimation() throws IOException, UnsupportedOperationException {
    delegate.outputsAnimation();
  }

  @Override
  public void setOutput(String output) throws UnsupportedOperationException {
    delegate.outputsAnimation();
  }

  @Override
  public void setTickSpeed(int speed)
          throws UnsupportedOperationException, IllegalArgumentException {
    if (speed < 1) {
      throw new IllegalArgumentException("Tick speed must be at least 1 tick per second");
    }
    this.tickSpeed = speed;
    snm.setValue(tickSpeed);
  }

  @Override
  public void setShapes(List<IReadOnlyMyShape> shapes) {
    delegate.setShapes(shapes);
  }

  @Override
  public void setOperations(List<Operation> ops) {
    delegate.setOperations(ops);
  }

  @Override
  public void setBoundaries(int x, int y, int width, int height) {
    // This view has its own arbitrary size due to its design
  }

  @Override
  public void render(List<IReadOnlyMyShape> shapes) {
    this.drawingPanel.draw(shapes);
  }

  @Override
  public boolean isVisualView() {
    return false;
  }

  @Override
  public List<Operation> getOperations() {
    throw new UnsupportedOperationException("Edit view does not have a list of operations");
  }

  @Override
  public List<IReadOnlyMyShape> getShapes() {
    throw new UnsupportedOperationException("Edit view does not have a list of IReadOnlyMyShapes");
  }

  @Override
  public int getTickSpeed() {
    return this.tickSpeed;
  }

  @Override
  public void addListener(IViewFeatures listener) {
    listeners.add(listener);
    this.setJListShapes(listener.getModelShapes());
  }

  @Override
  public void setJListShapes(List<IReadOnlyMyShape> shapes) {
    defaultModelShape.removeAllElements();
    for (int i = 0; i < shapes.size(); i++) {
      StringBuilder sb = new StringBuilder();
      sb.append(shapes.get(i).getName());
      sb.append("\t\t\t\t");
      sb.append(shapes.get(i).getShapeType());
      defaultModelShape.addElement(sb.toString());
    }
  }

  @Override
  public void setJListKeyFrames(List<IKeyFrame> keyframes, String name) {
    defaultModelKf.removeAllElements();
    for (int i = 0; i < keyframes.size(); i++) {
      if (name.equals(keyframes.get(i).getName())) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(keyframes.get(i).getName());
        sb.append(" T: ");
        sb.append(keyframes.get(i).getTime());
        sb.append(" X: ");
        sb.append(keyframes.get(i).getX());
        sb.append(" Y: ");
        sb.append(keyframes.get(i).getY());
        sb.append(" W: ");
        sb.append(keyframes.get(i).getWidth());
        sb.append(" H: ");
        sb.append(keyframes.get(i).getHeight());
        sb.append(" R: ");
        sb.append(keyframes.get(i).getRed());
        sb.append(" G: ");
        sb.append(keyframes.get(i).getGreen());
        sb.append(" B: ");
        sb.append(keyframes.get(i).getBlue());
        defaultModelKf.addElement(sb.toString());
      }
    }
  }

  @Override
  public boolean isEditView() {
    return true;
  }

  @Override
  public JList<String> getJListShapes() {
    return this.shapeList;
  }

  @Override
  public JList<String> getKeyFrames() {
    return this.keyFrameList;
  }
}