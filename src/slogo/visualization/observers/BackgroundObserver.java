package slogo.visualization.observers;

import javafx.scene.paint.Color;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * An observer interface that observes changes that occur due to changes in the background. In the
 * classes that implement this interface, they will update their states when certain actions or
 * events occur from changing the state of the background in the workspace
 *
 * @author Samy Boutouis
 */
public interface BackgroundObserver {

  /**
   * Notifies the background observers that the background color has changed to a certain color.
   *
   * @param color Color object that represents the background color of the turtle pane.
   */
  void setBackgroundColor(Color color);

  /**
   * Notifies all background observers that a new turtle is being added to the screen and they must
   * update the background to accommodate for the new turtles.
   *
   * @param turtle Turtle object that would be added to the screen.
   */
  void addToBackground(FrontEndTurtle turtle);

  /**
   * Get the color associated with the background of the turtle pane.
   *
   * @return Color object that represents the background color of the turtle pane.
   */
  Color getBackgroundColor();
}
