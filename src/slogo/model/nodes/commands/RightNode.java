package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.MovementCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a right command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class RightNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  public RightNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.rotate(values.get(0));
      currTurtle.addCommand(new MovementCommand(0, values.get(0)));
      return values.get(0);
    });
  }

  ;
}