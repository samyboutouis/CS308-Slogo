package slogo.model.nodes.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class MakeUserInstructionNode extends BracketNode {

  private List<SlogoNode> parameters;
  private List<SlogoNode> myCommands;
  private List<VariableNode> variableNames;
  private int firstEnd;
  private int ret;
  private String methodName;
  private Map<String, String> userDefinedCommandsInString;

  // this node creates the user defined node and adds it to the map of commands
  // that node needs a list of string to represent the variable names
  public MakeUserInstructionNode(int numParameters, Map<String, String> map) {
    super(numParameters); // dummy value since isFull is overridden
    parameters = super.getParameters();
    myCommands = new ArrayList<>();
    variableNames = new ArrayList<>();
    userDefinedCommandsInString = map;
  }

  public CommandNode createNode() {
    try {
      firstEnd = super.getFirstEnd(); // index of first end bracket
      getVariables();
      ret = 1;
      return new CommandNode(variableNames.size(), myCommands, variableNames);
    } catch (ClassCastException e) {
      // if variable wasn't a variable node
      // if we only define a command but don't call it again, this method won't run, but
      // ret still = 0 since that's its default value when initialized
      ret = 0;
      return null;
    }
    // create node creates an object with a reference to the variable and command lists
    // basically gives it the same commands to run, except their parameters will be different
    // last recursive node has parameters that make it not call the recursive part again
  }

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    getCommands(); // builds all the commands of this method, which could be recursive
    // if it were recursive, this is called after the entire method has been read, but we also handle
    // if it is called more than once because of the myCommands.clear() call

    userDefinedCommandsInString.put(methodName, getStringCommands());
    return ret;
  }

  public void setMethodName(String name) {
    methodName = name;
  }

  // the method which will return the string of commands used in the userdefined instruction Node
  private String getStringCommands() {
    String stringCommands = "";
    for (int i = 0; i < parameters.size(); i++) {
      stringCommands = stringCommands + " " + parameters.get(i).getMyString();
    }
    return stringCommands;
  }

  private void getCommands() {
    myCommands.clear();
    for (int i = firstEnd; i < parameters.size(); i++) {
      // technically could do i = firstEnd + 2 but our code handles i = firstEnd
      if (!(parameters.get(i) instanceof ListStartNode) && !(parameters
          .get(i) instanceof ListEndNode)) {
        myCommands.add(parameters.get(i));
      }
    }
  }

  private void getVariables() {
    // to arc [ :incr :degrees ]
    // to arc [ ]
    // i = 0 is left bracket since arc not added as a child
    variableNames.clear();
    for (int i = 1; i < firstEnd; i++) {
      variableNames.add((VariableNode) parameters.get(i));
    }
  }
}
