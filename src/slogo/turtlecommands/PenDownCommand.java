package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.FrontEndTurtle;

public class PenDownCommand implements Command {

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.penDown();
  }
}
