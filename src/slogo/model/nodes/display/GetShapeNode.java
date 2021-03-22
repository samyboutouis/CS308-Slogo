package slogo.model.nodes.display;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.DisplayCommand;

public class GetShapeNode extends TurtleCommandNode {

  public GetShapeNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle
          .addCommand(new DisplayCommand("Turtle Shape Index is " + currTurtle.getShapeIndex()));
      return currTurtle.getShapeIndex();
    });
  }
}
