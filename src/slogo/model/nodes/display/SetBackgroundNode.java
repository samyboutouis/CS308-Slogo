package slogo.model.nodes.display;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.model.nodes.commands.TurtleCommandNode;

public class SetBackgroundNode extends TurtleCommandNode {

  public SetBackgroundNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      // create set background command here
      return values.get(0);
    });
  }
}
