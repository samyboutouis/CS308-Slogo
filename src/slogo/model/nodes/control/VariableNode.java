package slogo.model.nodes.control;

import java.util.Map;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for a variable.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class VariableNode extends SlogoNode {

  private Map<String, Double> variables;
  private String name;

  /**
   * Constructor.
   *
   * @param numParameters variable node takes in no parameters.
   * @param variables map of variable names to values.
   * @param name name of variable (with colon included).
   */
  public VariableNode(int numParameters, Map<String, Double> variables, String name) {
    super(numParameters); // dummy value for parameters
    this.variables = variables;
    this.name = name;
  }

  /**
   * Set the value of this variable.
   * @param value variable value to be set.
   */
  public void setValue(double value) {
    variables.put(name, value);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of variable or 0.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if (!variables.containsKey(name)) {
      return 0;
    } else {
      return variables.get(name);
    }
  }
}
