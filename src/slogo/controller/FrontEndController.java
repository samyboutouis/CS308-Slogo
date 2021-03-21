package slogo.controller;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.FrontEndTurtleTracker;
import slogo.Main;
import slogo.SafeTurtle;
import slogo.visualization.ButtonFactory;
import slogo.visualization.FrontEndTurtle;
import slogo.visualization.PaletteDisplay;
import slogo.visualization.Workspace;

public class FrontEndController {
  private static final String DIALOG_HEADER_TEXT = "Create XML File";
  private static final String ERROR_MESSAGE = "Invalid file.";

  private final Stage stage;
  private final FrontEndTurtleTracker frontEndTurtleTracker;
  private final ButtonFactory buttonFactory;
  private final PaletteDisplay paletteDisplay;
  private final Controller controller;
  private final Workspace workspace;

  public FrontEndController(Stage stage, FrontEndTurtleTracker frontEndTurtleTracker, Controller controller, PaletteDisplay paletteDisplay, Workspace workspace) {
    this.stage = stage;
    this.frontEndTurtleTracker = frontEndTurtleTracker;
    this.buttonFactory = new ButtonFactory(this);
    this.paletteDisplay = paletteDisplay;
    this.controller = controller;
    this.workspace = workspace;
  }

  public void handleAddTurtleClick(Button addTurtleButton) {
    FrontEndTurtle turtle = new FrontEndTurtle(frontEndTurtleTracker);
    frontEndTurtleTracker.addTurtle(turtle);
  }

  public void handleTurtleImageClick(Button button, SafeTurtle safeTurtle) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Image File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters()
        .setAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.svg"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      //paletteDisplay.getImagePathFromIndex();
      safeTurtle.setImage(file);
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

  public void handlePenUpClick(Button button, SafeTurtle turtle) {
    buttonFactory.setImage(button, "PenDownButton");
    turtle.penUp();
    button.setOnAction(event -> handlePenDownClick(button, turtle));
  }

  public void handlePenDownClick(Button button, SafeTurtle turtle) {
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
    fileChooser.setTitle("Open XML File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters()
      .setAll(new ExtensionFilter("XML File", "*.xml"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      try {
        XMLParser xmlParser = new XMLParser(file);
        frontEndTurtleTracker.notifyBackgroundObservers(xmlParser.getBackgroundColor());
        controller.setTranslatedLanguage(xmlParser.getLanguage());
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
      XMLCreator xmlCreator = new XMLCreator(frontEndTurtleTracker, controller, newValue);
    }
  }

  public void handleColorThemeClick(Button button) {
    buttonFactory.setImage(button, "DarkThemeButton");
    workspace.setStyleSheet("default.css");
    button.setOnAction(event -> handleDarkThemeClick(button));
  }

  public void handleDarkThemeClick(Button button) {
    buttonFactory.setImage(button, "ColorThemeButton");
    workspace.setStyleSheet("dark.css");
    button.setOnAction(event -> handleColorThemeClick(button));
  }
}
