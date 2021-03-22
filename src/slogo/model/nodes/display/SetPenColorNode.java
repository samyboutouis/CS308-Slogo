package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;

public class SetPenColorNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public SetPenColorNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();

  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return 0;
  }
}
