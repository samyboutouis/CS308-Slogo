package slogo.visualization;

import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ViewLayout {

  private final static int GRID_ROW_COUNT = 2;
  private final static int GRID_COLUMN_COUNT = 3;
  private final static int PADDING_LENGTH = 10;
  private final static String DISPLAY_CLASS_NAME = "displayWindow";

  private final GridPane pane;

  public ViewLayout(GridPane pane){
    this.pane = pane;

    setupGrid();
    setupViews();
    setupViewContainers();
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
        new ViewContainer(viewContainerPane);
      }
    }
  }
}
