package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.ClearScreenCommand;

public class ClearScreenNode extends TurtleCommandNode {
  private Turtle turtle;

  public ClearScreenNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    this.turtle = turtle;
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
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,new ArrayList<>(),(currTurtle, values)->{
      currTurtle.addCommand(new ClearScreenCommand());
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.clearScreen();
      return Math.sqrt(Math.pow(prevX - currTurtle.getX(), 2) +  Math.pow(prevY - currTurtle.getY(), 2));
    });
  }
}
