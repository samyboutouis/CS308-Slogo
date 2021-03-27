package slogo.visualization;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.controller.XMLCreator;
import slogo.controller.XMLParser;
import slogo.visualization.turtle.FrontEndTurtleTracker;
import slogo.Main;
import slogo.visualization.turtle.Turtle;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * The FrontEndController class is responsible for handling the responses to clicks from buttons
 * that have changes to the entire workspace. It is dependent on the turtle tracker, workspace,
 * controller, and stage to make necessary changes to the application when handling button clicks.
 *
 * @author Samy Boutouis
 */
public class FrontEndController {

  private static final String IMAGE_FILE_CHOOSER_TITLE = "Open Image File";
  private static final String XML_FILE_CHOOSER_TITLE = "Open XML File";
  private static final String INITIAL_DIRECTORY = "src/resources/preferences";
  private static final String DIALOG_HEADER_TEXT = "Create XML File";
  private static final String ERROR_MESSAGE = "Invalid file.";
  private static final String LIGHT_STYLESHEET = "light.css";
  private static final String DARK_STYLESHEET = "dark.css";
  private static final String PEN_DOWN_BUTTON = "PenDownButton";
  private static final String PEN_UP_BUTTON = "PenUpButton";
  private static final String LIGHT_MODE_BUTTON = "ColorThemeButton";
  private static final String DARK_MODE_BUTTON = "DarkThemeButton";

  private final Stage stage;
  private final FrontEndTurtleTracker frontEndTurtleTracker;
  private final ButtonFactory buttonFactory;
  private final Controller controller;
  private final Workspace workspace;

  /**
   * Constructs object.
   *
   * @param stage Stage that holds the components of the UI
   * @param frontEndTurtleTracker object that contains references to the turtles currently active
   *                              in the workspace
   * @param controller Controller that communicates information between the front-end and back-end
   * @param workspace Contains references to the entire workspace view including different displays
   */
  public FrontEndController(Stage stage, FrontEndTurtleTracker frontEndTurtleTracker,
      Controller controller, Workspace workspace) {
    this.stage = stage;
    this.frontEndTurtleTracker = frontEndTurtleTracker;
    this.buttonFactory = new ButtonFactory(this);
    this.controller = controller;
    this.workspace = workspace;
  }

  /**
   * Creates a front-end turtle and adds it to the screen and turtle tracker
   *
   * @param addTurtleButton Button that adds turtle to screen when clicked
   */
  public void handleAddTurtleClick(Button addTurtleButton) {
    FrontEndTurtle turtle = new FrontEndTurtle(frontEndTurtleTracker);
    frontEndTurtleTracker.addTurtle(turtle);
  }

  /**
   * Event handler that changes the image of a selected turtle
   *
   * @param button Button that changes image of turtle when clicked
   * @param turtle Turtle that is being changed by the image selected
   */
  public void handleTurtleImageClick(Button button, Turtle turtle) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(IMAGE_FILE_CHOOSER_TITLE);
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters()
        .setAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.svg"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      turtle.setImage(file);
    }
  }

  /**
   * Moves all active turtle right by a certain amount on the screen
   *
   * @param textField TextField object that contains user's number value for movement
   */
  public void handleRightClick(TextField textField) {
    frontEndTurtleTracker.rotate(Double.parseDouble(textField.getText()));
    textField.clear();
  }

  /**
   * Moves all active turtle left by a certain amount on the screen
   *
   * @param textField TextField object that contains user's number value for movement
   */
  public void handleLeftClick(TextField textField) {
    frontEndTurtleTracker.rotate(-Double.parseDouble(textField.getText()));
    textField.clear();
  }

  /**
   * Moves all active turtle up by a certain amount on the screen.
   *
   * @param textField TextField object that contains user's number value for movement
   */
  public void handleUpClick(TextField textField) {
    frontEndTurtleTracker.forward(Double.parseDouble(textField.getText()));
    textField.clear();
  }

  /**
   * Moves all active turtle down by a certain amount on the screen.
   *
   * @param textField TextField object that contains user's number value for movement
   */
  public void handleDownClick(TextField textField) {
    frontEndTurtleTracker.back(Double.parseDouble(textField.getText()));
    textField.clear();
  }

  /**
   * Moves the pen up for a selected turtle so lines are not drawn when a turtle moves.
   *
   * @param button Button that register's user's click to move pen up
   * @param turtle Turtle object that is having its pen moved up
   */
  public void handlePenUpClick(Button button, Turtle turtle) {
    buttonFactory.setImage(button, PEN_DOWN_BUTTON);
    button.setId(PEN_DOWN_BUTTON);
    turtle.penUp();
    button.setOnAction(event -> handlePenDownClick(button, turtle));
  }

  /**
   * Moves the pen down for a selected turtle so lines are drawn when a turtle moves.
   *
   * @param button Button that register's user's click to move pen down
   * @param turtle Turtle object that is having its pen moved down
   */
  public void handlePenDownClick(Button button, Turtle turtle) {
    buttonFactory.setImage(button, PEN_UP_BUTTON);
    button.setId(PEN_UP_BUTTON);
    turtle.penDown();
    button.setOnAction(event -> handlePenUpClick(button, turtle));
  }

  /**
   * Method that creates a new workspace view when handler is called from button click.
   */
  public void handleNewWorkspaceClick() {
    Main main = new Main();
    main.createNewWorkspace();
  }

  /**
   * Loads in the preferences from a previous workspace so a new workspace has the previous preferences
   * like background color, language, number of turtles, etc.
   */
  public void handleLoadWorkspaceClick() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(XML_FILE_CHOOSER_TITLE);
    fileChooser.setInitialDirectory(new File(INITIAL_DIRECTORY));
    fileChooser.getExtensionFilters()
        .setAll(new ExtensionFilter("XML File", "*.xml"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      try {
        XMLParser xmlParser = new XMLParser(file);
        setUpWorkspace(xmlParser);
      } catch (Exception e) {
        AlertType type = AlertType.ERROR;
        new Alert(type, ERROR_MESSAGE).showAndWait();
      }
    }
  }

  /**
   * Creates an XML file describing the user's current preferences for a workspace.
   */
  public void handleSaveWorkspaceClick() {
    TextInputDialog textDialog = new TextInputDialog();
    textDialog.setHeaderText(String.format(DIALOG_HEADER_TEXT));
    textDialog.showAndWait();
    String newValue = textDialog.getEditor().getText();
    if (newValue != null) {
      XMLCreator xmlCreator = new XMLCreator(frontEndTurtleTracker, controller, workspace,
          newValue);
    }
  }

  private void setUpWorkspace(XMLParser xmlParser) {
    controller.setTranslatedLanguage(xmlParser.getLanguage());
    workspace.setStyleSheet(xmlParser.getStylesheet());
    frontEndTurtleTracker.notifyBackgroundObservers(xmlParser.getBackgroundColor());
    for (int i = 0; i < xmlParser.getNumTurtles(); i++) {
      FrontEndTurtle turtle = new FrontEndTurtle(frontEndTurtleTracker);
      frontEndTurtleTracker.addTurtle(turtle);
    }
  }

  /**
   * Changes the workspace color theme to light theme.
   *
   * @param button Button that changes the color mode of the workspace to light mode
   */
  public void handleColorThemeClick(Button button) {
    buttonFactory.setImage(button, DARK_MODE_BUTTON);
    workspace.setStyleSheet(LIGHT_STYLESHEET);
    button.setOnAction(event -> handleDarkThemeClick(button));
  }

  /**
   * Changes the workspace color theme to dark theme.
   *
   * @param button Button that changes the color mode of the workspace to dark mode
   */
  public void handleDarkThemeClick(Button button) {
    buttonFactory.setImage(button, LIGHT_MODE_BUTTON);
    workspace.setStyleSheet(DARK_STYLESHEET);
    button.setOnAction(event -> handleColorThemeClick(button));
  }
}
