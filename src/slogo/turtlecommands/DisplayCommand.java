package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

// used to display information from backend to frontend
public class DisplayCommand implements Command {

  private String info;

  public DisplayCommand(String info) {
    this.info = info;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // frontEndTurtle.print(info);
  }
}
