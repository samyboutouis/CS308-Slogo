package slogo.visualization;

import java.io.File;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class TurtleDisplay {
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private static final int BACKGROUND_RADIUS = 20;
  private static final String TURTLE_PANE_ID = "TurtlePaneID";
  private static final String DISPLAY_CLASS_NAME = "displayWindow";

  private final ResourceBundle idBundle;
  private final AnchorPane anchorPane;

  public TurtleDisplay() {
    this.anchorPane = new AnchorPane();
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);
    setScreen();
  }

  private void setScreen() {
    anchorPane.getStyleClass().add(DISPLAY_CLASS_NAME);
    anchorPane.setId(idBundle.getString(TURTLE_PANE_ID));
  }

  public void addTurtle(FrontEndTurtle turtle) {
    turtle.addToScreen(anchorPane, anchorPane.getHeight(), anchorPane.getWidth());
  }

  public void setBackgroundColor(Color color) {
    anchorPane
      .setBackground(new Background(
        new BackgroundFill(color, new CornerRadii(BACKGROUND_RADIUS), Insets.EMPTY)));
  }

  public AnchorPane getPane() {
    return anchorPane;
  }
}
