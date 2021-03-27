package slogo.model.nodes.control;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for a list start symbol.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class ListStartNode extends SlogoNode {

  /**
   * Constructor.
   * @param numParameters 0 since it is full immediately
   */
  public ListStartNode(int numParameters) {
    super(numParameters); // numParameters = 0, so it is always full
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return 0 but will be ignored by calling classes.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    // nodes that use bracket node will know to ignore the bracket return value
    return 0;
  }
}
