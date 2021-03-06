package slogo.visualization;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ToolbarDisplay {
  private final HBox pane;
  public ToolbarDisplay(HBox pane){
    this.pane = pane;

    pane.setStyle("-fx-background-color: #00fff0");


    Text test = new Text("toolbar");
    pane.getChildren().add(test);
  }
}
