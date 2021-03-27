package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Home command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class HomeCommand implements Command {

  /**
   * Do Home command on this turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.home();
  }
}
