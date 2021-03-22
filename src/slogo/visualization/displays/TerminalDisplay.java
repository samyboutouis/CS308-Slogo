package slogo.visualization.displays;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import slogo.controller.Controller;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.AnimationManager;
import slogo.visualization.turtle.FrontEndTurtle;
import slogo.visualization.turtle.FrontEndTurtleTracker;

public class TerminalDisplay {

  private static final ResourceBundle ID_BUNDLE = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle("resources/languages/English");
  private static final ResourceBundle ERROR_BUNDLE = ResourceBundle
      .getBundle("resources/languages/EnglishErrors");
  private static final String LIBRARIES_PATH = "src/resources/libraries";
  private static final String TERMINAL_RUN_BUTTON = "TerminalRunButton";
  private static final String TERMINAL_SAVE_BUTTON = "TerminalSaveButton";
  private static final String TERMINAL_LOAD_BUTTON = "TerminalLoadButton";
  private static final String TERMINAL_PROMPT = "TerminalPrompt";
  private static final String TERMINAL_TEXT_BOX_ID = "TerminalTextBoxID";
  private static final String TERMINAL_BUTTON_ID = "TerminalButtonID";
  private static final String ERROR_TITLE_PROPERTY = "ErrorTitle";
  private static final String DISPLAY_CLASS_NAME = "displayWindow";
  private static final String SAVE_DIALOG_DESC = "SLogo File";
  private static final String SAVE_DIALOG_FORMAT = ".slogo";
  private static final int COLUMN_COUNT = 4;
  private static final int PADDING_LENGTH = 10;
  private static final int BUTTON_WIDTH = 80;
  private static final int SAVE_BUTTON_X = -25;
  private static final int LOAD_BUTTON_X = 60;
  private static final int BUTTON_XY = 50;

  private final Scene scene;
  private final GridPane pane;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final UserCommandsDisplay userCommandsDisplay;
  private final FrontEndTurtleTracker turtleTracker;
  private final Controller controller;

  private TextArea textBox;
  private Button runButton;
  private Button saveButton;
  private Button loadButton;

  private boolean ctrlPressed;

  public TerminalDisplay(Scene scene, HistoryDisplay historyDisplay,
      FrontEndTurtleTracker frontEndTurtleTracker, VariablesDisplay variablesDisplay,
      UserCommandsDisplay userCommandsDisplay, Controller controller) {

    this.scene = scene;
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.userCommandsDisplay = userCommandsDisplay;
    this.turtleTracker = frontEndTurtleTracker;
    this.controller = controller;

    pane = new GridPane();

    initializePane();
    initializeTextField();
    initializeRunButton();
    initializeSaveButton();
    initializeLoadButton();
    applyTerminalLogic();
  }

  private void initializePane(){
    pane.getStyleClass().add(DISPLAY_CLASS_NAME);
    pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    pane.setHgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));

    for (int i = 0; i < COLUMN_COUNT; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      col.setPercentWidth(100.0 / COLUMN_COUNT);
      pane.getColumnConstraints().add(col);
    }
  }

  private void initializeTextField() {
    textBox = new TextArea();
    textBox.setPrefColumnCount(COLUMN_COUNT);
    textBox.setWrapText(true);
    textBox.setFocusTraversable(false);
    textBox.setPromptText(RESOURCE_BUNDLE.getString(TERMINAL_PROMPT));
    textBox.setId(ID_BUNDLE.getString(TERMINAL_TEXT_BOX_ID));
    pane.add(textBox, 0, 0, 3, 1);
  }

  private Button makeButton(String property, double width, double height, int X, int Y){
    Button button = new Button(RESOURCE_BUNDLE.getString(property));
    button.setMaxSize(width, height);
    button.setWrapText(true);
    button.setTextAlignment(TextAlignment.CENTER);
    button.setTranslateX(X);
    button.setTranslateY(Y);
    return button;
  }

  private void initializeRunButton() {
    runButton = makeButton(TERMINAL_RUN_BUTTON, Double.MAX_VALUE, Double.MAX_VALUE,0, 0);
    runButton.setId(ID_BUNDLE.getString(TERMINAL_BUTTON_ID));
    pane.add(runButton, 3, 0, 1, 1);
  }

  private void initializeSaveButton() {
    saveButton = makeButton(TERMINAL_SAVE_BUTTON, BUTTON_WIDTH, 0, SAVE_BUTTON_X, BUTTON_XY);
    applySaveButtonLogic();
    pane.add(saveButton, 2, 0);
  }

  private void initializeLoadButton() {
    loadButton = makeButton(TERMINAL_LOAD_BUTTON, BUTTON_WIDTH, 0, LOAD_BUTTON_X, BUTTON_XY);
    applyLoadButtonLogic();
    pane.add(loadButton, 2, 0);
  }

  private void applyLoadButtonLogic() {
    loadButton.setOnAction(e -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(new File(LIBRARIES_PATH));
      File selectedFile = fileChooser.showOpenDialog(scene.getWindow());

      if (selectedFile != null) {
        try {
          String content = new Scanner(selectedFile).useDelimiter("\\Z").next();
          setTerminalText(content);
        } catch (FileNotFoundException fileNotFoundException) {
          fileNotFoundException.printStackTrace();
        }
      }
    });
  }

  private void applySaveButtonLogic() {
    saveButton.setOnAction(e -> {
      String command = textBox.getText().trim();
      if (command.length() > 0) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(LIBRARIES_PATH));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter(SAVE_DIALOG_DESC, SAVE_DIALOG_FORMAT));
        File saveFile = fileChooser.showSaveDialog(scene.getWindow());

        try {
          PrintWriter printWriter = new PrintWriter(saveFile);
          printWriter.println(command);
          printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
          fileNotFoundException.printStackTrace();
        }
      }
    });
  }

  private void applyTerminalLogic() {
    ctrlPressed = false;
    runButton.setOnAction(e -> sendCommandToController());
    scene.setOnKeyPressed(e -> applyKeyPressedLogic(e, true));
    scene.setOnKeyReleased(e -> applyKeyPressedLogic(e, false));
  }

  private void applyKeyPressedLogic(KeyEvent e, boolean state) {
    if (e.getCode() == KeyCode.CONTROL) {
      ctrlPressed = state;
    } else if (e.getCode() == KeyCode.ENTER && ctrlPressed) {
      sendCommandToController();
    }
  }

  private void sendCommandToController() {
    String command = textBox.getText().trim();
    if (turtleTracker.isEmpty()) {
      FrontEndTurtle turtle = new FrontEndTurtle(turtleTracker);
      turtleTracker.addTurtle(turtle);
    }
    if (command.length() > 0) {
      try {
        BackEndTurtleTracker backEndTurtleTracker = turtleTracker.passToBackEnd();
        new AnimationManager(
            controller.parseProgram(command, backEndTurtleTracker).getAllTurtleCommands(),
            turtleTracker);
        historyDisplay.addNewHistoryTag(command);
        variablesDisplay.updateBox(controller.getVariables());
        userCommandsDisplay.updateBox(controller.getUserDefinedCommands());
      } catch (Exception error) {
        createErrorDialog(error);
      }
    }
    textBox.clear();
  }

  private void createErrorDialog(Exception error) {
    Alert newAlert = new Alert(AlertType.ERROR);
    newAlert.setTitle(ERROR_BUNDLE.getString(ERROR_TITLE_PROPERTY));
    newAlert.setHeaderText(null);
    newAlert.setContentText(error.getMessage());
    newAlert.showAndWait();
  }

  public void setTerminalText(String command) {
    textBox.setText(command);
  }

  public GridPane getPane() {
    return pane;
  }
}