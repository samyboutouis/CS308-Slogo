package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class DoTimesNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private VariableNode variable;
  private int brackets;
  private double doTimesEnd;
  private int firstEnd;

  public DoTimesNode(int numParameters) {
    super(
        numParameters); // parameters being full determined by bracket, just like conditional node, so this is a dummy value
    brackets = numParameters;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && checkBrackets();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    setFirstEnd();
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

  private void setFirstEnd() {
    for (int i = 0; i < parameters.size(); i++) {
      if (parameters.get(i) instanceof ListEndNode) {
        firstEnd = i;
        break;
      }
    }
  }

  // check to see if we've seen brackets number of list end nodes
  private boolean checkBrackets() {
    int seen = 0;
    for (SlogoNode node : parameters) {
      if (node instanceof ListEndNode) {
        seen++;
      }
    }
    return seen == brackets;
  }
}
