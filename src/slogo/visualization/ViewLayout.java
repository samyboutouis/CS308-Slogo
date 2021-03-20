package slogo.visualization;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import slogo.controller.FrontEndController;

public class ViewLayout {

  private final static int GRID_ROW_COUNT = 2;
  private final static int GRID_COLUMN_COUNT = 3;
  private final static int PADDING_LENGTH = 10;

  private final List<ViewContainer> viewContainers = new ArrayList<>();
  private final CustomGridPane pane;
  private final FrontEndController frontEndController;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final UserCommandsDisplay userCommandsDisplay;
  private String[] viewOrder;

  private final static int[] viewContainerXPositions = {};
  private final static int[] viewContainerYPositions = {};

  public ViewLayout(HistoryDisplay historyDisplay, VariablesDisplay variablesDisplay, UserCommandsDisplay userCommandsDisplay, FrontEndController frontEndController){
    this.pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    this.frontEndController = frontEndController;
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.userCommandsDisplay = userCommandsDisplay;

    setupViewContainers();
    initializeViewOrder();
  }

  private void initializeViewOrder(){
    viewOrder = new String[GRID_COLUMN_COUNT * GRID_ROW_COUNT];
  }

  private void setupViewContainers(){
    for(int row = 0; row < GRID_ROW_COUNT; row++){
      for(int col = 0; col < GRID_COLUMN_COUNT; col++){
        GridPane viewContainerPane = new GridPane();
        pane.add(viewContainerPane, col, row);
        viewContainers.add(new ViewContainer(this, viewContainerPane, GRID_COLUMN_COUNT * row + col));
      }
    }

    //initially set all panes invisible
    historyDisplay.getPane().setVisible(false);
    variablesDisplay.getPane().setVisible(false);
    userCommandsDisplay.getPane().setVisible(false);
  }

  public void updateViewLayouts(int containerIndex, String viewName){
    int currentIndex = findIndexOf(viewName);
    if(currentIndex != -1) {
      viewOrder[currentIndex] = viewOrder[containerIndex];
      viewContainers.get(currentIndex).updateComboBox(viewOrder[containerIndex]);
    }
    viewOrder[containerIndex] = viewName;
    viewContainers.get(containerIndex).updateComboBox(viewName);


    // set visible & position views
    for(String name : viewOrder){
      System.out.printf("%s, ", name);
    }
    System.out.println();
  }

  private void turnOnView(Pane targetPane, int index){
    targetPane.setLayoutX(viewContainerXPositions[index]);
    targetPane.setLayoutY(viewContainerYPositions[index]);
    targetPane.setVisible(true);
  }

  private void turnOffView(Pane targetPane){
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
