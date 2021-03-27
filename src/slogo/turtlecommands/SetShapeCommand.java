package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Set Shape command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetShapeCommand implements Command {

  private int index;

  /**
   * Constructor for command.
   *
   * @param index index of palette to set new shape for turtle.
   */
  public SetShapeCommand(int index) {
    this.index = index;
  }

  /**
   * Set shape of turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setShape(index);
  }

}
