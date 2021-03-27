package slogo.model.nodes.control;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for a constant.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ConstantNode extends SlogoNode {

  private double value;

  /**
   * Constructor for constant node.
   *
   * @param numParameters number of parameters (0) for a constant node
   * @param value value of the constant
   */
  public ConstantNode(int numParameters, double value) {
    super(numParameters); // should be 0 when created
    this.value = value;
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of the constant.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return value;
  }

}
