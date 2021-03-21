package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.MakeUserInstructionNode;
import slogo.model.nodes.control.RepeatNode;
import slogo.model.nodes.control.VariableNode;

public class NodeFactory {

  public SlogoNode getNode(String symbol, String value, Class<?> node, int parameters, Map<String, Double> variables, Map<String,
      MakeUserInstructionNode> userDefinedCommands, SlogoNode prev, Map<String, String> userDefinedCommandsInString)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    SlogoNode curr = prev;
    switch(symbol) {
      case "Constant" -> curr = constantCase(value, parameters);
      case "Variable" -> curr = variableCase(value, parameters, variables);
      case "Repeat" -> curr = repeatCase(parameters, variables);
      case "Command" -> {
        curr = commandCase(curr, value, userDefinedCommands);
        if(curr == null) { return null; }
      }
      case "MakeUserInstruction" -> curr = makeCase(parameters, userDefinedCommandsInString);
      default -> curr = (SlogoNode) node.getDeclaredConstructor(Integer.TYPE).newInstance(parameters);
    }
    curr.setString(value); // set the string for curr Node
    return curr;
  }

  private SlogoNode makeCase(int parameters, Map<String, String> userDefinedCommandsInString) {
    return new MakeUserInstructionNode(parameters, userDefinedCommandsInString);
  }

  private SlogoNode commandCase(SlogoNode curr, String value, Map<String,
      MakeUserInstructionNode> userDefinedCommands) throws IllegalArgumentException {
    if (curr instanceof MakeUserInstructionNode) {
      ((MakeUserInstructionNode) curr).setMethodName(value);
      userDefinedCommands.put(value, (MakeUserInstructionNode) curr);
      return null; // command reader should treat this as a continue
    } else {
      if (!userDefinedCommands.containsKey(value)) {
        throw new IllegalArgumentException("Command " + value + " undefined!");
      } else {
        return userDefinedCommands.get(value).createNode();
      }
    }
  }

  private SlogoNode repeatCase(int parameters, Map<String, Double> variables) {
    // needs the map of variables in constructor to add repcount variable
    return new RepeatNode(parameters, variables);
  }

  private SlogoNode variableCase(String value, int parameters, Map<String, Double> variables) {
    // s is the value we read, symbol is the classification
    return new VariableNode(parameters, variables, value);
  }

  private SlogoNode constantCase(String value, int parameters) {
    // guaranteed to be parsable to double since our regex will catch something that isn't
    // if stack is empty and we see a constant, it doesn't do anything to the program but
    // we still add it to the tree
    return new ConstantNode(parameters, Double.parseDouble(value));
  }
}
