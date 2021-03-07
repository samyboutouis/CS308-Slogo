package slogo.visualization;

import javafx.scene.layout.Pane;

public class TurtleDisplay {
  private final Pane pane;
  public TurtleDisplay(Pane pane){
    this.pane = pane;
    pane.setStyle("-fx-background-color: #ff0000");
  }
}
