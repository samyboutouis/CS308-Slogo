package slogo.turtlecommands;

import slogo.Command;
import slogo.Turtle;

public class ClearScreenCommand implements Command {

  @Override
  public void doCommand(Turtle turtle) {
    turtle.clearScreen();
  }
}
