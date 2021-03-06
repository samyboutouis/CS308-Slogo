package slogo.visualization;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ScreenManager {

  private final static int GRID_LENGTH = 10;
  private final static int PADDING_LENGTH = 10;

  private final Scene scene;
  private final GridPane gridPane;

  public ScreenManager(Pane root, Scene scene){
    this.scene = scene;

    gridPane = new GridPane();
    root.getChildren().add(gridPane);

    setupGrid();
    setupDisplays();
  }

  private void setupGrid(){
    initializeGridSize();
    initializeGridResizeListeners();
    initializeGridRowsAndCols();
  }

  private void initializeGridSize(){
    gridPane.setMinSize(scene.getWidth(), scene.getHeight());
    gridPane.setVgap(PADDING_LENGTH);
    gridPane.setHgap(PADDING_LENGTH);
    gridPane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));
  }

  private void initializeGridResizeListeners(){
    scene.heightProperty().addListener((observableValue, oldHeight, newHeight) -> {
      gridPane.setMinHeight(newHeight.doubleValue());
    });

    scene.widthProperty().addListener((observableValue, oldWidth, newWidth) -> {
      gridPane.setMinWidth(newWidth.doubleValue());
    });
  }

  private void initializeGridRowsAndCols(){
    for(int i = 0; i < GRID_LENGTH; i++){
      RowConstraints row = new RowConstraints();
      ColumnConstraints col = new ColumnConstraints();
      row.setVgrow(Priority.ALWAYS);
      col.setHgrow(Priority.ALWAYS);
      gridPane.getRowConstraints().add(row);
      gridPane.getColumnConstraints().add(col);
    }
  }

  private void setupDisplays(){
    Pane turtlePane = new Pane();
    Pane terminalPane = new Pane();
    VBox historyPane = new VBox();
    VBox variablesPane = new VBox();
    VBox userCommandsPane = new VBox();
    HBox toolbarPane = new HBox();

    gridPane.add(toolbarPane, 0, 0, 10, 1);
    gridPane.add(turtlePane, 0, 1, 6, 7);
    gridPane.add(terminalPane, 0, 8, 6, 2);
    gridPane.add(historyPane, 6, 1, 4, 5);
    gridPane.add(variablesPane, 6, 6, 2, 4);
    gridPane.add(userCommandsPane, 8, 6, 2, 4);

    new TurtleDisplay(turtlePane);
    new TerminalDisplay(terminalPane);
    new HistoryDisplay(historyPane);
    new VariablesDisplay(variablesPane);
    new UserCommandsDisplay(userCommandsPane);
    new ToolbarDisplay(toolbarPane);
  }
}
