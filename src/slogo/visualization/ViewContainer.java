package slogo.visualization;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class ViewContainer {

  private final ViewLayout viewLayout;
  private final GridPane pane;
  private final ComboBox<String> comboBox;
  private final Button closeButton;
  private final int containerIndex;

  private final static String[] viewNames = {"variables", "commands", "history", "palette", "states", "buttons"};

  public ViewContainer(ViewLayout viewLayout, GridPane pane, int containerIndex){
    this.viewLayout = viewLayout;
    this.pane = pane;
    this.containerIndex = containerIndex;
    pane.getStyleClass().add("yeetBox"); //for testing

    comboBox = new ComboBox<>();
    closeButton = new Button();

    pane.add(comboBox, 0, 0);
    pane.add(closeButton, 1, 0);

    initializeComboBox();
    initializeCloseButton();
  }

  private void initializeComboBox(){
    for (String viewName : viewNames) {
      comboBox.getItems().add(viewName);
    }
    comboBox.setOnAction(event -> handleClick(comboBox.getValue()));
  }

  private void initializeCloseButton(){
    closeButton.setOnAction(e -> handleCloseButton());
  }

  private void handleClick(String viewName){
    viewLayout.updateViewLayouts(containerIndex, viewName);
  }

  private void handleCloseButton(){
    viewLayout.updateViewLayouts(containerIndex, null);
  }

  public void updateComboBox(String title){
    comboBox.setValue(title);
  }
}