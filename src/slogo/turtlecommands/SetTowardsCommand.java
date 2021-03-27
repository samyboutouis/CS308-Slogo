package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the towards command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetTowardsCommand implements Command {

  // deals with turtle call that sets direction to somewhere absolute using coordinates
  private double x;
  private double y;

  /**
   * Constructor for command.
   *
   * @param x x coordinate for turtle to face
   * @param y y coordinate for turtle to face
   */
  public SetTowardsCommand(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Towards command on turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.towards(x, y);
  }
}
