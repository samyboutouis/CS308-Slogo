package slogo.model.nodes.display;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;

public class SetBackgroundNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public SetBackgroundNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      // create set background command here
      return values.get(0);
    });
  }
}
