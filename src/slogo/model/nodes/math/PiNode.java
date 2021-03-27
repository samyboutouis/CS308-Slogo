/**
 * Represents the node of the SlogoNode tree for a Pi command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class PiNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for PiNode
   * @param numParameters is 0
   */
  public PiNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return the constant Pi
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.PI;
  }
}