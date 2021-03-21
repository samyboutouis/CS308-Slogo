package slogo.model.nodes.booleans;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class NotEqualNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public NotEqualNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
    setBooleanNode(true);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if (Double.compare(parameters.get(0).getReturnValue(tracker),
        parameters.get(1).getReturnValue(tracker)) == 0) {
      return 0;
    }
    return 1;
  }
}
