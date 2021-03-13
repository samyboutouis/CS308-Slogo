package slogo.model;

import java.util.List;
import slogo.Command;
import slogo.model.nodes.ListEndNode;
import slogo.model.nodes.ListStartNode;

public class IfNode extends SlogoNode{
  private List<SlogoNode> parameters;
  private int brackets; // how many pairs of brackets to expect

  public IfNode(int numParameters) {
    super(numParameters); // dummy value since isFull is overridden
    brackets = numParameters;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1) instanceof ListEndNode;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    // assume expr is just one tree
      double ret = 0;
      if(parameters.get(0).getReturnValue(commands) != 0.0) { // does the expression part
        for(int i = 1; i < parameters.size(); i++){ // i = 1 should be list start
          if(!(parameters.get(i) instanceof ListStartNode) && !(parameters.get(i) instanceof ListEndNode)){
            ret = parameters.get(i).getReturnValue(commands); // ret is value of last command executed
          }
        }
      }
      return ret;
  }
}