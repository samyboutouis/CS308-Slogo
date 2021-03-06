package slogo.turtlecommands;

import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.turtle.FrontEndTurtle;

public class TellCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private boolean setActive;
  private int id;

  public TellCommand(SafeFrontEndTurtleTracker safe, int id, boolean setActive) {
    this.safe = safe;
    this.setActive = setActive;
    this.id = id;
  }

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
