/**
 * Represents the node of the SlogoNode tree for a quotient command.
 *
 * @author Andre Wang
 */

package slogo.model.nodes.math;


import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class QuotientNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for Quotient Node
   * @param numParameters for quotient node
   */
  public QuotientNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return x/y
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return parameters.get(0).getReturnValue(tracker) / parameters.get(1).getReturnValue(tracker);
  }
}

