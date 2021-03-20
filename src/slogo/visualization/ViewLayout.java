package slogo.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import slogo.controller.FrontEndController;

public class ViewLayout {

  private final static int GRID_ROW_COUNT = 2;
  private final static int GRID_COLUMN_COUNT = 3;
  private final static int PADDING_LENGTH = 10;

  private final Map<String, Pane> viewNamesMap = new HashMap<>();

  private final List<ViewContainer> viewContainers = new ArrayList<>();
  private final CustomGridPane pane;
  private final FrontEndController frontEndController;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final UserCommandsDisplay userCommandsDisplay;
  private final ButtonDisplay buttonDisplay;
  private final TurtleStateDisplay turtleStateDisplay;
  private String[] viewOrder;

  private final static int[] viewContainerXPositions = {500, 200, 300, 400};
  private final static int[] viewContainerYPositions = {500, 200, 300, 400};

  public ViewLayout(HistoryDisplay historyDisplay, VariablesDisplay variablesDisplay, UserCommandsDisplay userCommandsDisplay, ButtonDisplay buttonDisplay, TurtleStateDisplay turtleStateDisplay, FrontEndController frontEndController){
    this.pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    this.frontEndController = frontEndController;
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.userCommandsDisplay = userCommandsDisplay;
    this.buttonDisplay = buttonDisplay;
    this.turtleStateDisplay = turtleStateDisplay;

    initializeMap();
    setupViewContainers();
    initializeViewOrder();
  }

  //private final static String[] viewNames = {"Variables Display", "commands", "history", "palette", "states", "buttons"};
  private void initializeMap(){
    viewNamesMap.put("Variables", variablesDisplay.getPane());
    viewNamesMap.put("Commands", userCommandsDisplay.getPane());
    viewNamesMap.put("History", historyDisplay.getPane());
    viewNamesMap.put("Buttons", buttonDisplay.getPane());
    viewNamesMap.put("Turtle States", turtleStateDisplay.getPane());
  }

  private void initializeViewOrder(){
    viewOrder = new String[GRID_COLUMN_COUNT * GRID_ROW_COUNT];
  }

  private void setupViewContainers(){
    for(int row = 0; row < GRID_ROW_COUNT; row++){
      for(int col = 0; col < GRID_COLUMN_COUNT; col++){
        GridPane viewContainerPane = new GridPane();
        pane.add(viewContainerPane, col, row);
        viewContainers.add(new ViewContainer(this, viewContainerPane, GRID_COLUMN_COUNT * row + col, viewNamesMap.keySet()));
      }
    }

    //initially set all panes invisible
    historyDisplay.getPane().setVisible(false);
    variablesDisplay.getPane().setVisible(false);
    userCommandsDisplay.getPane().setVisible(false);
    buttonDisplay.getPane().setVisible(false);
    turtleStateDisplay.getPane().setVisible(false);
  }

  //clickedOn,   currentINdex
  //commands -> variables

  public void updateViewLayouts(int clickedIndex, String viewName){
    int currentIndex = findIndexOf(viewName);

    if(currentIndex != -1) {
      String nameToBeSwapped = viewOrder[clickedIndex];
      turnOffView(viewNamesMap.get(viewName), currentIndex);
      if(nameToBeSwapped != null){
        turnOnView(viewNamesMap.get(nameToBeSwapped), currentIndex);
      }
      viewOrder[currentIndex] = viewOrder[clickedIndex];
      viewContainers.get(currentIndex).updateComboBox(viewOrder[clickedIndex]);
    }
    if(viewContainers.get(clickedIndex).getPane().getChildren().size() > 2){
      turnOffView(viewNamesMap.get(viewOrder[clickedIndex]), clickedIndex);
    }
    viewOrder[clickedIndex] = viewName;
    viewContainers.get(clickedIndex).updateComboBox(viewName);
    if(viewName != null && viewContainers.get(clickedIndex).getPane().getChildren().size() <= 2){
      turnOnView(viewNamesMap.get(viewName), clickedIndex);
    }
//
//
//    // set visible & position views
//    for(String name : viewOrder){
//      System.out.printf("%s, ", name);
//    }
//    System.out.println();
  }

  private void turnOnView(Pane targetPane, int index){
    viewContainers.get(index).getPane().add(targetPane, 0, 1, 1, 1);
    targetPane.setVisible(true);
  }

  private void turnOffView(Pane targetPane, int index){
    viewContainers.get(index).getPane().getChildren().remove(targetPane);
    targetPane.setVisible(false);
  }

  private int findIndexOf(String desiredView){
    for(int i = 0; i < viewOrder.length; i++) {
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
