package slogo.visualization.displays;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import slogo.visualization.observers.BackgroundObserver;
import slogo.visualization.turtle.FrontEndTurtle;
import slogo.visualization.turtle.FrontEndTurtleTracker;

public class TurtleDisplay implements BackgroundObserver {

  private static final int BACKGROUND_RADIUS = 20;
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private static final String TURTLE_PANE_ID = "TurtlePaneID";
  private static final Color DEFAULT_COLOR = Color.web("#dedcdc");

  private final ResourceBundle idBundle;
  private final AnchorPane anchorPane;

  public TurtleDisplay(FrontEndTurtleTracker frontEndTurtleTracker) {
    this.anchorPane = new AnchorPane();
    this.idBundle = ResourceBundle.getBundle(ID_PROPERTY);
    frontEndTurtleTracker.addObserver(this);
    setScreen();
  }

  private void setScreen() {
    anchorPane.setId(idBundle.getString(TURTLE_PANE_ID));
    setBackgroundColor(DEFAULT_COLOR);
  }

  public void setBackgroundColor(Color color) {
    anchorPane
        .setBackground(new Background(
            new BackgroundFill(color, new CornerRadii(BACKGROUND_RADIUS), Insets.EMPTY)));
  }

  public void addToBackground(FrontEndTurtle turtle) {
    turtle.addToScreen(anchorPane, anchorPane.getHeight(), anchorPane.getWidth());
  }

  public AnchorPane getPane() {
    return anchorPane;
  }

  public Color getBackgroundColor() {
    return (Color) anchorPane.getBackground().getFills().get(0).getFill();
  }
}
