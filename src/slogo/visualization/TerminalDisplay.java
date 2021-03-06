package slogo.visualization;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TerminalDisplay {
  private final Pane pane;
  public TerminalDisplay(Pane pane){
    this.pane = pane;

    pane.setStyle("-fx-background-color: #ffff00");


    Text test = new Text("terminal");
    pane.getChildren().add(test);
  }
}
