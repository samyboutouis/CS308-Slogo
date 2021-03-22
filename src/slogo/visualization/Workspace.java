package slogo.visualization;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.visualization.displays.ButtonDisplay;
import slogo.visualization.displays.HistoryDisplay;
import slogo.visualization.displays.PaletteDisplay;
import slogo.visualization.displays.TerminalDisplay;
import slogo.visualization.displays.ToolbarDisplay;
import slogo.visualization.displays.TurtleDisplay;
import slogo.visualization.displays.TurtleStateDisplay;
import slogo.visualization.displays.UserCommandsDisplay;
import slogo.visualization.displays.VariablesDisplay;
import slogo.visualization.turtle.FrontEndTurtleTracker;

public class Workspace {

  private final static String WORKSPACE_PANE_ID = "Workspace";
  private final static String RESOURCE_PACKAGE = "resources";
  private final static String STYLESHEETS_PACKAGE = "/resources/stylesheets";
  private final static int GRID_ROW_COUNT = 10;
  private final static int GRID_COLUMN_COUNT = 5;
  private final static int PADDING_LENGTH = 10;
  private final static int[] paneIndexes = {0, 0, 5, 1, 0, 1, 2, 7, 0, 8, 2, 2, 2, 1, 3, 9};
  private final static String DEFAULT_STYLESHEET = "dark.css";

  private final Stage stage;
  private final Scene scene;
  private final CustomGridPane pane;
  private final Controller controller;
  private TerminalDisplay terminalDisplay;
  private String stylesheet;

  public Workspace(Pane root, Scene scene, Stage stage) {
    this.scene = scene;
    this.stage = stage;
    controller = new Controller();
    pane = new CustomGridPane(GRID_ROW_COUNT, GRID_COLUMN_COUNT, PADDING_LENGTH);
    pane.setId(WORKSPACE_PANE_ID);
    root.getChildren().add(pane);
    pane.setPrefSize(scene);
    setupDisplays();
    setStyleSheet(DEFAULT_STYLESHEET);
  }

  private void setupDisplays() {
    PaletteDisplay paletteDisplay = new PaletteDisplay(this);
    FrontEndTurtleTracker frontEndTurtleTracker = new FrontEndTurtleTracker(paletteDisplay);
    FrontEndController frontEndController = new FrontEndController(stage, frontEndTurtleTracker,
      controller, this);
    HistoryDisplay historyDisplay = new HistoryDisplay(this);
    VariablesDisplay variablesDisplay = new VariablesDisplay(this);
    UserCommandsDisplay userCommandsDisplay = new UserCommandsDisplay(this);
    ButtonDisplay buttonDisplay = new ButtonDisplay(this, frontEndController);
    TurtleStateDisplay turtleStateDisplay = new TurtleStateDisplay(frontEndController,
        frontEndTurtleTracker);
    TurtleDisplay turtleDisplay = new TurtleDisplay(frontEndTurtleTracker);
    terminalDisplay = new TerminalDisplay(RESOURCE_PACKAGE, scene, historyDisplay,
        frontEndTurtleTracker, variablesDisplay, userCommandsDisplay, controller);
    ToolbarDisplay toolbarDisplay = new ToolbarDisplay(RESOURCE_PACKAGE, controller,
        frontEndController, frontEndTurtleTracker);
    ViewLayout viewLayout = new ViewLayout(historyDisplay, variablesDisplay, userCommandsDisplay,
        paletteDisplay, buttonDisplay, turtleStateDisplay);

    addDisplaysToWorkspace(toolbarDisplay.getPane(), turtleDisplay.getPane(),
        terminalDisplay.getPane(), viewLayout.getPane());
  }

  private void addDisplaysToWorkspace(GridPane toolbarPane, AnchorPane turtlePane,
      GridPane terminalPane, GridPane viewLayoutPane) {
    pane.add(toolbarPane, paneIndexes[0], paneIndexes[1], paneIndexes[2], paneIndexes[3]);
    pane.add(turtlePane, paneIndexes[4], paneIndexes[5], paneIndexes[6], paneIndexes[7]);
    pane.add(terminalPane, paneIndexes[8], paneIndexes[9], paneIndexes[10], paneIndexes[11]);
    pane.add(viewLayoutPane, paneIndexes[12], paneIndexes[13], paneIndexes[14], paneIndexes[15]);
  }

  public void setStyleSheet(String styleSheetName) {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(
      getClass().getResource(String.format("%s/%s", STYLESHEETS_PACKAGE, styleSheetName))
        .toExternalForm());
    stylesheet = styleSheetName;
  }

  public TerminalDisplay getTerminalDisplay() {
    return terminalDisplay;
  }

  public String getStylesheet() {
    return stylesheet;
  }
}
