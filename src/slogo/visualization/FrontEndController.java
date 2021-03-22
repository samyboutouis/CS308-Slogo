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

public class FrontEndController {
  private static final String IMAGE_FILE_CHOOSER_TITLE = "Open Image File";
  private static final String XML_FILE_CHOOSER_TITLE = "Open XML File";
  private static final String INITIAL_DIRECTORY = "src/resources/preferences";
  private static final String DIALOG_HEADER_TEXT = "Create XML File";
  private static final String ERROR_MESSAGE = "Invalid file.";
  private static final String LIGHT_STYLESHEET = "light.css";
  private static final String DARK_STYLESHEET = "dark.css";

  private final Stage stage;
  private final FrontEndTurtleTracker frontEndTurtleTracker;
  private final ButtonFactory buttonFactory;
  private final Controller controller;
  private final Workspace workspace;

  public FrontEndController(Stage stage, FrontEndTurtleTracker frontEndTurtleTracker,
    Controller controller, Workspace workspace) {
    this.stage = stage;
    this.frontEndTurtleTracker = frontEndTurtleTracker;
    this.buttonFactory = new ButtonFactory(this);
    this.controller = controller;
    this.workspace = workspace;
  }

  public void handleAddTurtleClick(Button addTurtleButton) {
    FrontEndTurtle turtle = new FrontEndTurtle(frontEndTurtleTracker);
    frontEndTurtleTracker.addTurtle(turtle);
  }

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

  public void handleRightClick(TextField textField) {
    frontEndTurtleTracker.rotate(Double.parseDouble(textField.getText()));
    textField.clear();
  }

  public void handleLeftClick(TextField textField) {
    frontEndTurtleTracker.rotate(-Double.parseDouble(textField.getText()));
    textField.clear();
  }

  public void handleUpClick(TextField textField) {
    frontEndTurtleTracker.forward(Double.parseDouble(textField.getText()));
    textField.clear();
  }

  public void handleDownClick(TextField textField) {
    frontEndTurtleTracker.back(Double.parseDouble(textField.getText()));
    textField.clear();
  }

  public void handlePenUpClick(Button button, Turtle turtle) {
    buttonFactory.setImage(button, "PenDownButton");
    turtle.penUp();
    button.setOnAction(event -> handlePenDownClick(button, turtle));
  }

  public void handlePenDownClick(Button button, Turtle turtle) {
    buttonFactory.setImage(button, "PenUpButton");
    turtle.penDown();
    button.setOnAction(event -> handlePenUpClick(button, turtle));
  }

  public void handleNewWorkspaceClick() {
    Main main = new Main();
    main.createNewWorkspace();
  }

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

  public void handleColorThemeClick(Button button) {
    buttonFactory.setImage(button, "DarkThemeButton");
    workspace.setStyleSheet(LIGHT_STYLESHEET);
    button.setOnAction(event -> handleDarkThemeClick(button));
  }

  public void handleDarkThemeClick(Button button) {
    buttonFactory.setImage(button, "ColorThemeButton");
    workspace.setStyleSheet(DARK_STYLESHEET);
    button.setOnAction(event -> handleColorThemeClick(button));
  }
}
