package slogo.model.nodes.control;

import java.util.ArrayList;
import java.util.List;
import slogo.Command;
import slogo.model.SlogoNode;

public class MakeUserInstructionNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private List<SlogoNode> myCommands;
  private List<VariableNode> variableNames;
  private int brackets;
  private int firstEnd;
  private int ret;

  // this node creates the user defined node and adds it to the map of commands
  // that node needs a list of string to represent the variable names

  public MakeUserInstructionNode(int numParameters) {
    super(numParameters); // dummy value since isFull is overridden
    brackets = numParameters;
    parameters = super.getParameters();
    myCommands = new ArrayList<>();
    variableNames = new ArrayList<>();
  }

  public CommandNode createNode() {
    try{
      getFirstEnd(); // index of first end bracket
      getVariables();
      getCommands();
      ret = 1;
      return new CommandNode(variableNames.size(), myCommands, variableNames);
    } catch( ClassCastException e) {
      // if variable wasn't a variable node
      ret = 0;
      return null;
    }
  }

  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && checkBrackets();
  }

  @Override
  public double getReturnValue(List<Command> commands) {
    return ret;
  }

  private void getCommands() {
    myCommands.clear();
    for(int i = firstEnd; i < parameters.size(); i++){ // technically could do i = firstEnd + 2 but our code handles i = firstEnd
      if(!(parameters.get(i) instanceof ListStartNode) && !(parameters.get(i) instanceof ListEndNode)){
        myCommands.add(parameters.get(i));
      }
    }
  }

  private void getVariables() {
    // to arc [ :incr :degrees ]
    // to arc [ ]
    // i = 0 is left bracket since arc not added as a child
    variableNames.clear();
    for(int i = 1; i < firstEnd; i++){
      variableNames.add((VariableNode) parameters.get(i));
    }
  }

  private void getFirstEnd() {
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
