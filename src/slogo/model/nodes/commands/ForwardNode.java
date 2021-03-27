package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a forward command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ForwardNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a forward node takes (1)
   */
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
