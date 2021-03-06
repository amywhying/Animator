# Animator

![demo](https://github.com/amywhying/Animator/blob/master/Screenshot%202019-06-23%20at%2012.27.44%20AM.png)


Excellence main class:

    This is the main method. It goes through the command line arguments and creates the appropriate view
    using the ViewFactory and creates the model using AnimationReader and AnimationBuilder. It sends
    the view and model to the controller. If there is a speed and output file specified, it
    calls the controller's set methods to set the speed and output. It then executes the animation
    through the controller. It shows errors and exceptions through JOptionPane.

ViewFactory  class:

    This is the factory class/method that takes in a type of view from the command line arguments and
    creates the correct View from it.


Controller package:
IController interface:

    This is the interface for a controller. We decided that a controller should be able to create
    the animation, and set the speed and output file for the view.

ControllerImpl class:

    This is the implementation of IController. We decided that the controller should have an
    instance of the view and model. When the controller is executed, it asks the model for the
    shapes, operations, and boundaries, and sends those to the views. We decided that for visual
    views, the shape "tweening" should happen in the model (because it has better access to all the
    shapes and operations) and the list of Shapes is passed through the controller to the
    Visual/Edit View. The controller keeps track of the tick speed, current tick, and timer.

    CHANGES:
    Our controller now also implements IViewFeatures, which is the interface between the
    controller and EditVisualView. It is like a higher abstracted version of an ActionListener or
    EventListener. The controller sets itself as the listener for the EditVisualView. Whenever an
    action (button press) happens in the view, the listener (controller) is notified and can alter
    the model appropriately.


Model package:
Model interface:

    This is the interface for our model. We have getAllOperations() for the Controller to use to
    tell the Text, SVG, and Visual Views the operations in the animations. We have getX(), getY(),
    getWidth(), and getHeight() to get the boundaries of the animation. We have getShapesAtTick()
    method to retrieve a list of all shapes at a given tick (using Operations). We put this method
    in the Model instead of the Controller or View because the method needs to be able to change
    the values of the shapes, which can only be done in the model.

    CHANGES:
    The Model had a lot of methods added to it. To support KeyFrames, insertKeyFrame, editKeyFrame,
    removeKeyFrame, getKeyFrames, and getShaepsAtTickKeyFrame were added. We also added
    findFirstKeyFrameTime and findLastKeyFrameTime to find the first and last time in all KeyFrames.
     This is used by the controller to know when to start and end the animation. We also added
    sortingKf which sorts the Model's KeyFrames. This is used when a View wants a sorted list of
    KeyFrames. We decided that when a KeyFrame is added, a user only needs to specify the Shape to
    add the KeyFrame to and the time for the KeyFrame. The KeyFrame that is added is given values
    based on existing KeyFrames for the Shape. After the KeyFrame is added, the can then edit it.
    To support the EditVisual view adding and removing shapes, we added declareShape and removeShape
    .

ModelImpl class:

    This is the implementation of Model. A ModelImpl is made through the Builder, so the ModelImpl
    constructor is private. Shapes and KeyFrames for the EditVisualView can also be added, removed,
    and modified after creating the original ModelImpl.
    
    CHANGES:
    When adding a KeyFrame through insertKeyFrame, the new KeyFrame's values depends on the other
    KeyFrames for the shape. If the shape has no KeyFrames or only one KeyFrame., the new KeyFrame is
    initialized with values of all 0. If the shape has at least 2 existing KeyFrames, the new
    KeyFrame takes its value depending on where it's being added. If it is being added before all
    the existing KeyFrames, the new KeyFrame will take the values of the previously first KeyFrame.
    If it is being added after all the existing KeyFrames, the new KeyFrame will take the values of
     the previously last KeyFrame. If it is being added in the middle of 2 existing KeyFrames, the
     new KeyFrame will take the value of its Shape tweened at the tick its being added.
     We also decided that if a Shape only has one KeyFrame, the shape should only appear at that one
     KeyFrame's tick. To have a Shape continuously appear on the animation, there must be at least 2
     KeyFrames for the shape.


KeyFrame package:

    IKeyFrame interface:
    This is the interface for a KeyFrame. We decided that a KeyFrame should be able to hold the
    Shape it controls, the time the KeyFrame exists, and the x, y, width, height, and rgb color
    values the Shape should be at the KeyFrame's time.

KeyFrame class:
    
    The concrete KeyFrame class just implements the interface and stores all the values of the
    KeyFrame. A KeyFrame can't be created before tick 0.


View package:
IView interface:
    
    This is the interface for a view. We decided that a view should be able to set its list of
    shapes, list of operations, boundaries, tick speed, output file, and output its animation. For
    setting speed and output file, now all views support those functionalities, so
    UnsupportedOperationExceptions are thrown. For views that don't specify an output but require
    knowing a list of shapes at any given tick, like the VisualView, there is a render() method.

    CHANGES:
    We added more methods in the interface that are exclusively for the EditVisualView,
    getJListShapes, getKeyFrames, addListener, setJListShapes, setJListKeyFrames. These are used by
    the controller in setting the EditVisualView's lists. For views that don't use these methods,
    UnsupportedOperationExceptions are thrown.

IViewFeatures interface:
    
    CHANGES:
    This is the interface that allows communication between the View and the Controller. The
    EditVisualView doesn't have an instance of the Controller, instead the Controller sets itself as
     the action/event listener for the view, which uses this interface. This is a new interface.

EditVisualView class:
    
    CHANGES:
    This is the class that allows users to edit animations as they are played. The class creates a
    Java Swing application which the user interacts with by pressing buttons on the application. It
    doesn't use some of the methods in the View which are utilized by the other View types, so it
    throws an unsupportedOperationException.

TextView class:
    
    This is the view for creating a chart of shapes and their animations. The createChart method was
    moved from the model, to this view. A text view doesn't have a tick speed to specify, so
    calling setTickSpeed on the TextView throws an UnsupportedOperationException.

SVGView class:
    
    This is the view for creating a SVG/XML text representing the animations. The SVG doesn't
    require knowing shapes at any given ticks, instead relies on knowing all shapes and operations
    at the beginning of the animation, so it does not support the render() method.

VisualView class:
    
    This is the view for creating a pop-up animation using JFrame. The VisualView requires knowing a
    list of shapes at a given tick speed, so it doesn't support the outputsAnimation() method. It
    also doesn't support an output file.

IDrawingPanel interface:

    This is an interface to draw a list of shapes for the Visual View.

DrawingPanel class:
    
    This is the class that creates the JPanel for the Visual view and actually draws each shape.


OPERATIONS DID NOT GET CHANGED
Operation package:
Operation interface:

    We made our Operations function object that do their individual operation on a shape over a
    certain tick interval. The main change to the Operation interface is the execute() method.
    Previously, the method took in the Shape to execute on. Now each Operation stores as a field the
    shape it operates on, and execute grabs the shape from the Operation's field. The interface
    still has getters for the Operations' start time, end time, shape name, and shape itself. It
    also has methods to determine the type of operation it is. We also added a getStates() method
    that returns an array of ints representing each of the initial and ending state of the shape
    the operation operates on. These are used in the tweening method in the model.

OperationAbstract abstract class:
    
    This is the abstract class for an Operation. The changes we made were allowing the start
    time of an Operation to be equal to its ending time and allowing an Operation to start at tick
    zero. We also now store the shape that an Operation operates on as a field in the class, instead
     of having the execute() method take in the shape to execute on. This makes it easier to grab
     the values of an operation in the views.

AdjustHeight class:
    
    This is an operation to change a shape's height. The only change we made is adding a constructor
     that takes in the shape's initial and ending states over the operation's tick interval.

AdjustWidth class:
    
    This is an operation to change a shape's width. The only change we made is adding a constructor
    that takes in the shape's initial and ending states over the operation's tick interval.

ChangeColor class:
    
    This is an operation to change a shape's color. The only change we made is adding a constructor
    that takes in the shape's initial and ending states over the operation's tick interval.

MovePosition class:
    
    This is an operation to change a shape's position. The only change we made is adding a
    constructor that takes in the shape's initial and ending states over the operation's tick
    interval.


SHAPES DID NOT GET CHANGED
Shape package:
IReadOnlyMyShape interface:

    This is the interface for a read-only version of a MyShape. We made a read-only version because
    the controller and view that receive lists of shapes should not be able to change those shapes.
    Changing the shapes should only be allowed in the model. The interface just has getters for all
    the values of a shape.

MyShape interface:

    This interface extends IReadOnlyMyShape and adds setters for all the values of a shape. Neither
    interface was changed.

MyShapeAbstract:

    This is the abstract class for a shape. We changed class to allow shapes to have heights and
    widths of 0. We also added a constructor that only takes in a name and creates a shape with
    values all set to 0. This constructor is used in the Model Builder, which only declares shapes
    with names.

ShapeType enum:

    This is the enumerations of all supported shapes. This is used in the views when creating shapes
     for an animation. The only change is that "oval" is now "ellipse".

Ellipse class:

    This is the class for an ellipse, which can also be used to create circles. Previously, it was
    called "Oval", but we changed it to "Ellipse" because "Ellipse" is more widely used in shape
    libraries. We also added a constructor that declares an Ellipse with just a name.

Rectangle class:

    This is the class for a Rectangle, which can also be used to create squares. We added a
    constructor that declares a Rectangle with only a name.

Triangle class:
    
    This is the class for a Rectangle, which can also be used to create squares. We added a
    constructor that declares a Rectangle with only a name.

EditVisualView class specific JFrame animation functionality:
    
    The entire view is separated into 6 different panels, in which all of them are scroll panes,
    where you can scroll in each panel to see all of its information. The window can also be
    enlarged or shrunk by dragging it in and out.
    Top-left panel displays a list of shapes representing the shapes in the animation. Below it is
    a "Remove" button that can be pressed to remove the selected shape from the list of shapes.
    Top-middle panel displays the animation itself. It contains 3 buttons: "Start", "Resume", and
    "Pause". Start button plays the animation when the tick is 0. Resume button resumes the
    animation if it was paused. Pause button stops the animation. If desired, users can scroll using
    the scoll-bar to see any covered part of the animation.
    Top-right panel displays a list of keyframes of a selected shape from the shape list in top-left
    panel. It only displays a list if a shape from the shape list is selected. Below it is a
    "Remove" button that can be pressed to remove the selected keyframe from the list of keyframes.
    Bottom-left panel allows the creation of rectangle or ellipse shapes. To create a shape, select
    the shape type of it by pressing the "Rectangle" or "Ellipse" radio button, then input the name
    of the shape, and select the "Enter" button.
    Bottom-mid panel has a spinner that allow users to select the animation tick speed. The higher
    the tick speed, the faster the animation. Below it is a  "Restart" button and a loop radio
    button. "Restart" button rewinds the animation backs to the start, and the loop button allows
    animation to loop infinitely if enabled.
    Bottom-right panel can create or modify keyframes. To create a keyframe, select the "Create"
    radio button, input the shape name and time fields (Other fields are disabled), and press the
    "Enter" button. To modify a keyframe, first select the desired keyframe from the keyframe list,
    then input the position and size fields, and select a color by pressing "Select Color" button,
    and finally press the "Enter" button.
