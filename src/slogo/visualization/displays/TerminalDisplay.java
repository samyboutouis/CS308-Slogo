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

  private static final ResourceBundle idBundle = ResourceBundle
      .getBundle("resources/stylesheets/CSS_IDs");
  private static final ResourceBundle resourceBundle = ResourceBundle
      .getBundle("resources/stylesheets/English");
  private static final ResourceBundle errorBundle = ResourceBundle
      .getBundle("resources/stylesheets/EnglishErrors");
  private static final String LIBRARIES_PATH = "src/resources/libraries";
  private static final String TERMINAL_RUN_BUTTON = "TerminalRunButton";
  private static final String TERMINAL_SAVE_BUTTON = "TerminalSaveButton";
  private static final String TERMINAL_LOAD_BUTTON = "TerminalLoadButton";
  private static final String TERMINAL_PROMPT = "TerminalPrompt";
  private static final String TERMINAL_TEXT_BOX_ID = "TerminalTextBoxID";
  private static final String TERMINAL_BUTTON_ID = "TerminalButtonID";
  private static final String ERROR_TITLE_PROPERTY = "ErrorTitle";
  private static final String DISPLAY_CLASS_NAME = "displayWindow";
  private static final int COLUMN_COUNT = 4;
  private static final int PADDING_LENGTH = 10;
  private static final int BUTTON_WIDTH = 80;

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

    ctrlPressed = false;

    initializeTextField();
    initializeRunButton();
    initializeSaveButton();
    initializeLoadButton();
    applyTerminalLogic();
  }

  private void initializeTextField() {
    textBox = new TextArea();
    textBox.setPrefColumnCount(COLUMN_COUNT);
    textBox.setWrapText(true);
    textBox.setFocusTraversable(false);
    textBox.setPromptText(resourceBundle.getString(TERMINAL_PROMPT));
    textBox.setId(idBundle.getString(TERMINAL_TEXT_BOX_ID));

    pane.add(textBox, 0, 0, 3, 1);
  }

  private void initializeRunButton() {
    runButton = new Button(resourceBundle.getString(TERMINAL_RUN_BUTTON));
    runButton.setMaxWidth(Double.MAX_VALUE);
    runButton.setMaxHeight(Double.MAX_VALUE);
    runButton.setWrapText(true);
    runButton.setTextAlignment(TextAlignment.CENTER);
    runButton.setId(idBundle.getString(TERMINAL_BUTTON_ID));

    pane.add(runButton, 3, 0, 1, 1);
  }

  private void initializeSaveButton() {
    saveButton = new Button(resourceBundle.getString(TERMINAL_SAVE_BUTTON));
    saveButton.setMaxWidth(BUTTON_WIDTH);
    saveButton.setTranslateX(-25);
    saveButton.setTranslateY(50);

    applySaveButtonLogic();

    pane.add(saveButton, 2, 0);
  }

  private void initializeLoadButton() {
    loadButton = new Button(resourceBundle.getString(TERMINAL_LOAD_BUTTON));
    loadButton.setMaxWidth(BUTTON_WIDTH);
    loadButton.setTranslateX(60);
    loadButton.setTranslateY(50);

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
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SLogo File", ".slogo"));
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
    runButton.setOnAction(e -> {
      sendCommandToController();
    });

    scene.setOnKeyPressed(e -> {
      applyKeyPressedLogic(e, true);
    });

    scene.setOnKeyReleased(e -> {
      applyKeyPressedLogic(e, false);
    });
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
    newAlert.setTitle(errorBundle.getString(ERROR_TITLE_PROPERTY));
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