package slogo.visualization;

import java.io.File;
import java.util.ResourceBundle;
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
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private static final int BACKGROUND_RADIUS = 20;
  private static final String ADD_TURTLE_BUTTON_ID = "AddTurtleButton";
  private static final String TURTLE_PANE_ID = "TurtlePaneID";

  private final ResourceBundle idBundle;
  private final AnchorPane anchorPane;
  private final Turtle turtle;

  public TurtleDisplay(AnchorPane anchorPane, Turtle turtle) {
    this.anchorPane = anchorPane;
    this.turtle = turtle;
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);
    setScreen();
  }

  private void setScreen() {
    addTurtle();
    anchorPane.setId(idBundle.getString(TURTLE_PANE_ID));
  }

  private void addTurtle() {
    Button addTurtle = new Button("Add Turtle");
    addTurtle.setOnAction(event -> {
      turtle.addToScreen(anchorPane, anchorPane.getHeight(), anchorPane.getWidth());
      addTurtle.setDisable(true);
    });
    addTurtle.setId(idBundle.getString(ADD_TURTLE_BUTTON_ID));
    VBox vBox = new VBox();
    vBox.getChildren().add(addTurtle);
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
