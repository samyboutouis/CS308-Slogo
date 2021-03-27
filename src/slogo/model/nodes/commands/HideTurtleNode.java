package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.HideTurtleCommand;

/**
 * Represents the node of the SlogoNode tree for a hide command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class HideTurtleNode extends TurtleCommandNode {

  public HideTurtleNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new HideTurtleCommand());
      currTurtle.hide();
      return 0;
    });
  }
}