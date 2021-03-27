package slogo.model.nodes.commands;

import java.util.ArrayList;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.ShowTurtleCommand;

/**
 * Represents the node of the SlogoNode tree for a show command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ShowTurtleNode extends TurtleCommandNode {


  public ShowTurtleNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, new ArrayList<>(), (currTurtle, values) -> {
      currTurtle.addCommand(new ShowTurtleCommand());
      currTurtle.show();
      return 1;
    });
  }
}
