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
  private final static String DISPLAY_CLASS_NAME = "displayWindow";

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
    AnchorPane turtlePane = new AnchorPane();
    GridPane terminalPane = new GridPane();
    GridPane toolbarPane = new GridPane();
    frontEndTurtle = new FrontEndTurtle();
    GridPane viewLayoutPane = new GridPane();
    //FIXME: Delete later
    GridPane historyPane = new GridPane();
    GridPane variablesPane = new GridPane();

    turtlePane.getStyleClass().add(DISPLAY_CLASS_NAME);
    terminalPane.getStyleClass().add(DISPLAY_CLASS_NAME);
    toolbarPane.getStyleClass().add(DISPLAY_CLASS_NAME);
    viewLayoutPane.getStyleClass().add(DISPLAY_CLASS_NAME);

    gridPane.add(toolbarPane, 0, 0, 5, 1);
    gridPane.add(turtlePane, 0, 1, 2, 7);
    gridPane.add(terminalPane, 0, 8, 2, 2);
    gridPane.add(viewLayoutPane, 2, 1, 3, 9);

    frontEndController = new FrontEndController(stage);
    new TerminalDisplay(terminalPane, RESOURCE_PACKAGE, new HistoryDisplay(historyPane, RESOURCE_PACKAGE),
      frontEndTurtle, new VariablesDisplay(variablesPane, RESOURCE_PACKAGE), controller);
//    new UserCommandsDisplay(userCommandsPane, RESOURCE_PACKAGE);
    TurtleDisplay turtleDisplay = new TurtleDisplay(turtlePane, frontEndTurtle);
    ToolbarDisplay toolbarDisplay = new ToolbarDisplay(toolbarPane, RESOURCE_PACKAGE, controller, frontEndController);
    frontEndController.setToolbarDisplay(toolbarDisplay);
    frontEndController.setTurtleDisplay(turtleDisplay);

    new ViewLayout(viewLayoutPane);
  }

  private void setStyleSheet() {
    scene.getStylesheets().add(
      getClass().getResource(String.format("/%s/stylesheets/%s", RESOURCE_PACKAGE, "default.css"))
        .toExternalForm());
  }
}
