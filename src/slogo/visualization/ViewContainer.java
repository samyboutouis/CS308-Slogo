package slogo.visualization;

import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * The ViewContainer class is responsible for creating an instance of a view container,
 * including all of its UI components, and sending updated information about what new
 * display view it should house to the parent ViewLayout object from user input.
 *
 * @author Samy Boutouis
 * @author Donghan Park
 */
public class ViewContainer {

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle("resources/languages/English");
  private static final String VIEW_CONTAINER_ID = "ViewContainer";
  private static final String VIEW_CONTAINER_COMBOBOX_ID = "ViewContainerComboBox";
  private static final String VIEW_CONTAINER_BUTTON_ID = "ViewContainerCloseButton";
  private static final String CLOSE_BUTTON = "CloseButton";
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

  /**
   * Constructor that creates an instance of the ViewContainer object.
   * @param viewLayout Reference to the ViewLayout object, which encapsulates and manages all view containers
   * @param containerIndex Corresponding index of the current view container in the list of all view containers
   * @param viewNames Set of all possible display views that the view container can house
   */
  public ViewContainer(ViewLayout viewLayout, int containerIndex, Set<String> viewNames) {
    this.viewLayout = viewLayout;
    this.containerIndex = containerIndex;
    this.viewNames = viewNames;

    pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    pane.getStyleClass().add(VIEW_CONTAINER_ID);

    comboBox = new ComboBox<>();
    closeButton = new Button();

    initializeCloseButton();
    initializeComboBox();
    applyCloseButtonLogic();
  }

  private void initializeCloseButton(){
    closeButton.setText(RESOURCE_BUNDLE.getString(CLOSE_BUTTON));
    closeButton.setId(VIEW_CONTAINER_BUTTON_ID);
    closeButton.setMaxSize(BUTTON_LENGTH, BUTTON_LENGTH);
    pane.add(closeButton, 5, 0, 1, 1);
  }

  private void initializeComboBox() {
    comboBox.setId(VIEW_CONTAINER_COMBOBOX_ID);
    comboBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    pane.add(comboBox, 0, 0, 5, 1);

    for (String viewName : viewNames) {
      comboBox.getItems().add(viewName);
    }
    applyComboBoxLogic();
  }

  private void applyCloseButtonLogic() {
    closeButton.setOnAction(e -> viewLayout.updateViewLayouts(containerIndex, null));
  }

  private void applyComboBoxLogic() {
    comboBox.setOnAction(e -> viewLayout.updateViewLayouts(containerIndex, comboBox.getValue()));
  }

  /**
   * Updates the title of the combobox with a new desired one.
   * @param title New title of the combobox
   */
  public void updateComboBox(String title) {
    comboBox.setValue(title);
  }

  /**
   * Returns the root pane of the display view.
   * @return Root pane of the display view
   */
  public GridPane getPane() {
    return pane;
  }
}