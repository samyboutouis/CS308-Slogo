package slogo.turtlecommands;

import slogo.Command;
import slogo.Turtle;

public class HomeCommand implements Command {

  @Override
  public void doCommand(Turtle turtle) {
    turtle.home();
  }
}
