package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.PenDownCommand;

/**
 * Represents the node of the SlogoNode tree for a pen down command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class PenDownNode extends TurtleCommandNode {

  public PenDownNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new PenDownCommand());
      currTurtle.penDown();
      return 1;
    });
  }
}
