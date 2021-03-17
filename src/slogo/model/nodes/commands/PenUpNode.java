package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.PenUpCommand;

public class PenUpNode extends TurtleCommandNode {
  private Turtle turtle;

  public PenUpNode(int numParameters, BackEndTurtle turtle) {
    super(numParameters);
    this.turtle = turtle;
  }
  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new PenUpCommand());
    turtle.penUp();
    return 0;
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,new ArrayList<>(), (currTurtle, values)->{
      currTurtle.addCommand(new PenUpCommand());
      currTurtle.penUp();
      return 0;
    });
  }
}
