package slogo.model.nodes.control;

import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class MakeUserInstructionNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private int brackets; // how many pairs of brackets to expect

  // this node creates the user defined node and adds it to the map of commands
  // that node needs a list of string to represent the variable names

  public MakeUserInstructionNode(int numParameters) {
    super(numParameters); // dummy value since isFull is overridden
    brackets = numParameters;
    parameters = super.getParameters();
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && checkBrackets();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    int firstEnd = getFirstEnd(); // index of first end bracket
    int start = 0;
    int end = 0;
    double ret = 0;
    if(parameters.get(0).getReturnValue(commands) != 0.0) { // does the expression part
      start = 1; // i = 1 should be list start (hop over expr)
      end = firstEnd;
    }
    else{
      start = firstEnd;
      end = parameters.size();
    }
    for(int i = start; i < end; i++){
      if(!(parameters.get(i) instanceof ListStartNode) && !(parameters.get(i) instanceof ListEndNode)){
        ret = parameters.get(i).getReturnValue(commands); // ret is value of last command executed
      }
    }
    return 1;
  }

  private int getFirstEnd() {
    int firstEnd = 0;
    for(int i = 0; i < parameters.size(); i++){
      if(parameters.get(i) instanceof ListEndNode){
        firstEnd = i;
        break;
      }
    }
    return firstEnd;
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
