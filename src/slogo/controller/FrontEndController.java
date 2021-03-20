package slogo.controller;

import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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
  private FrontEndTurtle turtle;

  public FrontEndController(Stage stage, FrontEndTurtle frontEndTurtle) {
    this.stage = stage;
    backgroundColor = Color.web("#dedcdc");
    penColor = Color.BLACK;
    turtle = frontEndTurtle;
  }

  public void handleAddTurtleClick(Button addTurtleButton) {
    turtleDisplay.addTurtle();
  }

  public void handlePenColorClick(Button penColorButton) {
    penColorButton.setVisible(false);
    penColorButton.setDisable(true);
    ColorPicker colorPicker = new ColorPicker(penColor);
    colorPicker.setId(COLOR_PICKER_ID);
    colorPicker.setOnAction(event -> handlePenColorPicker(penColorButton, colorPicker));
    toolbarDisplay.getGridPane().add(colorPicker, 3, 0, 1, 1);
  }

  private void handlePenColorPicker(Button penColorButton, ColorPicker colorPicker) {
    penColor = colorPicker.getValue();
    turtleDisplay.setPenColor(penColor);
    toolbarDisplay.getGridPane().getChildren().remove(colorPicker);
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
    toolbarDisplay.getGridPane().add(colorPicker, 1, 0, 1, 1);
  }

  private void handleBackgroundColorPicker(Button backgroundColorButton, ColorPicker colorPicker) {
    backgroundColor = colorPicker.getValue();
    turtleDisplay.setBackgroundColor(backgroundColor);
    toolbarDisplay.getGridPane().getChildren().remove(colorPicker);
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
      turtleDisplay.setTurtleImage(file);
    }
  }

  public void setToolbarDisplay(ToolbarDisplay toolbarDisplay) {
    this.toolbarDisplay = toolbarDisplay;
  }

  public void setTurtleDisplay(TurtleDisplay turtleDisplay) {
    this.turtleDisplay = turtleDisplay;
  }

  public void handleRightClick(TextField textField) {
    turtle.rotate(Double.parseDouble(textField.getText()));
  }

  public void handleLeftClick(TextField textField) {
    turtle.rotate(-Double.parseDouble(textField.getText()));
  }

  public void handleUpClick(TextField textField) {
    turtle.forward(Double.parseDouble(textField.getText()));
  }

  public void handleDownClick(TextField textField) {
    turtle.back(Double.parseDouble(textField.getText()));
  }
}
