package slogo.turtlecommands;

import slogo.Command;
import slogo.Turtle;

public class ShowTurtleCommand implements Command {

  @Override
  public void doCommand(Turtle turtle) {
    turtle.show();
  }
}
