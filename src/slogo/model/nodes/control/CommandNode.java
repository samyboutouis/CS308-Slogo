package slogo.model.nodes.control;

import java.util.ArrayList;
import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the actual node when a user calls the command they defined, so return value is not
 * just 1 or 0.
 *
 * In a recursive user defined command, each command node will have the same list of variable nodes
 * and same myCommands but the parameters are different, that those are what determines the exit
 * condition.
 *
 *      return new CommandNode(variableNames.size(), myCommands, variableNames);
 *      // in MakeUserInstructionNode
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class CommandNode extends SlogoNode {

  private List<SlogoNode> myCommands;
  private List<SlogoNode> parameters;
  private List<VariableNode> variables;
  private List<Double> prevValues;

  /**
   * Constructor for command node. Created in a MakeUserInstructionNode each time the command name
   * is seen in the program (including in its definition itself if it is recursive).
   *
   * @param numParameters number of parameters for this custom node
   * @param myCommands list of commands that are in this user defined command
   * @param variables list of variable nodes that are part of this user defined command.
   */
  public CommandNode(int numParameters, List<SlogoNode> myCommands, List<VariableNode> variables) {
    super(numParameters);
    parameters = super.getParameters();
    this.myCommands = myCommands;
    this.variables = variables;
    prevValues = new ArrayList<>();
  }

  /**
   * Execute the user defined commands by looping through each node in myCommands.
   *
   * Update variable scope by saving variable values before execution and replacing them after.
   *
   * Calculate parameter values and set those to the variable nodes before command is run.
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of the last executed command in the user defined command.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    double ret = 0;
    setParameters(tracker);
    for (SlogoNode node : myCommands) {
      ret = node.getReturnValue(tracker);
    }
    updateVariableScope();
    return ret;
  }

  // size of parameters must be equal to size of variables
  private void setParameters(BackEndTurtleTracker tracker) {
    for (int i = 0; i < variables.size(); i++) {
      // keep track of previous values once this command returns
      prevValues.add(variables.get(i).getReturnValue(tracker));
      variables.get(i).setValue(parameters.get(i).getReturnValue(tracker));
    }
  }

  private void updateVariableScope() {
    for (int i = 0; i < variables.size(); i++) {
      // if variable was undefined before, it's just 0 since it wasn't in the map
      variables.get(i).setValue(prevValues.get(i));
    }
  }
}
