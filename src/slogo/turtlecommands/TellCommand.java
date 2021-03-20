package slogo.turtlecommands;

import java.util.Map;
import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;

public class TellCommand implements Command {

  private SafeFrontEndTurtleTracker safe;

  public TellCommand(SafeFrontEndTurtleTracker safe, int id, boolean exists){
    this.safe = safe;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
//    if(isActive){
//      // solves circle issues
//      frontEndTurtle.setActive();
//      safe.removeActive(id);
//    } else{
//      frontEndTurtle.setInactive();
//      // have internal call to FrontEndTurtleTracker
//      // or we can call using safeTracker
//      safe.setActive(id);
//    }
  }
}
