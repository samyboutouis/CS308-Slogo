package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents all of the movement commands done on a turtle (fd, bk, rt, lt).
 *
 * All are relative values, so this could be fit in one command, since a 0 meant nothing would
 * change in that movement.
 *
 * Each movement command should only have one value in constructor be non-zero.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class MovementCommand implements Command {

  private double forward;
  private double directionChange;

  /**
   * Construct this command.
   *
   * forward and directionChange are relative values.
   * handles Forward, Backward, Right, Left.
   *
   * Both values can be negative to represent other direction.
   *
   * @param forward how much to move turtle forward.
   * @param directionChange how much to change turtle direction.
   */

  public MovementCommand(double forward, double directionChange) {
    this.forward = forward;
    this.directionChange = directionChange;
  }

  /**
   * Move turtle forward/backward or right/left
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.forward(forward);
    frontEndTurtle.rotate(directionChange);
  }
}




