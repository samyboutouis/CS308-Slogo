package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Hide Turtle command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class HideTurtleCommand implements Command {

  /**
   * Hide the turtle when called.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.hide();
  }
}
