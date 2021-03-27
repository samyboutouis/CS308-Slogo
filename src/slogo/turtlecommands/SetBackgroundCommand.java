package slogo.turtlecommands;

import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the SetBackground command done on a turtle.
 *
 * Needs a safe tracker in order to change the entire front end background, since a single
 * turtle does not control the background.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetBackgroundCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private int index;

  /**
   * Constructor of command. Needs to pass in the tracker and the index of the color to set
   * the background to.
   *
   * @param safe tracker that has access to changing background color.
   * @param index index of the color to set the background to.
   */
  public SetBackgroundCommand(SafeFrontEndTurtleTracker safe, int index) {
    this.safe = safe;
    this.index = index;
  }

  /**
   * Set background color once called.
   *
   * @param frontEndTurtle unused for this command.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    safe.setBackgroundColor(index);
  }
}
