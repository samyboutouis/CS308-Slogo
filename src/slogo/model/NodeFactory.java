package slogo.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import slogo.model.nodes.control.ConstantNode;
import slogo.model.nodes.control.MakeUserInstructionNode;
import slogo.model.nodes.control.RepeatNode;
import slogo.model.nodes.control.VariableNode;

/**
 * A factory for creating subclasses of SlogoNode.
 *
 * Assumes all information passed in during method call is accurate. Assumes returning null
 * will be handled correctly by calling class (CommandReader) to continue the loop.
 *
 * Depends on Reflection and all SlogoNode subclasses. Used only by CommandReader.
 *
 * Create all the correct values to pass into the getNode method and then call getNode:
 *
 *      curr = nodeFactory.getNode(symbol, s, node, parameters, variables, userDefinedCommands,
 *             curr, userDefinedCommandsInString);
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class NodeFactory {

  /**
   * Sole public method of this class. Creates a SlogoNode subclass using reflection. A majority
   * of nodes are created using reflection, only a few cases requiring passing additional
   * information to the node.
   *
   * Assumes calling class (CommandReader) will understand a null return value means to continue
   * the for loop because we do not yet make the CommandNode for a user defined command if it
   * is still being defined (previous node was a MakeUserInstruction type node).
   *
   * Assumes SlogoNode prev was the previous node this method processed, only used when user
   * defines their own command.
   *
   * @param symbol regex symbol that is mapped from what the user typed in
   * @param value what the user typed in (each value is obtained by delimiting input by whitespace)
   * @param node Class object that can instantiate a class that has the type "?" in "<?>"
   * @param parameters number of parameters or pairs of bracket this command takes in
   * @param variables map of all user defined variable names to their value
   * @param userDefinedCommands map of all user defined command names to the node representing the
   *                            definition of that command
   * @param prev previous SlogoNode processed by the factory, used when user defines a command
   * @param userDefinedCommandsInString map of command name to its contents passed to a
   *                                    MakeUserInstructionNode
   * @return The SlogoNode corresponding to the value typed in by the user, or null if its the name
   * of the user defined command that is currently being defined
   * @throws NoSuchMethodException if reflection can not find a suitable constructor
   * @throws IllegalAccessException (from oracle javadocs) if this Constructor object is enforcing
   * Java language access control and the underlying constructor is inaccessible.
   * @throws InvocationTargetException (from oracle javadocs) if the underlying constructor throws
   * an exception.
   * @throws InstantiationException (from oracle javadocs) if the class that declares the
   * underlying constructor represents an abstract class.
   */
  public SlogoNode getNode(String symbol, String value, Class<?> node, int parameters,
      Map<String, Double> variables, Map<String,
      MakeUserInstructionNode> userDefinedCommands, SlogoNode prev,
      Map<String, String> userDefinedCommandsInString)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    SlogoNode curr = prev;
    switch (symbol) {
      case "Constant" -> curr = constantCase(value, parameters);
      case "Variable" -> curr = variableCase(value, parameters, variables);
      case "Repeat" -> curr = repeatCase(parameters, variables);
      case "Command" -> {
        curr = commandCase(curr, value, userDefinedCommands);
        if (curr == null) {
          return null;
        }
      }
      case "MakeUserInstruction" -> curr = makeCase(parameters, userDefinedCommandsInString);
      default -> curr = (SlogoNode) node.getDeclaredConstructor(Integer.TYPE)
          .newInstance(parameters);
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
