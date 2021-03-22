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

  public GridPane getPane() {
    return pane;
  }
}
