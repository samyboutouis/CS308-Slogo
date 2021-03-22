package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class ForNode extends BracketNode {

  private List<SlogoNode> parameters;
  private VariableNode variable;
  private double forStart;
  private double forEnd;
  private double forIncrement;

  public ForNode(int numParameters) {
    // parameters being full determined by bracket, just like conditional node, so this is a dummy value
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    int firstEnd = super.getFirstEnd();
    getIndexing(tracker);
    double ret = 0;
    for (double i = forStart; i <= forEnd; i = i + forIncrement) {
      variable.setValue(i);
      for (int j = firstEnd; j < parameters.size();
          j++) { // runs through all the commands in the loop
        if (!(parameters.get(j) instanceof ListStartNode) && !(parameters
            .get(j) instanceof ListEndNode)) {
          ret = parameters.get(j).getReturnValue(tracker); // ret is value of last command executed
        }
      }
    }
    return ret;
  }

  // get start, end, and increment
  private void getIndexing(BackEndTurtleTracker tracker) {
    // for [ :var 1 5 1 ]
    // i = 0 is left bracket
    variable = (VariableNode) parameters.get(1);
    forStart = parameters.get(2).getReturnValue(tracker);
    forEnd = parameters.get(3).getReturnValue(tracker);
    forIncrement = parameters.get(4).getReturnValue(tracker);
  }
}
