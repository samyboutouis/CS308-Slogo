/**
 * Represents the node of the SlogoNode tree for a sine command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class SineNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for sine node
   * @param numParameters
   */
  public SineNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }


  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return sin(x), after x is converted to radians from degrees
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.sin(Math.toRadians(parameters.get(0).getReturnValue(tracker)));
  }
}
