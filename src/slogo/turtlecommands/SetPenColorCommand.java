package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Set Pen Color command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetPenColorCommand implements Command {

  private int index;

  /**
   * Constructor for command.
   *
   * @param index index of palette for new pen color.
   */
  public SetPenColorCommand(int index) {
    this.index = index;
  }

  /**
   * Set pen color on turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setPenColor(index);
  }
}
