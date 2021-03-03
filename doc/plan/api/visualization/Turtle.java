package slogo.visualization;

import api.Movement;
import api.MovementList;

public interface Turtle {
  /*
   * This method, given a list of movements created by the back-end from a user's input will cause the turtle to continually update to visualize all the movements. Will be called once the back-end parses a user's command.
   */
  public void display(MovementList movements);

  /*
   * This method will update the turtle's position based on a movement passed in from the movement list. Based on the specific movement passed in, including a turtle's new position, orientation, and
   pen status, the turtle on the screen will proceed to
   move, rotate, and/or draw on the screen.
   */
  public void updateTurtle(Movement movement);

  /*
   * This method will create a movement object based on the turtle's current state. Specifically, it will create the new movement object by getting all the important variables of the turtle including position,rotatation, and pen status. This movement object will be passed to the back-end to calculate the new movement position.
   */
  public Movement createMovement(double turtleX, double turtleY, double turtleRotation, boolean isPenDown);
}
