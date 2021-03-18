package slogo.visualization;

import javafx.scene.layout.VBox;

public class ButtonView implements View {
  private VBox vbox;

  public ButtonView() {
    vbox = new VBox();
  }

  public void hide() {
    vbox.setVisible(false);
  }

  public void show() {
    vbox.setVisible(true);
  }

  public void setPosition(double xPosition, double yPosition) {

  }
}
