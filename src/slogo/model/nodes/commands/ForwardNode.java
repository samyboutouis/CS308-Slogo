package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class ForwardNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public ForwardNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.forward(values.get(0));
      currTurtle.addCommand(new MovementCommand(values.get(0), 0));
      // move forward the amount in values.get(0)
      return values.get(0); // only one value for a forward node
    });
  }
}
