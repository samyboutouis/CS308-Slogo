package slogo.visualization;

import java.io.File;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

  private static final int BACKGROUND_RADIUS = 20;

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
    col.setHalignment(HPos.CENTER);
    gridPane.getColumnConstraints().add(col);
    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    gridPane.getRowConstraints().add(row);
  }

  private void setScreen() {
    gridPane.add(turtle, 0, 0);
    testButtons();
  }

  private void testButtons() {
    Button button = new Button("Right");
    button.setOnAction(event -> turtle.right( 10));
    Button left = new Button("Left");
    left.setOnAction(event -> turtle.left( 10));
    Button forward = new Button("Forward");
    forward.setOnAction(event -> turtle.forward( 10));
    Button back = new Button("Back");
    back.setOnAction(event -> turtle.back( 10));
    gridPane.add(button, 0, 1);
    gridPane.add(left, 0, 2);
    gridPane.add(forward, 0, 3);
    gridPane.add(back, 0, 4);
  }

  public void setTurtleImage(File file) {
    turtle.setImage(file);
  }

  public void setBackgroundColor(Color color) {
    gridPane
      .setBackground(new Background(
        new BackgroundFill(color, new CornerRadii(BACKGROUND_RADIUS), Insets.EMPTY)));
  }
}
