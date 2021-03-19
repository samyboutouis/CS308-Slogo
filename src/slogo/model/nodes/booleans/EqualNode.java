package slogo.model.nodes.booleans;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class EqualNode extends SlogoNode{
  private List<SlogoNode> parameters;

  public EqualNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if(Double.compare(parameters.get(0).getReturnValue(tracker), parameters.get(1).getReturnValue(tracker)) == 0){
      return 1;
    }
    return 0;
  }
}
