package slogo.turtlecommands;

import slogo.Command;
import slogo.Turtle;

public class PenDownCommand implements Command {

  @Override
  public void doCommand(Turtle turtle) {
    turtle.penDown();
  }
}
