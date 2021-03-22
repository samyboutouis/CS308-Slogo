package slogo.visualization;

import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public class ViewContainer {

  private static final String VIEW_CONTAINER_ID = "ViewContainer";
  private static final String VIEW_CONTAINER_COMBOBOX_ID = "ViewContainerComboBox";
  private static final String VIEW_CONTAINER_BUTTON_ID = "ViewContainerCloseButton";
  private static final int GRID_ROW_COUNT = 10;
  private static final int GRID_COLUMN_COUNT = 6;
  private static final int PADDING_LENGTH = 10;
  private static final int BUTTON_LENGTH = 5;

  private final ViewLayout viewLayout;
  private final CustomGridPane pane;
  private final ComboBox<String> comboBox;
  private final Button closeButton;
  private final int containerIndex;
  private final Set<String> viewNames;

  public ViewContainer(ViewLayout viewLayout, int containerIndex, Set<String> viewNames) {
    this.viewLayout = viewLayout;
    this.containerIndex = containerIndex;
    this.viewNames = viewNames;

    pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    pane.getStyleClass().add(VIEW_CONTAINER_ID);

    comboBox = new ComboBox<>();
    comboBox.setId(VIEW_CONTAINER_COMBOBOX_ID);
    comboBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    closeButton = new Button("-");
    closeButton.setId(VIEW_CONTAINER_BUTTON_ID);
    closeButton.setMaxSize(BUTTON_LENGTH, BUTTON_LENGTH);

    pane.add(comboBox, 0, 0, 5, 1);
    pane.add(closeButton, 5, 0, 1, 1);

    initializeComboBox();
    initializeCloseButton();
  }

  private void initializeComboBox() {
    for (String viewName : viewNames) {
      comboBox.getItems().add(viewName);
    }
    comboBox.setOnAction(event -> handleClick(comboBox.getValue()));
  }

  private void initializeCloseButton() {
    closeButton.setOnAction(e -> handleCloseButton());
  }

  private void handleClick(String viewName) {
    viewLayout.updateViewLayouts(containerIndex, viewName);
  }

  private void handleCloseButton() {
    viewLayout.updateViewLayouts(containerIndex, null);
  }

  public void updateComboBox(String title) {
    comboBox.setValue(title);
  }

  public GridPane getPane() {
    return pane;
  }
}