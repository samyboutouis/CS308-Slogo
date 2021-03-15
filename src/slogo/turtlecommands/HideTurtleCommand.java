package slogo.turtlecommands;

import slogo.Command;
import slogo.FrontEndTurtle;

public class HideTurtleCommand implements Command {

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.hide();
  }
}
