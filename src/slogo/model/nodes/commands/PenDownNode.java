package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.PenDownCommand;

public class PenDownNode extends TurtleCommandNode {

  public PenDownNode(int numParameters) {
    super(numParameters);
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenDownCommand());
    turtle.penDown();
    return 1;
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,new ArrayList<>(), (currTurtle, values)->{
      currTurtle.addCommand(new PenDownCommand());
      currTurtle.penDown();
      return 1;
    });
  }
}
