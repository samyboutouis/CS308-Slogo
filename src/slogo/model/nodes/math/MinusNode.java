package slogo.model.nodes.math;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;
import slogo.model.TurtleTracker;

public class MinusNode extends SlogoNode {

  private List<SlogoNode> parameters;

  public MinusNode(int numParameters){
    super(numParameters);
    parameters = super.getParameters();
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return -1*parameters.get(0).getReturnValue(tracker);
  }
}