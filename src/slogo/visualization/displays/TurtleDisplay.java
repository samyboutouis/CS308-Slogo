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

/**
 * Display class that controls and displays all the turtles on the screen. Is dependent on the
 * FrontEndTurtleTracker class to display all the turtles and get their states displayed on the
 * screen properly. This class should be used to create a visual representation of all the turtles.
 *
 * @author Samy Boutouis
 */
public class TurtleDisplay implements BackgroundObserver {

  private static final int BACKGROUND_RADIUS = 20;
  private static final String ID_PROPERTY = "resources/stylesheets/CSS_IDs";
  private static final String TURTLE_PANE_ID = "TurtlePaneID";
  private static final Color DEFAULT_COLOR = Color.web("#dedcdc");

  private final ResourceBundle idBundle;
  private final AnchorPane anchorPane;

  /**
   * Constructor for the class.
   *
   * @param frontEndTurtleTracker TurtleTracker for the entire workspace tracking all the turtles
   */
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

  /**
   * Sets the current color of the turtle pane.
   *
   * @param color Color object that represents the background color of the turtle pane.
   */
  public void setBackgroundColor(Color color) {
    anchorPane
        .setBackground(new Background(
            new BackgroundFill(color, new CornerRadii(BACKGROUND_RADIUS), Insets.EMPTY)));
  }

  /**
   * Adds a turtle to the screen by adding it to the pane.
   *
   * @param turtle Turtle object that would be added to the screen.
   */
  public void addToBackground(FrontEndTurtle turtle) {
    turtle.addToScreen(anchorPane, anchorPane.getHeight(), anchorPane.getWidth());
  }

  /**
   * Returns the root pane of the display view.
   *
   * @return Root pane of the display view
   */
  public AnchorPane getPane() {
    return anchorPane;
  }

  /**
   * Gets the current color of the turtle pane.
   *
   * @return Color object representing the background color of the turtle display.
   */
  public Color getBackgroundColor() {
    return (Color) anchorPane.getBackground().getFills().get(0).getFill();
  }
}
