package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

public class LeftNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;

  public LeftNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.rotate(-1 * values.get(0));
      currTurtle.addCommand(new MovementCommand(0, -1 * values.get(0)));
      return values.get(0);
    });
  }
}