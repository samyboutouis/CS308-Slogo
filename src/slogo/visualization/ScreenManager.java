package slogo.visualization;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ScreenManager {

  private final GridPane gridPane;
  private final static int GRID_LENGTH = 10;

  public ScreenManager(Pane root, Scene scene){
    gridPane = new GridPane();

    root.getChildren().add(gridPane);
    gridPane.setMinSize(scene.getWidth(), scene.getHeight());
    gridPane.setGridLinesVisible(true);

    gridPane.setVgap(10);
    gridPane.setHgap(10);

//    scene.heightProperty().addListener((obs, old, nw) -> {
//      gridPane.setPrefHeight(nw.doubleValue());
//    });
//
//    scene.widthProperty().addListener((obs, old, nw) -> {
//      gridPane.setPrefWidth(nw.doubleValue());
//    });

    for(int i = 0; i < GRID_LENGTH; i++){
      RowConstraints row = new RowConstraints();
      ColumnConstraints col = new ColumnConstraints();
      row.setVgrow(Priority.ALWAYS);
      col.setHgrow(Priority.ALWAYS);
      gridPane.getRowConstraints().add(row);
      gridPane.getColumnConstraints().add(col);
    }

    setupWindows();
  }

  private void setupWindows(){
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
