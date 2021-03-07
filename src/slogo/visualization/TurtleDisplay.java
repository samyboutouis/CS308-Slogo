package slogo.visualization;

import javafx.scene.layout.Pane;
import slogo.Turtle;

public class TurtleDisplay {
  private final Pane pane;
  private Turtle turtle;

  public TurtleDisplay(Pane pane){
    this.pane = pane;
    this.turtle = new Turtle();
    setScreen();
  }

  private void setScreen() {
    pane.getChildren().add(turtle);
  }
}
