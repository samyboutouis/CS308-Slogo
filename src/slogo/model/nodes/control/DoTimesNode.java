package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class DoTimesNode extends BracketNode {

  private List<SlogoNode> parameters;
  private VariableNode variable;
  private double doTimesEnd;
  private int firstEnd;

  public DoTimesNode(int numParameters) {
    // parameters being full determined by bracket, just like conditional node, so this is a dummy value
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    firstEnd = super.getFirstEnd();
    getIndexing(tracker);
    double ret = 0;
    for (double i = 1; i <= doTimesEnd; i++) {
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

  // get limit
  private void getIndexing(BackEndTurtleTracker tracker) {
    // dotimes [ :a fd 50 ] [ fd 50 ]
    // i = 0 is left bracket
    variable = (VariableNode) parameters.get(1);
    doTimesEnd = parameters.get(2).getReturnValue(tracker);
  }
}
