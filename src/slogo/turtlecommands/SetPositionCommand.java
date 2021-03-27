package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the SETXY and GOTO commands done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetPositionCommand implements Command {

  private double x;
  private double y;

  /**
   * Constructor of command. Needs x and y coordinate to set turtle position.
   *
   * @param x x coordinate to set turtle position to.
   * @param y y coordinate to set turtle position to.
   */
  public SetPositionCommand(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Set xy/go to command on turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setXY(x, y);
  }
}
