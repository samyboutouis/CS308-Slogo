package slogo.visualization;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HistoryDisplay {
  private VBox pane;

  public HistoryDisplay(VBox pane){
    this.pane = pane;

    pane.setStyle("-fx-background-color: #fff000");


    Text test = new Text("history");
    pane.getChildren().add(test);
  }
}
