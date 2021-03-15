package slogo.turtlecommands;

import slogo.Command;
import slogo.FrontEndTurtle;

public class ClearScreenCommand implements Command {

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.clearScreen();
  }
}
