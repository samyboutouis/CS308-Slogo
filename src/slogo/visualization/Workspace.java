package slogo.visualization;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.FrontEndTurtleTracker;
import slogo.controller.Controller;
import slogo.controller.FrontEndController;

public class Workspace {

  private final static int GRID_ROW_COUNT = 10;
  private final static int GRID_COLUMN_COUNT = 5;
  private final static int PADDING_LENGTH = 10;
  private final static String RESOURCE_PACKAGE = "resources";
  private final static int[] paneIndexes = {0, 0, 5, 1, 0, 1, 2, 7, 0, 8, 2, 2, 2, 1, 3, 9};

  private final Stage stage;
  private final Scene scene;
  private final CustomGridPane pane;
  private final Controller controller;
  private FrontEndController frontEndController;

  public Workspace(Pane root, Scene scene, Stage stage) {
    this.scene = scene;
    this.stage = stage;
    controller = new Controller();
    pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    root.getChildren().add(pane);
    pane.setPrefSize(scene);
    setupDisplays();
    setStyleSheet();
  }


  private void setupDisplays() {
    FrontEndTurtleTracker frontEndTurtleTracker = new FrontEndTurtleTracker();
    frontEndController = new FrontEndController(stage, frontEndTurtleTracker);

    HistoryDisplay historyDisplay = new HistoryDisplay(RESOURCE_PACKAGE);
    VariablesDisplay variablesDisplay = new VariablesDisplay(RESOURCE_PACKAGE);
    UserCommandsDisplay userCommandsDisplay = new UserCommandsDisplay(RESOURCE_PACKAGE);
    ButtonDisplay buttonDisplay = new ButtonDisplay(frontEndController);
    //paletteDisplay
    TurtleStateDisplay turtleStateDisplay = new TurtleStateDisplay(frontEndController,
      frontEndTurtleTracker);
    frontEndTurtleTracker.addObserver(turtleStateDisplay);

    TurtleDisplay turtleDisplay = new TurtleDisplay();
    TerminalDisplay terminalDisplay = new TerminalDisplay(RESOURCE_PACKAGE, historyDisplay,
      frontEndTurtleTracker, variablesDisplay, controller, turtleDisplay);
    ToolbarDisplay toolbarDisplay = new ToolbarDisplay(RESOURCE_PACKAGE, controller,
      frontEndController);
    ViewLayout viewLayout = new ViewLayout(historyDisplay, variablesDisplay, userCommandsDisplay,
      buttonDisplay, turtleStateDisplay, frontEndController);

    frontEndController.setToolbarDisplay(toolbarDisplay);
    frontEndController.setTurtleDisplay(turtleDisplay);

    pane.add(toolbarDisplay.getPane(), paneIndexes[0], paneIndexes[1], paneIndexes[2],
      paneIndexes[3]);
    pane
      .add(turtleDisplay.getPane(), paneIndexes[4], paneIndexes[5], paneIndexes[6], paneIndexes[7]);
    pane.add(terminalDisplay.getPane(), paneIndexes[8], paneIndexes[9], paneIndexes[10],
      paneIndexes[11]);
    pane.add(viewLayout.getPane(), paneIndexes[12], paneIndexes[13], paneIndexes[14],
      paneIndexes[15]);

    pane.add(historyDisplay.getPane(), 0, 0, 1, 1);
  }

  private void setStyleSheet() {
    scene.getStylesheets().add(
      getClass().getResource(String.format("/%s/stylesheets/%s", RESOURCE_PACKAGE, "default.css"))
        .toExternalForm());
  }
}
