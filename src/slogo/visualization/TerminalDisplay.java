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
  private final static String BUTTON_LABEL = "TerminalButton";

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

    for(int i = 0; i < 3; i++){
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      pane.getColumnConstraints().add(col);
    }

    initializeTextField();
    initializeButton();
  }

  private void initializeTextField(){
    textBox = new TextArea("Type command...");
    textBox.setPrefColumnCount(10);
    textBox.setWrapText(true);
    textBox.setId("TerminalTextBox");

    HBox.setHgrow(textBox, Priority.ALWAYS);

    pane.add(textBox, 0, 0, 2, 1);

    applyTextBoxLogic();
  }

  private void initializeButton(){
    button = new Button(resourceBundle.getString(BUTTON_LABEL));
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMaxHeight(Double.MAX_VALUE);

    HBox.setHgrow(button, Priority.ALWAYS);

    pane.add(button, 2, 0, 1, 1);

    applyButtonLogic();
  }

  private void applyTextBoxLogic(){
    textBox.setOnMouseClicked(e -> {
      if(textBox.getText().equals("Type command...")){
        textBox.clear();
      }
    });
  }

  private void applyButtonLogic(){
    button.setOnAction(e -> {
      String command = textBox.getText();
      if(command != null && !command.equals("Type command...") && command.trim().length() > 0){
        System.out.println(textBox.getText());
      }
      textBox.setText("Type command...");
    });
  }
}
