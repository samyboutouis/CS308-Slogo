package slogo.model.nodes.control;

import java.util.List;
import java.util.Map;
import slogo.Command;
import slogo.model.SlogoNode;

public class MakeUserInstructionNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private int brackets;
  private int firstEnd;
  private UserDefinedNode definedNode;

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
    try{
      getFirstEnd(); // index of first end bracket
      getVariables();
      for(int i = firstEnd; i < parameters.size(); i++){ // technically could do i = firstEnd + 2 but our code handles i = firstEnd
        if(!(parameters.get(i) instanceof ListStartNode) && !(parameters.get(i) instanceof ListEndNode)){
          definedNode.addCommand(parameters.get(i));
        }
      }
      return 1;
    } catch (ClassCastException c) {
      // if name wasn't a user defined node (already exists)
      return 0;
    }
  }

  private void getVariables() {
    // to arc [ :incr :degrees ]
    // to arc [ ]
    definedNode = (UserDefinedNode) parameters.get(0);
    for(int i = 2; i < firstEnd; i++){
      definedNode.addVariableName((VariableNode) parameters.get(i));
    }
  }

  private int getFirstEnd() {
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
