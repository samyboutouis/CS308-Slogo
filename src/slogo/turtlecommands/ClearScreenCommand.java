package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the clear screen command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ClearScreenCommand implements Command {

  /**
   * Clear screen command for this turtle.
   *
   * @param frontEndTurtle the turtle that will have clear screen done on it.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.clearScreen();
  }
}
