package slogo.model.nodes.control;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for an if command.
 *
 * Assumes expression is only one command (with chained parameters if necessary, but only one value
 * to calculated in the expression).
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class IfNode extends BracketNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor.
   *
   * @param numParameters number of bracket pairs expected.
   */
  public IfNode(int numParameters) {
    super(numParameters); // dummy value since isFull is overridden
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of last command executed or 0 if expression is 0.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    // assume expr is just one tree
    double ret = 0;
    if (parameters.get(0).getReturnValue(tracker) != 0.0) { // does the expression part
      for (int i = 1; i < parameters.size(); i++) { // i = 1 should be list start
        if (!(parameters.get(i) instanceof ListStartNode) && !(parameters
            .get(i) instanceof ListEndNode)) {
          ret = parameters.get(i).getReturnValue(tracker); // ret is value of last command executed
        }
      }
    }
    return ret;
  }
}