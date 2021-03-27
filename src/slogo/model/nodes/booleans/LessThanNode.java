/**
 * Represents the node of the SlogoNode tree for a less than command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
package slogo.model.nodes.booleans;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class LessThanNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for less than node
   * @param numParameters
   */
  public LessThanNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
    setBooleanNode(true);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return x<y
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if (parameters.get(0).getReturnValue(tracker) < parameters.get(1).getReturnValue(tracker)) {
      return 1;
    }
    return 0;
  }
}