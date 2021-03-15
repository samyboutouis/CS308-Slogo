package slogo.model.nodes.control;

import java.util.List;
import java.util.Map;
import slogo.Command;
import slogo.model.SlogoNode;

public class RepeatNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private Map<String, Double> variables;
  private VariableNode variable;
  private double repeatEnd;

  public RepeatNode(int numParameters, Map<String, Double> variables) {
    super(numParameters); // parameters being full determined like IfNode (last one is ListEnd)
    this.variables = variables;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1) instanceof ListEndNode;
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    // assume expr only results in one value, so parameters.get(1) is start bracket
    getIndexing(commands);
    double ret = 0;
    for(double i = 1; i <= repeatEnd; i++){
      variable.setValue(i);
      for(int j = 1; j < parameters.size(); j++){ // starts at j = 1 to avoid initial expression
        if(!(parameters.get(j) instanceof ListStartNode) && !(parameters.get(j) instanceof ListEndNode)){
          ret = parameters.get(j).getReturnValue(commands); // ret is value of last command executed
        }
      }
    }
    return ret;
  }

  // get how many times we repeat
  private void getIndexing(List<Command> commands){
    // repeat fd 50 [ fd 50 ]
    // i = 1 is left bracket
    variable = new VariableNode(0, variables, ":repcount");
    repeatEnd = parameters.get(0).getReturnValue(commands);
  }
}
