package slogo.visualization;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ViewLayout {

  private final static int GRID_ROW_COUNT = 2;
  private final static int GRID_COLUMN_COUNT = 3;
  private final static int PADDING_LENGTH = 10;
  private final static String DISPLAY_CLASS_NAME = "displayWindow";
  private final List<ViewContainer> viewContainers = new ArrayList<>();

  private final GridPane pane;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final UserCommandsDisplay userCommandsDisplay;

  private String[] viewOrder;

  public ViewLayout(GridPane pane, HistoryDisplay historyDisplay, VariablesDisplay variablesDisplay, UserCommandsDisplay userCommandsDisplay){
    this.pane = pane;
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.userCommandsDisplay = userCommandsDisplay;

    setupGrid();
    setupViews();
    setupViewContainers();
    initializeViewOrder();
  }

  private void initializeViewOrder(){
    viewOrder = new String[GRID_COLUMN_COUNT * GRID_ROW_COUNT];
  }

  private void setupGrid() {
    initializeGridSize();
    initializeGridRowsAndCols();
  }

  private void initializeGridSize() {
    pane.setMinSize(0, 0);
    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    pane.setVgap(PADDING_LENGTH);
    pane.setHgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH));
  }

  private void initializeGridRowsAndCols() {
    for (int i = 0; i < GRID_ROW_COUNT; i++) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(100.0 / GRID_ROW_COUNT);
      pane.getRowConstraints().add(row);
    }

    for (int i = 0; i < GRID_COLUMN_COUNT; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setPercentWidth(100.0 / GRID_COLUMN_COUNT);
      pane.getColumnConstraints().add(col);
    }
  }

  private void setupViews(){
    GridPane historyPane = new GridPane();
    GridPane variablesPane = new GridPane();
    GridPane userCommandsPane = new GridPane();

    historyPane.getStyleClass().add(DISPLAY_CLASS_NAME);
    variablesPane.getStyleClass().add(DISPLAY_CLASS_NAME);
    userCommandsPane.getStyleClass().add(DISPLAY_CLASS_NAME);
  }

  private void setupViewContainers(){
    for(int row = 0; row < GRID_ROW_COUNT; row++){
      for(int col = 0; col < GRID_COLUMN_COUNT; col++){
        GridPane viewContainerPane = new GridPane();
        pane.add(viewContainerPane, col, row);
        viewContainers.add(new ViewContainer(this, viewContainerPane, GRID_COLUMN_COUNT * row + col));
      }
    }
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

  private int findIndexOf(String desiredView){
    for(int i = 0; i < viewOrder.length; i++) {
      if (viewOrder[i] != null && viewOrder[i].equals(desiredView)) {
        return i;
      }
    }
    return -1;
  }
}
