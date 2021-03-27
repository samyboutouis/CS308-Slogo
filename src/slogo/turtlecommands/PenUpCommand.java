package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Pen Up command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class PenUpCommand implements Command {

  /**
   * Put turtle pen up.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.penUp();
  }
}
