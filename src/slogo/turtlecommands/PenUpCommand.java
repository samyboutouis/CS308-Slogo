package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

public class PenUpCommand implements Command {

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.penUp();
  }
}
