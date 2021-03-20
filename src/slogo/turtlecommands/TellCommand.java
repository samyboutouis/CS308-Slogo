package slogo.turtlecommands;

import java.util.Map;
import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;

public class TellCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private boolean setActive;
  private int id;

  public TellCommand(SafeFrontEndTurtleTracker safe, int id, boolean setActive){
    this.safe = safe;
    this.setActive = setActive;
    this.id = id;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // assume frontend already created the turtle, we just need to call the tracker methods, not the individual turtle method
    if(setActive) {
      // safe.setActive(id);
    }
    else{
      // safe.setInactive(id);
    }
  }
}
