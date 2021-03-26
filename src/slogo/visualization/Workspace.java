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

/**
 * The Workspace class is responsible for initializing and setting up all of the main display
 * views found in the application, including the palette, history, variables, user-commands,
 * button, turtle states, turtle, terminal, and toolbar displays. Additionally, it initializes
 * the front-end turtle tracker and front-end controller objects, which allow for communication
 * between the front-end and back-end.
 *
 * @author Samy Boutouis
 * @author Donghan Park
 */
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

  /**
   * Constructor that creates an instance of the Workspace object.
   * @param root Main root pane of the application window
   * @param scene Main scene of the application window
   * @param stage Main stage of the application window
   */
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
    terminalDisplay = new TerminalDisplay(scene, historyDisplay,
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

  /**
   * Sets the stylesheet that is being used to style all UI components in the application.
   * @param styleSheetName Name of the new stylesheet
   */
  public void setStyleSheet(String styleSheetName) {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(
        getClass().getResource(String.format("%s/%s", STYLESHEETS_PACKAGE, styleSheetName))
            .toExternalForm());
    stylesheet = styleSheetName;
  }

  /**
   * Returns the reference to the terminal display view object.
   * @return Reference to the terminal display view object
   */
  public TerminalDisplay getTerminalDisplay() {
    return terminalDisplay;
  }

  /**
   * Returns the name of the stylesheet currently being used by the application.
   * @return Name of stylesheet currently being used by the application
   */
  public String getStylesheet() {
    return stylesheet;
  }
}
