package slogo.controller;

import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.FrontEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;
import slogo.visualization.ToolbarDisplay;
import slogo.visualization.TurtleDisplay;

public class FrontEndController {
  private static final String COLOR_PICKER_ID = "ColorPicker";

  private final Stage stage;
  private Color backgroundColor;
  private Color penColor;
  private TurtleDisplay turtleDisplay;
  private ToolbarDisplay toolbarDisplay;
  private FrontEndTurtleTracker frontEndTurtleTracker;
  private FrontEndTurtle turtle;

  public FrontEndController(Stage stage, FrontEndTurtleTracker frontEndTurtleTracker) {
    this.stage = stage;
    this.frontEndTurtleTracker = frontEndTurtleTracker;
    backgroundColor = Color.web("#dedcdc");
    penColor = Color.BLACK;
  }

  public void handleAddTurtleClick(Button addTurtleButton) {
    turtle = new FrontEndTurtle(frontEndTurtleTracker);
    turtleDisplay.addTurtle(turtle);
    frontEndTurtleTracker.addTurtle(turtle);
  }

  public void handlePenColorClick(Button penColorButton) {
    penColorButton.setVisible(false);
    penColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(penColor);
    colorPicker.setId(COLOR_PICKER_ID);
    colorPicker.setOnAction(event -> handlePenColorPicker(penColorButton, colorPicker));
    toolbarDisplay.getPane().add(colorPicker, 3, 0, 1, 1);
  }

  private void handlePenColorPicker(Button penColorButton, ColorPicker colorPicker) {
    penColor = colorPicker.getValue();
    turtle.setPenColor(penColor);
    toolbarDisplay.getPane().getChildren().remove(colorPicker);
    penColorButton.setVisible(true);
    penColorButton.setDisable(false);
  }

  public void handleBackgroundColorClick(Button backgroundColorButton) {
    backgroundColorButton.setVisible(false);
    backgroundColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(backgroundColor);
    colorPicker.setId(COLOR_PICKER_ID);
    colorPicker
      .setOnAction(event -> handleBackgroundColorPicker(backgroundColorButton, colorPicker));
    toolbarDisplay.getPane().add(colorPicker, 1, 0, 1, 1);
  }

  private void handleBackgroundColorPicker(Button backgroundColorButton, ColorPicker colorPicker) {
    backgroundColor = colorPicker.getValue();
    turtleDisplay.setBackgroundColor(backgroundColor);
    toolbarDisplay.getPane().getChildren().remove(colorPicker);
    backgroundColorButton.setVisible(true);
    backgroundColorButton.setDisable(false);
  }

  public void handleTurtleImageClick(Button button) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Image File");
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    fileChooser.getExtensionFilters()
      .setAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.svg"));
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      turtle.setImage(file);
    }
  }

  public void setToolbarDisplay(ToolbarDisplay toolbarDisplay) {
    this.toolbarDisplay = toolbarDisplay;
  }

  public void setTurtleDisplay(TurtleDisplay turtleDisplay) {
    this.turtleDisplay = turtleDisplay;
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
}
