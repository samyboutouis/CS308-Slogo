package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Set Heading command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetHeadingCommand implements Command {

  // deals with turtle call that sets direction to somewhere absolute using angle
  private double degrees;

  /**
   * Constructor of command.
   *
   * @param degrees where to point turtle.
   */
  public SetHeadingCommand(double degrees) {
    this.degrees = degrees;
  }

  /**
   * Set Heading command on turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setDirection(degrees);
  }
}
