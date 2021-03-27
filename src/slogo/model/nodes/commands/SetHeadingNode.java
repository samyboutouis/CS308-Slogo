package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetHeadingCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a set heading command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetHeadingNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a set heading node takes (1)
   */
  public SetHeadingNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetHeadingCommand(values.get(0)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.setDirection(values.get(0));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }
}
