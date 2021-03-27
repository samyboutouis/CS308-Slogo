/**
 * Represents the node of the SlogoNode tree for a not command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
package slogo.model.nodes.booleans;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class NotNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for a not node
   * @param numParameters
   */
  public NotNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
    setBooleanNode(true);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return if x==0 return 1, else 0
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if (parameters.get(0).getReturnValue(tracker) == 0.0) {
      return 1;
    }
    return 0;
  }
}
