package slogo.visualization;

import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import slogo.Turtle;

public class TurtleDisplay {

  private static final int BACKGROUND_RADIUS = 20;

  private final AnchorPane anchorPane;
  private final Turtle turtle;

  public TurtleDisplay(AnchorPane anchorPane) {
    this.anchorPane = anchorPane;
    this.turtle = new Turtle(anchorPane);
    setScreen();
  }

  private void setScreen() {
    testButtons();
  }

  private void testButtons() {
    Button button = new Button("Right");
    button.setOnAction(event -> turtle.right( 10));
    Button left = new Button("Left");
    left.setOnAction(event -> turtle.left( 10));
    Button forward = new Button("Forward");
    forward.setOnAction(event -> {
      turtle.forward( 10);
      System.out.println(anchorPane.getWidth() + " " + anchorPane.getHeight());
    });
    Button back = new Button("Back");
    back.setOnAction(event -> turtle.back( 10));
    Button penUp = new Button("Pen Up");
    penUp.setOnAction(event -> turtle.penUp());
    Button penDown = new Button("Pen Down");
    penDown.setOnAction(event -> turtle.penDown());
    VBox vBox = new VBox();
    vBox.getChildren().add(button);
    vBox.getChildren().add(left);
    vBox.getChildren().add(forward);
    vBox.getChildren().add(back);
    vBox.getChildren().add(penUp);
    vBox.getChildren().add(penDown);
    anchorPane.getChildren().add(vBox);
  }

  public void setTurtleImage(File file) {
    turtle.setImage(file);
  }

  public void setBackgroundColor(Color color) {
    anchorPane
      .setBackground(new Background(
        new BackgroundFill(color, new CornerRadii(BACKGROUND_RADIUS), Insets.EMPTY)));
  }

  public void setPenColor(Color color) {
    turtle.setPenColor(color);
  }
}
