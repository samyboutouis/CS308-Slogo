package slogo.model.nodes.booleans;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class OrNode extends SlogoNode{
  private List<SlogoNode> parameters;

  public OrNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
    setBooleanNode(true);
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if(parameters.get(0).getReturnValue(tracker) != 0.0 || parameters.get(1).getReturnValue(tracker) != 0.0){
      return 1;
    }
    return 0;
  }
}
