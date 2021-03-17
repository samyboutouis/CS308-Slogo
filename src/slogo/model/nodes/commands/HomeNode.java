package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.FrontEndTurtle;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.HomeCommand;

public class HomeNode extends SlogoNode {
  private Turtle turtle;

  public HomeNode(int numParameters, Turtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new HomeCommand());
    double prevX = turtle.getX();
    double prevY = turtle.getY();
    turtle.home();
    return Math.sqrt(Math.pow(prevX - turtle.getX(), 2) +  Math.pow(prevY - turtle.getY(), 2));
    // fd fd 50 fd 50 home
  }
}
