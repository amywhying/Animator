package mocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JList;

import cs3500.animator.model.keyframe.IKeyFrame;
import cs3500.animator.model.operation.Operation;
import cs3500.animator.model.shape.IReadOnlyMyShape;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFeatures;

public class MockEditView implements IView {
  List<IViewFeatures> listeners;
  Readable readable;

  public MockEditView(Readable readable) {
    this.listeners = new ArrayList<IViewFeatures>();
    this.readable = readable;
  }

  @Override
  public void outputsAnimation() throws IOException, UnsupportedOperationException {

  }

  @Override
  public void setOutput(String output) throws UnsupportedOperationException {

  }

  @Override
  public void setBoundaries(int x, int y, int width, int height) {

  }

  @Override
  public void render(List<IReadOnlyMyShape> shapesToRender) throws UnsupportedOperationException {
    Scanner scan = new Scanner(this.readable);
    try {
      String command = scan.next();
      for (IViewFeatures listener : this.listeners) {
        switch (command) {
          case "Pause":
            listener.paused();
            break;
          case "Play":
            listener.play();
            break;
          case "Rewind":
            listener.rewind();
            break;
          case "ChangeSpeed":
            listener.changeSpeed(0); // value doesn't matter
            break;
          case "AddShape":
            listener.addShape("Shape Name", false); // value doesn't matter
            break;
          case "RemoveShape":
            listener.removeShape();
            break;
          case "ChangeLoop":
            listener.loop(false);
            break;
          case "Resume":
            listener.resume();
            break;
          case "AddKeyFrame":
            listener.addKeyFrame(null, null);
            break;
          case "RemoveKeyFrame":
            listener.removeKeyFrame();
            break;
          case "EditKeyFrame":
            listener.editKeyFrame(null, null, 0, 0, 0);
            break;
          default:
            break;
        }
      }
    } catch (Exception e) {
      // exceptions shouldn't be thrown in the mock
    }
  }

  @Override
  public boolean isVisualView() {
    return false;
  }

  @Override
  public List<Operation> getOperations() {
    return null;
  }

  @Override
  public void setOperations(List<Operation> ops) {

  }

  @Override
  public List<IReadOnlyMyShape> getShapes() {
    return null;
  }

  @Override
  public void setShapes(List<IReadOnlyMyShape> shapes) {

  }

  @Override
  public int getX() {
    return 0;
  }

  @Override
  public int getY() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getTickSpeed() {
    return 0;
  }

  @Override
  public void setTickSpeed(int speed) throws UnsupportedOperationException, IllegalArgumentException {

  }

  @Override
  public JList<String> getJListShapes() throws UnsupportedOperationException {
    return null;
  }

  @Override
  public JList<String> getKeyFrames() throws UnsupportedOperationException {
    return null;
  }

  @Override
  public void addListener(IViewFeatures listener) {
    this.listeners.add(listener);
  }

  @Override
  public void setJListShapes(List<IReadOnlyMyShape> shapes) throws UnsupportedOperationException {

  }

  @Override
  public void setJListKeyFrames(List<IKeyFrame> keyframes, String name) throws UnsupportedOperationException {

  }

  @Override
  public boolean isEditView() {
    return true;
  }
}
