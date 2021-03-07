package slogo.visualization;

import javafx.scene.layout.VBox;
import slogo.Turtle;

public class TurtleDisplay {
  private final VBox vbox;
  private Turtle turtle;

  public TurtleDisplay(VBox vbox){
    this.vbox = vbox;
    this.turtle = new Turtle(vbox.getMinWidth(), vbox.getMinHeight());
    setScreen();
  }

  private void setScreen() {
    vbox.getChildren().add(turtle);
  }
}
