package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for an id command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class IDNode extends SlogoNode {

  public IDNode(int numParameters) {
    super(numParameters);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return tracker.getCurr();
  }
}
