package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Set Pen Size command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetPenSizeCommand implements Command {

  private double thickness;

  /**
   * Constructor for command.
   *
   * @param thickness new pen size to set turtle pen to.
   */
  public SetPenSizeCommand(double thickness) {
    this.thickness = thickness;
  }

  /**
   * Set pen size on turtle.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setPenThickness(thickness);
  }
}
