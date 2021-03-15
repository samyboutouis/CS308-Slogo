package slogo.visualization;

import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import slogo.FrontEndTurtle;

public class TurtleDisplay {
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private static final int BACKGROUND_RADIUS = 20;
  private static final String ADD_TURTLE_BUTTON_ID = "AddTurtleButton";
  private static final String TURTLE_PANE_ID = "TurtlePaneID";

  private final ResourceBundle idBundle;
  private final AnchorPane anchorPane;
  private final FrontEndTurtle frontEndTurtle;

  public TurtleDisplay(AnchorPane anchorPane, FrontEndTurtle frontEndTurtle) {
    this.anchorPane = anchorPane;
    this.frontEndTurtle = frontEndTurtle;
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);
    setScreen();
  }

  private void setScreen() {
    anchorPane.setId(idBundle.getString(TURTLE_PANE_ID));
  }

  public void addTurtle() {
    frontEndTurtle.addToScreen(anchorPane, anchorPane.getHeight(), anchorPane.getWidth());
  }

  public void setTurtleImage(File file) {
    frontEndTurtle.setImage(file);
  }

  public void setBackgroundColor(Color color) {
    anchorPane
      .setBackground(new Background(
        new BackgroundFill(color, new CornerRadii(BACKGROUND_RADIUS), Insets.EMPTY)));
  }

  public void setPenColor(Color color) {
    frontEndTurtle.setPenColor(color);
  }
}
