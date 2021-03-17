package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.BackEndTurtle;
import slogo.Turtle;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.PenDownCommand;

public class PenDownNode extends TurtleCommandNode {
  private Turtle turtle;

  public PenDownNode(int numParameters, BackEndTurtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenDownCommand());
    turtle.penDown();
    return 1;
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,new ArrayList<>(), (currTurtle, values)->{
      currTurtle.addCommand(new PenDownCommand());
      currTurtle.penDown();
      return 1;
    });
  }
}
