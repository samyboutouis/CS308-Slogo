/**
 * Represents the node of the SlogoNode tree for a minus command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.math;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class MinusNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   *  constructor for minus node
   * @param numParameters for minus node
   */
  public MinusNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return, -x
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return -1 * parameters.get(0).getReturnValue(tracker);
  }
}