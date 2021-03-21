package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class IfNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public IfNode(int numParameters) {
    super(numParameters); // dummy value since isFull is overridden
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1) instanceof ListEndNode;
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    // assume expr is just one tree
    double ret = 0;
    if (parameters.get(0).getReturnValue(tracker) != 0.0) { // does the expression part
      for (int i = 1; i < parameters.size(); i++) { // i = 1 should be list start
        if (!(parameters.get(i) instanceof ListStartNode) && !(parameters
            .get(i) instanceof ListEndNode)) {
          ret = parameters.get(i).getReturnValue(tracker); // ret is value of last command executed
        }
      }
    }
    return ret;
  }
}