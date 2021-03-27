/**
 * Represents the node of the SlogoNode tree for a random command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class RandomNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for RandomNode
   * @param numParameters
   */
  public RandomNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return a random number between 0 to X
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return Math.random() * parameters.get(0).getReturnValue(tracker);
  }
}