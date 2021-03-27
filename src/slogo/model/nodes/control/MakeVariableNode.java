package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for a make and/or set command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class MakeVariableNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command expects.
   */
  public MakeVariableNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of the value that we want to set in the variable.
   * @throws IllegalArgumentException if a variable node was not given to this node.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) throws IllegalArgumentException {
    double value = parameters.get(1).getReturnValue(tracker);
    SlogoNode leftChild = parameters.get(0);
    if (leftChild instanceof VariableNode) {
      ((VariableNode) leftChild).setValue(value);
    } else {
      throw new IllegalArgumentException("make/set was not given a variable");
    }
    return value;
  }
}
