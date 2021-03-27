package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetTowardsCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a set towards command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetTowardsNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public SetTowardsNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetTowardsCommand(values.get(0), values.get(1)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.towards(values.get(0), values.get(1));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }
}
