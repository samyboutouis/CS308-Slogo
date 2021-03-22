package slogo.model.nodes.queries;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class HeadingNode extends TurtleCommandNode {

  public HeadingNode(int parameter) {
    super(parameter);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new DisplayCommand(
          "Turtle " + currTurtle.getIndex() + " Heading: " + currTurtle.getDirection()));
      return currTurtle.getDirection();
    });
  }
}