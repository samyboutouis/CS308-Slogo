package slogo.model.nodes.commands;

import java.util.List;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.turtlecommands.ClearScreenCommand;

public class ClearScreenNode extends SlogoNode {
  private Turtle turtle;

  public ClearScreenNode(int numParameters, Turtle turtle){
    super(numParameters);
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new ClearScreenCommand());
    double prevX = turtle.getX();
    double prevY = turtle.getY();
    turtle.clearScreen();
    return Math.sqrt(Math.pow(prevX - turtle.getX(), 2) +  Math.pow(prevY - turtle.getY(), 2));
  }
}
