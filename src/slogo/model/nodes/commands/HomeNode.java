package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.HomeCommand;

public class HomeNode extends TurtleCommandNode {

  public HomeNode(int numParameters) {
    super(numParameters);
  }
/*
  @Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new HomeCommand());
    double prevX = turtle.getX();
    double prevY = turtle.getY();
    turtle.home();
    return Math.sqrt(Math.pow(prevX - turtle.getX(), 2) +  Math.pow(prevY - turtle.getY(), 2));
    // fd fd 50 fd 50 home
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new HomeCommand());
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.home();
      return Math
          .sqrt(Math.pow(prevX - currTurtle.getX(), 2) + Math.pow(prevY - currTurtle.getY(), 2));
    });
  }
}
