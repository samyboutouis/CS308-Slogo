package slogo.visualization;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TurtleDisplay {
  private final Pane pane;
  public TurtleDisplay(Pane pane){
    this.pane = pane;
    pane.setStyle("-fx-background-color: #ff0000");

    Text test = new Text("turtle");
    pane.getChildren().add(test);
  }
}
