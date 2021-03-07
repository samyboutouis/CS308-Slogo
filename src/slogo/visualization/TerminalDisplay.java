package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TerminalDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static String TERMINAL_BUTTON = "TerminalButton";
  private final static String TERMINAL_PROMPT = "TerminalPrompt";
  private final static int COLUMN_COUNT = 4;

  private final ResourceBundle resourceBundle;
  private final GridPane pane;
  private TextArea textBox;
  private Button button;

  public TerminalDisplay(GridPane pane, String resourcePackage){
    this.pane = pane;

    pane.setMaxWidth(Double.MAX_VALUE);
    pane.setMaxHeight(Double.MAX_VALUE);

    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));

    pane.setHgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));

    for(int i = 0; i < COLUMN_COUNT; i++){
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      col.setPercentWidth(100.0 / COLUMN_COUNT);
      pane.getColumnConstraints().add(col);
    }

    initializeTextField();
    initializeButton();

    applyTextBoxLogic();
    applyButtonLogic();
  }

  private void initializeTextField(){
    textBox = new TextArea("Type command...");
    textBox.setPrefColumnCount(10);
    textBox.setWrapText(true);
    textBox.setId("TerminalTextBox");
    textBox.setFocusTraversable(false);

    HBox.setHgrow(textBox, Priority.ALWAYS);

    pane.add(textBox, 0, 0, 3, 1);
  }

  private void initializeButton(){
    button = new Button(resourceBundle.getString(TERMINAL_BUTTON));
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMaxHeight(Double.MAX_VALUE);
    button.setWrapText(true);

    HBox.setHgrow(button, Priority.ALWAYS);

    pane.add(button, 3, 0, 1, 1);
  }

  private void applyTextBoxLogic(){
    textBox.setOnMouseClicked(e -> {
      if(textBox.getText().equals(resourceBundle.getString(TERMINAL_PROMPT))){
        textBox.clear();
      }
    });
  }

  private void applyButtonLogic(){
    button.setOnAction(e -> {
      String command = textBox.getText();
      if(command != null && !command.equals(resourceBundle.getString(TERMINAL_PROMPT)) && command.trim().length() > 0){
        System.out.println(textBox.getText());
      }
      textBox.setText(resourceBundle.getString(TERMINAL_PROMPT));
    });
  }
}
