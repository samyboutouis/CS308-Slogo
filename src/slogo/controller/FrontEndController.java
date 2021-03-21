package slogo.controller;

import java.io.File;
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

public class FrontEndController {
  private static final String DIALOG_HEADER_TEXT = "Create XML File";

  private final Stage stage;
  private final FrontEndTurtleTracker frontEndTurtleTracker;
  private final ButtonFactory buttonFactory;
  private final PaletteDisplay paletteDisplay;
  private final Controller controller;

  public FrontEndController(Stage stage, FrontEndTurtleTracker frontEndTurtleTracker, Controller controller, PaletteDisplay paletteDisplay) {
    this.stage = stage;
    this.frontEndTurtleTracker = frontEndTurtleTracker;
    this.buttonFactory = new ButtonFactory(this);
    this.paletteDisplay = paletteDisplay;
    this.controller = controller;
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
}
