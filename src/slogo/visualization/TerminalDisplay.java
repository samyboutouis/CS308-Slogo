package slogo.visualization;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TerminalDisplay {

  private final Pane pane;

  public TerminalDisplay(Pane pane){
    this.pane = pane;

    pane.setStyle("-fx-background-color: #efefef");


  }

  private void initializeTextField(){
    TextField textField = new TextField();
  }
}
