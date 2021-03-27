/**
 * Represents the node of the SlogoNode tree for a equal command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
package slogo.model.nodes.booleans;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

public class EqualNode extends SlogoNode {

  private List<SlogoNode> parameters;

  /**
   * constructor for equal node
   * @param numParameters
   */
  public EqualNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
    setBooleanNode(true);
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return returns x==y
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    if (Double.compare(parameters.get(0).getReturnValue(tracker),
        parameters.get(1).getReturnValue(tracker)) == 0) {
      return 1;
    }
    return 0;
  }
}
