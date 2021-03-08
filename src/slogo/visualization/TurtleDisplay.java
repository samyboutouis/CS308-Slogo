package slogo.visualization;

import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import slogo.Turtle;

public class TurtleDisplay {

  private final GridPane gridPane;
  private final Turtle turtle;

  public TurtleDisplay(GridPane gridPane) {
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

  public void setTurtleImage(File file) {
    turtle.setImage(file);
  }

  public void setBackgroundColor(Color color) {
    gridPane
      .setBackground(new Background(new BackgroundFill(color, new CornerRadii(20), Insets.EMPTY)));
  }
}
