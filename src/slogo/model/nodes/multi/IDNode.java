package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for an id command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class IDNode extends SlogoNode {

  /**
   * Constructor for ID node.
   *
   * @param numParameters amount of bracket pairs an id command takes (0)
   */
  public IDNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return id of current turtle.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return tracker.getCurr();
  }
}
