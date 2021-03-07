package slogo.visualization;

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

  private final GridPane pane;

  public TerminalDisplay(GridPane pane){
    this.pane = pane;

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
    Button button = new Button("BUTTON");
    button.setMinHeight(100);
    pane.add(button, 6, 0, 2, 1);
  }
}
