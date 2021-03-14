package slogo.model.nodes.control;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class ForNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private VariableNode variable;
  private int brackets;
  private double forStart;
  private double forEnd;
  private double forIncrement;
  private int firstEnd;

  public ForNode(int numParameters) {
    super(numParameters); // parameters being full determined by bracket, just like conditional node, so this is a dummy value
    brackets = numParameters;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && checkBrackets();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    setFirstEnd();
    getIndexing();
    double ret = 0;
    for(double i = forStart; i <= forEnd; i = i + forIncrement){
      variable.setValue(i);
      for(int j = firstEnd; j < parameters.size(); j++){ // runs through all the commands in the loop
        if(!(parameters.get(j) instanceof ListStartNode) && !(parameters.get(j) instanceof ListEndNode)){
          ret = parameters.get(j).getReturnValue(commands); // ret is value of last command executed
        }
      }
    }
    return ret;
  }

  // get start, end, and increment
  private void getIndexing(){
    // for [ :var 1 5 1 ]
    // i = 0 is left bracket
    variable = (VariableNode) parameters.get(1);
    // if values can be other commands, pass in commands instead of null
    forStart = parameters.get(2).getReturnValue(null);
    forEnd = parameters.get(3).getReturnValue(null);
    forIncrement = parameters.get(4).getReturnValue(null);
  }

  private void setFirstEnd() {
    for(int i = 0; i < parameters.size(); i++){
      if(parameters.get(i) instanceof ListEndNode){
        firstEnd = i;
        break;
      }
    }
  }

  // check to see if we've seen brackets number of list end nodes
  private boolean checkBrackets() {
    int seen = 0;
    for(SlogoNode node : parameters){
      if(node instanceof ListEndNode){
        seen++;
      }
    }
    return seen == brackets;
  }
}
