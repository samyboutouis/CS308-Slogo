package slogo.turtlecommands;

import slogo.Command;
import slogo.FrontEndTurtle;

// used to display information from backend to frontend
public class DisplayCommand implements Command {

  public DisplayCommand(String info){

  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // frontEndTurtle.print(info);
  }
}
