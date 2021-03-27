/**
 * Represents the node of the SlogoNode tree for a cosine command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class CosineNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   *
   * @param numParameters, number of parameters a cosine node takes
   */
  public CosineNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return, first convert the degrees to radians, then use the cosine in math. cos(x)
   */

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.cos(Math.toRadians(parameters.get(0).getReturnValue(tracker)));
  }
}
