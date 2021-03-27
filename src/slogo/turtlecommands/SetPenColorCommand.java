package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Home command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetPenColorCommand implements Command {

  private int index;

  public SetPenColorCommand(int index) {
    this.index = index;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setPenColor(index);
  }
}
