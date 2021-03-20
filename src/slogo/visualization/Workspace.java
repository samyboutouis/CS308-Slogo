package slogo.visualization;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.controller.FrontEndController;

public class Workspace {

  private final static int GRID_ROW_COUNT = 10;
  private final static int GRID_COLUMN_COUNT = 5;
  private final static int PADDING_LENGTH = 10;
  private final static String RESOURCE_PACKAGE = "resources";

  private final Stage stage;
  private final Scene scene;
  private final GridPane gridPane;
  private final Controller controller;
  private FrontEndController frontEndController;
  private FrontEndTurtle frontEndTurtle;

  public Workspace(Pane root, Scene scene, Stage stage) {
    this.scene = scene;
    this.stage = stage;
    gridPane = new GridPane();
    controller = new Controller();
    root.getChildren().add(gridPane);
    setupGrid();
    setupDisplays();
    setStyleSheet();
  }

  private void setupGrid() {
    initializeGridSize();
    initializeGridRowsAndCols();
  }

  private void initializeGridSize() {
    gridPane.setMinSize(0, 0);
    gridPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    gridPane.setPrefSize(scene.getWidth(), scene.getHeight());
    gridPane.setVgap(PADDING_LENGTH);
    gridPane.setHgap(PADDING_LENGTH);
    gridPane.setPadding(new Insets(PADDING_LENGTH));
  }

  private void initializeGridRowsAndCols() {
    for (int i = 0; i < GRID_ROW_COUNT; i++) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(100.0 / GRID_ROW_COUNT);
      gridPane.getRowConstraints().add(row);
    }
    for (int i = 0; i < GRID_COLUMN_COUNT; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setPercentWidth(100.0 / GRID_COLUMN_COUNT);
      gridPane.getColumnConstraints().add(col);
    }
  }

  private void setupDisplays() {
    frontEndTurtle = new FrontEndTurtle();
    frontEndController = new FrontEndController(stage, frontEndTurtle);
    HistoryDisplay historyDisplay = new HistoryDisplay(RESOURCE_PACKAGE);
    VariablesDisplay variablesDisplay = new VariablesDisplay(RESOURCE_PACKAGE);
    UserCommandsDisplay userCommandsDisplay = new UserCommandsDisplay(RESOURCE_PACKAGE);
    TerminalDisplay terminalDisplay = new TerminalDisplay(RESOURCE_PACKAGE, historyDisplay, frontEndTurtle, variablesDisplay, controller);
    TurtleDisplay turtleDisplay = new TurtleDisplay(frontEndTurtle);
    ToolbarDisplay toolbarDisplay = new ToolbarDisplay(RESOURCE_PACKAGE, controller, frontEndController);
    ViewLayout viewLayout = new ViewLayout(historyDisplay, variablesDisplay, userCommandsDisplay, frontEndController);
    frontEndController.setToolbarDisplay(toolbarDisplay);
    frontEndController.setTurtleDisplay(turtleDisplay);
    gridPane.add(toolbarDisplay.getPane(), 0, 0, 5, 1);
    gridPane.add(turtleDisplay.getPane(), 0, 1, 2, 7);
    gridPane.add(terminalDisplay.getPane(), 0, 8, 2, 2);
    gridPane.add(viewLayout.getPane(), 2, 1, 3, 9);
  }

  private void setStyleSheet() {
    scene.getStylesheets().add(
      getClass().getResource(String.format("/%s/stylesheets/%s", RESOURCE_PACKAGE, "default.css"))
        .toExternalForm());
  }
}
