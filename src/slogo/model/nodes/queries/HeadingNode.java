package slogo.model.nodes.queries;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class HeadingNode extends TurtleCommandNode {
  public HeadingNode(int parameter){
    super(parameter);
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    commands.add(new DisplayCommand("Heading: "+turtle.getDirection()));
    return turtle.getDirection();
  }*/

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values)->{
      currTurtle.addCommand(new DisplayCommand("Turtle "+currTurtle.getIndex()+" Heading: "+currTurtle.getDirection()));
      return currTurtle.getDirection();
    });
  }
}