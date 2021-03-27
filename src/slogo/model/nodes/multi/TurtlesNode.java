package slogo.model.nodes.multi;

import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for a turtles command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class TurtlesNode extends SlogoNode {

  /**
   * Constructor for turtles node.
   *
   * @param numParameters amount of parameters a turtles command takes (0)
   */
  public TurtlesNode(int numParameters) {
    super(numParameters);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return amount of turtles created.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return tracker.turtles();
  }
}
