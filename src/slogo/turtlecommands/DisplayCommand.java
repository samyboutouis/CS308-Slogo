package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Used to display information from backend to frontend.
 *
 * Not currently used by front end but backend provided functionality for queries to be displayed
 * by front end.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class DisplayCommand implements Command {

  private String info;

  /**
   * Constructor of command to take in display info.
   *
   * @param info information to be displayed.
   */
  public DisplayCommand(String info) {
    this.info = info;
  }

  /**
   * No current turtle command implemented to display this information.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // frontEndTurtle.print(info);
  }
}
