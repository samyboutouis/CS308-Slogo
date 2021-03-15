package slogo.turtlecommands;

import slogo.Command;
import slogo.FrontEndTurtle;

public class PenUpCommand implements Command {

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.penUp();
  }
}
