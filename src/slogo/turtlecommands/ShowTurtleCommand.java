package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.FrontEndTurtle;

public class ShowTurtleCommand implements Command {

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.show();
  }
}
