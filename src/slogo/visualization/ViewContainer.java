package slogo.visualization;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class ViewContainer {

  private final GridPane pane;
  private final ComboBox<String> comboBox;
  private final Button closeButton;

  public ViewContainer(GridPane pane){
    this.pane = pane;

    comboBox = new ComboBox<>();
    closeButton = new Button();

    pane.add(comboBox, 0, 0);
    pane.add(closeButton, 0, 1);
  }
}