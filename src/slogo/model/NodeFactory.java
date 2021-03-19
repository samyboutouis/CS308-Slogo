package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.MakeUserInstructionNode;
import slogo.model.nodes.control.RepeatNode;
import slogo.model.nodes.control.VariableNode;
import slogo.model.nodes.multi.TellNode;

public class NodeFactory {
    public SlogoNode getNode(String symbol, String value, Class<?> node, int parameters,
        TurtleTracker tracker, Map<String, Double> variables, Map<String,
        MakeUserInstructionNode> userDefinedCommands, SlogoNode prev)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      SlogoNode curr = prev;
      switch(symbol) {
        // handle separately: Constant, Variable
        case "Tell" -> {
          curr = new TellNode(parameters);
          tracker
              .clearActiveTurtles(); // clear the previous active list of turtles, to prepare room for new list of active turtles.
        }
        case "Constant" -> {
          curr = new ConstantNode(parameters, Double.parseDouble(value));
          // if stack is empty and we see a constant, it doesn't do anything to the program but
          // we still add it to the tree
        }
        case "Variable" -> {
          curr = new VariableNode(parameters, variables,
              value); // s is the value we read, symbol is the classification
        }
        case "Repeat" -> {
          // needs the map of variables in constructor to add repcount variable
          curr = new RepeatNode(parameters, variables);
        }
        case "Command" -> {
          if (curr instanceof MakeUserInstructionNode) {
            userDefinedCommands.put(value, (MakeUserInstructionNode) curr);
            return null; // command reader should treat this as a continue
          } else {
            if (!userDefinedCommands.containsKey(value)) {
              throw new IllegalArgumentException("Command " + value + " undefined!");
            } else {
              curr = userDefinedCommands.get(value).createNode();
            }
          }
        }
        case "MakeUserInstruction" -> {
          // curr = new MakeUserInstructionNode(parameters, userDefinedCommandsInString);
        }
        default -> {
          curr = (SlogoNode) node.getDeclaredConstructor(Integer.TYPE).newInstance(parameters);
        }
      }
      return curr;
    }
}
