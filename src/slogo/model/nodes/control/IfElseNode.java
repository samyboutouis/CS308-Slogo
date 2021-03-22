package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class IfElseNode extends BracketNode {

  private List<SlogoNode> parameters;

  public IfElseNode(int numParameters) {
    super(numParameters); // dummy value since isFull is overridden in bracket node
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    // assume expr is just one tree
    int firstEnd = super.getFirstEnd(); // index of first end bracket
    int start = 0;
    int end = 0;
    double ret = 0;
    if (parameters.get(0).getReturnValue(tracker) != 0.0) { // does the expression part
      start = 1; // i = 1 should be list start (hop over expr)
      end = firstEnd;
    } else {
      start = firstEnd;
      end = parameters.size();
    }
    for (int i = start; i < end; i++) {
      if (!(parameters.get(i) instanceof ListStartNode) && !(parameters
          .get(i) instanceof ListEndNode)) {
        ret = parameters.get(i).getReturnValue(tracker); // ret is value of last command executed
      }
    }
    return ret;
  }
}