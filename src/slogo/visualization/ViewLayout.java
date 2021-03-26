package slogo.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import slogo.visualization.displays.ButtonDisplay;
import slogo.visualization.displays.HistoryDisplay;
import slogo.visualization.displays.PaletteDisplay;
import slogo.visualization.displays.TurtleStateDisplay;
import slogo.visualization.displays.UserCommandsDisplay;
import slogo.visualization.displays.VariablesDisplay;

/**
 * The ViewLayout class is responsible for initializing and setting up the view containers
 * that houses the movable display views, as well as managing/updating each display view's
 * position and visibility according to user input.
 *
 * @author Samy Boutouis
 * @author Donghan Park
 */
public class ViewLayout {

  private final static int GRID_ROW_COUNT = 2;
  private final static int GRID_COLUMN_COUNT = 3;
  private final static int PADDING_LENGTH = 10;

  private final Map<String, Pane> viewNamesMap = new HashMap<>();
  private final List<ViewContainer> viewContainers = new ArrayList<>();
  private final CustomGridPane pane;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final UserCommandsDisplay userCommandsDisplay;
  private final PaletteDisplay paletteDisplay;
  private final ButtonDisplay buttonDisplay;
  private final TurtleStateDisplay turtleStateDisplay;

  private String[] viewOrder;

  /**
   * Constructor that creates an instance of the ViewLayout object.
   * @param historyDisplay Reference to the history display view object
   * @param variablesDisplay Reference to the variables display view object
   * @param userCommandsDisplay Reference to the user-commands display view object
   * @param paletteDisplay Reference to the palette display view object
   * @param buttonDisplay Reference to the buttons display view object
   * @param turtleStateDisplay Reference to the turtle states display view object
   */
  public ViewLayout(HistoryDisplay historyDisplay, VariablesDisplay variablesDisplay,
      UserCommandsDisplay userCommandsDisplay, PaletteDisplay paletteDisplay,
      ButtonDisplay buttonDisplay, TurtleStateDisplay turtleStateDisplay) {

    this.pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.userCommandsDisplay = userCommandsDisplay;
    this.paletteDisplay = paletteDisplay;
    this.buttonDisplay = buttonDisplay;
    this.turtleStateDisplay = turtleStateDisplay;

    initializeMap();
    setupViewContainers();
    initializeViewOrder();
  }

  private void initializeMap() {
    viewNamesMap.put("Variables", variablesDisplay.getPane());
    viewNamesMap.put("Commands", userCommandsDisplay.getPane());
    viewNamesMap.put("History", historyDisplay.getPane());
    viewNamesMap.put("Palette", paletteDisplay.getPane());
    viewNamesMap.put("Move Turtle", buttonDisplay.getPane());
    viewNamesMap.put("Turtles", turtleStateDisplay.getPane());
  }

  private void initializeViewOrder() {
    viewOrder = new String[GRID_COLUMN_COUNT * GRID_ROW_COUNT];
  }

  private void setupViewContainers() {
    for (int row = 0; row < GRID_ROW_COUNT; row++) {
      for (int col = 0; col < GRID_COLUMN_COUNT; col++) {
        ViewContainer newViewContainer = new ViewContainer(this, GRID_COLUMN_COUNT * row + col,
            viewNamesMap.keySet());
        pane.add(newViewContainer.getPane(), col, row);
        viewContainers.add(newViewContainer);
      }
    }

    setAllDisplaysInvisible();
  }

  private void setAllDisplaysInvisible() {
    historyDisplay.getPane().setVisible(false);
    variablesDisplay.getPane().setVisible(false);
    userCommandsDisplay.getPane().setVisible(false);
    paletteDisplay.getPane().setVisible(false);
    buttonDisplay.getPane().setVisible(false);
    turtleStateDisplay.getPane().setVisible(false);
  }

  /**
   * Updates the position and visibility of the six movable display views--including the
   * variable, history, graphical buttons, turtle states, user-commands, and palette
   * views--when the user opens a new desired view or closes the current view of the view
   * container. Also implements swapping feature, which essentially swaps the position of
   * the new desired view and the position of the view container that that respective view
   * was currently in. For instance, let the variables display view be in view container 1
   * and the history display view be in view container 2. Choosing to move the variables
   * view to container 2 would swap the history view, currently in container 2, to
   * container 1, where the variables view was at originally.
   * @param clickedIndex The index of the view container to put the new desired view in
   * @param viewName Name of the new desired view to set in the corresponding view container
   */
  public void updateViewLayouts(int clickedIndex, String viewName) {
    int currentIndex = findIndexOf(viewName);

    if (currentIndex != -1) {
      String nameToBeSwapped = viewOrder[clickedIndex];
      turnOffView(viewNamesMap.get(viewName), currentIndex);
      if (nameToBeSwapped != null) {
        turnOnView(viewNamesMap.get(nameToBeSwapped), currentIndex);
      }
      viewOrder[currentIndex] = viewOrder[clickedIndex];
      viewContainers.get(currentIndex).updateComboBox(viewOrder[clickedIndex]);
    }
    if (viewContainers.get(clickedIndex).getPane().getChildren().size() > 2) {
      turnOffView(viewNamesMap.get(viewOrder[clickedIndex]), clickedIndex);
    }
    viewOrder[clickedIndex] = viewName;
    viewContainers.get(clickedIndex).updateComboBox(viewName);
    if (viewName != null && viewContainers.get(clickedIndex).getPane().getChildren().size() <= 2) {
      turnOnView(viewNamesMap.get(viewName), clickedIndex);
    }
  }

  private void turnOnView(Pane targetPane, int index) {
    viewContainers.get(index).getPane().add(targetPane, 0, 1, 6, 9);
    targetPane.setVisible(true);
  }

  private void turnOffView(Pane targetPane, int index) {
    viewContainers.get(index).getPane().getChildren().remove(targetPane);
    targetPane.setVisible(false);
  }

  private int findIndexOf(String desiredView) {
    for (int i = 0; i < viewOrder.length; i++) {
      if (viewOrder[i] != null && viewOrder[i].equals(desiredView)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the root pane of the display view.
   * @return Root pane of the display view
   */
  public GridPane getPane() {
    return pane;
  }
}
