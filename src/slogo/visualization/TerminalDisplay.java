package slogo.visualization;

import java.util.ResourceBundle;
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
import slogo.FrontEndTurtleTracker;
import slogo.controller.Controller;
import slogo.model.BackEndTurtleTracker;

public class TerminalDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static String TERMINAL_BUTTON = "TerminalButton";
  private final static String TERMINAL_PROMPT = "TerminalPrompt";
  private final static String TERMINAL_TEXT_BOX_ID = "TerminalTextBoxID";
  private final static String TERMINAL_BUTTON_ID = "TerminalButtonID";
  private final static String ERROR_TITLE_PROPERTY = "ErrorTitle";
  private final static String DISPLAY_CLASS_NAME = "displayWindow";
  private final static int COLUMN_COUNT = 4;

  private final Scene scene;
  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle errorBundle;
  private final GridPane pane;
  private TextArea textBox;
  private Button button;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final UserCommandsDisplay userCommandsDisplay;

  private final FrontEndTurtleTracker turtleTracker;
  private final Controller controller;

  private boolean ctrlPressed;

  public TerminalDisplay(String resourcePackage, Scene scene, HistoryDisplay historyDisplay,
    FrontEndTurtleTracker frontEndTurtleTracker, VariablesDisplay variablesDisplay,
    UserCommandsDisplay userCommandsDisplay, Controller controller) {

    pane = new GridPane();
    pane.getStyleClass().add(DISPLAY_CLASS_NAME);
    this.scene = scene;
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.userCommandsDisplay = userCommandsDisplay;
    this.turtleTracker = frontEndTurtleTracker;
    this.controller = controller;

    pane.setMaxWidth(Double.MAX_VALUE);
    pane.setMaxHeight(Double.MAX_VALUE);

    pane.setHgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));

    String language = "English";
    this.resourceBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));
    this.idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "stylesheets", "CSS_IDs"));
    this.errorBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language + "Errors"));

    for (int i = 0; i < COLUMN_COUNT; i++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      col.setPercentWidth(100.0 / COLUMN_COUNT);
      pane.getColumnConstraints().add(col);
    }

    ctrlPressed = false;

    initializeTextField();
    initializeButton();
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

  private void initializeButton() {
    button = new Button(resourceBundle.getString(TERMINAL_BUTTON));
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMaxHeight(Double.MAX_VALUE);
    button.setWrapText(true);
    button.setTextAlignment(TextAlignment.CENTER);
    button.setId(idBundle.getString(TERMINAL_BUTTON_ID));

    pane.add(button, 3, 0, 1, 1);
  }

  private void applyTerminalLogic() {
    button.setOnAction(e -> {
      sendCommandToController();
    });

    scene.setOnKeyPressed(e -> {
      applyKeyPressedLogic(e, true);
    });

    scene.setOnKeyReleased(e -> {
      applyKeyPressedLogic(e, false);
    });
  }

  private void applyKeyPressedLogic(KeyEvent e, boolean state){
    if(e.getCode() == KeyCode.CONTROL){
      ctrlPressed = state;
    } else if (e.getCode() == KeyCode.ENTER && ctrlPressed) {
      sendCommandToController();
    }
  }

  private void sendCommandToController(){
    String command = textBox.getText().trim();
    if(turtleTracker.isEmpty()) {
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
        historyDisplay.addNewHistoryTag(command);
        createErrorDialog(error); // backend throws new exception with specific error message
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

  public void setTerminalText(String command){
    textBox.setText(command);
  }

  public GridPane getPane() {
    return pane;
  }
}
