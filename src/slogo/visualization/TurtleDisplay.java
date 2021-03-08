package slogo.visualization;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import slogo.Turtle;

public class TurtleDisplay {

  private final GridPane gridPane;
  private Turtle turtle;

  public TurtleDisplay(GridPane gridPane){
    this.gridPane = gridPane;
    initializeGridPane();
    this.turtle = new Turtle();
    setScreen();
  }

  private void initializeGridPane() {
    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    gridPane.getColumnConstraints().add(col);
    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    gridPane.getRowConstraints().add(row);
  }

  private void setScreen() {
    gridPane.add(turtle, 0, 0);
//    Button button = new Button("FORWARD");
//    button.setOnAction(event -> turtle.forward( 10));
//    gridPane.add(button, 0, 0);
  }
}
