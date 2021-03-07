package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class TerminalDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static String BUTTON_LABEL = "TerminalButton";

  private final ResourceBundle resourceBundle;
  private final GridPane pane;

  public TerminalDisplay(GridPane pane, String resourcePackage){
    this.pane = pane;
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s", resourcePackage, "English"));

    pane.setVgap(PADDING_LENGTH);
    pane.setHgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));

    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    pane.getRowConstraints().add(row);

    for(int i = 0; i < 8; i++){
      ColumnConstraints col = new ColumnConstraints();
      col.setHgrow(Priority.ALWAYS);
      pane.getColumnConstraints().add(col);
    }

    pane.setStyle("-fx-background-color: #efefef");

    initializeTextField();
    initializeButton();
  }

  private void initializeTextField(){
    TextArea textBox = new TextArea();
    pane.add(textBox, 0, 0, 6, 1);
  }

  private void initializeButton(){
    VBox box = new VBox();
    box.setFillWidth(true);

    Button button = new Button(resourceBundle.getString(BUTTON_LABEL));
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMaxHeight(Double.MAX_VALUE);
    VBox.setVgrow(button, Priority.ALWAYS);
    box.getChildren().add(button);

    pane.add(box, 6, 0, 2, 1);
  }
}
