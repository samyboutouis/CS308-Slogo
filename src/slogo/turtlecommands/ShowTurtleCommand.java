package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Show Turtle command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ShowTurtleCommand implements Command {

  /**
   * Shows turtle once called by front end.
   *
   * @param frontEndTurtle the turtle that will be shown once called.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.show();
  }
}
