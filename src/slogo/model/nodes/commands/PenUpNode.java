package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.PenUpCommand;

public class PenUpNode extends TurtleCommandNode {

  public PenUpNode(int numParameters) {
    super(numParameters);
  }
  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenUpCommand());
    turtle.penUp();
    return 0;
  }*/

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,new ArrayList<>(), (currTurtle, values)->{
      currTurtle.addCommand(new PenUpCommand());
      currTurtle.penUp();
      return 0;
    });
  }
}
