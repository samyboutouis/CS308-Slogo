package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.PenUpCommand;

/**
 * Represents the node of the SlogoNode tree for a pen up command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class PenUpNode extends TurtleCommandNode {

  public PenUpNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new PenUpCommand());
      currTurtle.penUp();
      return 0;
    });
  }
}
