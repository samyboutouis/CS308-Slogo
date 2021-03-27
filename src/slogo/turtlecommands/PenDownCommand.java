package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Pen Down command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class PenDownCommand implements Command {

  /**
   * Put turtle pen down.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.penDown();
  }
}
