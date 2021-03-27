/**
 * Represents the node of the SlogoNode tree for a arctangent  command.
 *
 * @author Andre Wang
 */


package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class ArcTangentNode extends SlogoNode {

  private List<SlogoNode> parameters;


  /**
   *
   * @param numParameters, the number of parameters
   */
  public ArcTangentNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }


  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return use the arctangent function to return a double, arctangent(x)
   */

  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.atan(Math.toRadians(parameters.get(0).getReturnValue(tracker)));
  }
}
