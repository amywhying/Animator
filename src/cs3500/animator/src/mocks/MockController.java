package mocks;

import java.io.IOException;
import java.util.List;

import cs3500.animator.controller.IController;
import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFeatures;

public class MockController implements IController, IViewFeatures {
  private Appendable appendable;

  public MockController(IView view, Appendable appendable) {
    this.appendable = appendable;
    view.addListener(this);
  }

  @Override
  public void execute() throws IOException {
    // not applicable to edit view
  }

  @Override
  public void setSpeed(int speed) throws UnsupportedOperationException {
    // not applicable to edit view
  }

  @Override
  public void setOutput(String output) throws UnsupportedOperationException {
    // not applicable to edit view
  }

  @Override
  public void paused() {
    try {
      this.appendable.append("Paused");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void play() {
    try {
      this.appendable.append("Played");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void rewind() {
    try {
      this.appendable.append("Rewinded");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void changeSpeed(int value) {
    try {
      this.appendable.append("Speed changed");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void addShape(String name, boolean rect) {
    try {
      this.appendable.append("Shape added");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void removeShape() {
    try {
      this.appendable.append("Shape removed");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void loop(boolean value) {
    try {
      this.appendable.append("Loop changed");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void resume() {
    try {
      this.appendable.append("Resumed");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void addKeyFrame(String name, String time) {
    try {
      this.appendable.append("KeyFrame added");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void removeKeyFrame() {
    try {
      this.appendable.append("KeyFrame removed");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public void editKeyFrame(String position, String dimensions, int red, int green, int blue) {
    try {
      this.appendable.append("KeyFrame edited");
    } catch (IOException e) {
      // the error should not be thrown in the mock
    }
  }

  @Override
  public List<IReadOnlyMyShape> getModelShapes() {
    // not applicable to edit view
    return null;
  }

  @Override
  public List<IKeyFrame> getModelKeyFrames() {
    // not applicable to edit view
    return null;
  }
}
