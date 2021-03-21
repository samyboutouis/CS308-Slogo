package slogo.turtlecommands;

import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;

public class SetBackgroundCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private int index;

  public SetBackgroundCommand(SafeFrontEndTurtleTracker safe, int index) {
    this.safe = safe;
    this.index = index;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // safe.setbg
  }
}
