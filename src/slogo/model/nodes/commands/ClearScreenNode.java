package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.ClearScreenCommand;

public class ClearScreenNode extends TurtleCommandNode {

  public ClearScreenNode(int numParameters) {
    super(numParameters);
  }
/*
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new ClearScreenCommand());
    double prevX = turtle.getX();
    double prevY = turtle.getY();
    turtle.clearScreen();
    return Math.sqrt(Math.pow(prevX - turtle.getX(), 2) +  Math.pow(prevY - turtle.getY(), 2));
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new ClearScreenCommand());
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.clearScreen();
      return Math
          .sqrt(Math.pow(prevX - currTurtle.getX(), 2) + Math.pow(prevY - currTurtle.getY(), 2));
    });
  }
}
