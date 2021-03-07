package slogo.visualization;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ToolbarDisplay {
  private final HBox pane;
  public ToolbarDisplay(HBox pane){
    this.pane = pane;

    Text test = new Text("toolbar");
    pane.getChildren().add(test);
  }
}
