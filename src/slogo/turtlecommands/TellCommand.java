package slogo.turtlecommands;

import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Tell, Ask, and AskWith command done on the terminal.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class TellCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private boolean setActive;
  private int id;

  /**
   * Constructor for command. Each turtle will have one of these in their lists every time a Tell,
   * Ask, or AskWith is used.
   *
   * Sets the turtle with this ID either to active or inactive, in order to maintain the active
   * rings around each turtle accurately while the user steps through the code they ran.
   *
   * @param safe tracker that can set turtles' active state.
   * @param id id of turtle to be set to active or inactive.
   * @param setActive true if turtle with ID id should be active, false otherwise.
   */
  public TellCommand(SafeFrontEndTurtleTracker safe, int id, boolean setActive) {
    this.safe = safe;
    this.setActive = setActive;
    this.id = id;
  }

  /**
   * Set turtle to be active or inactive.
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // assume frontend already created the turtle, we just need to call the tracker methods, not the individual turtle method
    if (setActive) {
      safe.setActive(id); // these commands also set the turtle to be active or not
    } else {
      safe.setInactive(id);
    }
  }
}
