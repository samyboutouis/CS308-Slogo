package slogo.visualization;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VariablesDisplay {
  private final VBox pane;
  public VariablesDisplay(VBox pane){
    this.pane = pane;

    pane.setStyle("-fx-background-color: #00ff00");


    Text test = new Text("vars");
    pane.getChildren().add(test);
  }
}
