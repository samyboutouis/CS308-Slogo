package slogo.visualization;

import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import slogo.Turtle;

public class TurtleDisplay {

  private static final int BACKGROUND_RADIUS = 20;

  private final AnchorPane anchorPane;
  private final Turtle turtle;

  public TurtleDisplay(AnchorPane anchorPane) {
    this.anchorPane = anchorPane;
    this.turtle = new Turtle(anchorPane);
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
