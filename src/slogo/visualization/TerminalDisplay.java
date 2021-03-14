package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import slogo.Turtle;
import slogo.model.CommandReader;

public class TerminalDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static String TERMINAL_BUTTON = "TerminalButton";
  private final static String TERMINAL_PROMPT = "TerminalPrompt";
  private final static String TERMINAL_TEXT_BOX_ID = "TerminalTextBoxID";
  private final static String TERMINAL_BUTTON_ID = "TerminalButtonID";
  private final static String ERROR_TITLE_PROPERTY = "ErrorTitle";
  private final static int COLUMN_COUNT = 4;

  private final ResourceBundle resourceBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle errorBundle;
  private final GridPane pane;
  private TextArea textBox;
  private Button button;
  private final HistoryDisplay historyDisplay;
  private final VariablesDisplay variablesDisplay;
  private final Turtle turtle;

  private final CommandReader commandReader;

  public TerminalDisplay(GridPane pane, String resourcePackage, HistoryDisplay historyDisplay,
    Turtle turtle, VariablesDisplay variablesDisplay) {
    this.pane = pane;
    this.historyDisplay = historyDisplay;
    this.variablesDisplay = variablesDisplay;
    this.turtle = turtle;

    commandReader = new CommandReader("English");

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

    initializeTextField();
    initializeButton();

    applyButtonLogic();
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
    button.setId(idBundle.getString(TERMINAL_BUTTON_ID));

    pane.add(button, 3, 0, 1, 1);
  }

  private void applyButtonLogic() {
    button.setOnAction(e -> {
      String command = textBox.getText().trim();
      if (command.length() > 0) {
        try {
          new AnimationManager(commandReader.parseInput(command), turtle);
          Button historyTag = historyDisplay.addNewHistoryTag(command);
          variablesDisplay.updateBox(commandReader.getVariables());
          applyHistoryTagLogic(historyTag);
        } catch (Exception error) {
          createErrorDialog(error); // backend throws new exception with specific error message
        }
      }
      textBox.clear();
    });
  }

  private void applyHistoryTagLogic(Button historyTab) {
    historyTab.setOnAction(e -> {
      String command = historyTab.getText();
      textBox.setText(command);
    });
  }

  private void createErrorDialog(Exception error) {
    Alert newAlert = new Alert(AlertType.ERROR);
    newAlert.setTitle(errorBundle.getString(ERROR_TITLE_PROPERTY));
    newAlert.setHeaderText(null);
    newAlert.setContentText(error.getMessage());
    newAlert.showAndWait();
  }
}
