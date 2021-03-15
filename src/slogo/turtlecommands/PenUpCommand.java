package slogo.turtlecommands;

import slogo.Command;
import slogo.Turtle;

public class PenUpCommand implements Command {

  @Override
  public void doCommand(Turtle turtle) {
    turtle.penUp();
  }
}
