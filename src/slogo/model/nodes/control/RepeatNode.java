package slogo.model.nodes.control;

import java.util.List;
import java.util.Map;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Represents the node of the SlogoNode tree for a repeat command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class RepeatNode extends BracketNode {

  private List<SlogoNode> parameters;
  private Map<String, Double> variables;
  private VariableNode variable;
  private double repeatEnd;

  /**
   * Constructor.
   *
   * @param numParameters number of bracket pairs expected.
   */
  public RepeatNode(int numParameters, Map<String, Double> variables) {
    super(numParameters); // parameters being full determined like IfNode (last one is ListEnd)
    this.variables = variables;
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of last executed command in loop.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    // assume expr only results in one value, so parameters.get(1) is start bracket
    getIndexing(tracker);
    double ret = 0;
    for (double i = 1; i <= repeatEnd; i++) {
      variable.setValue(i);
      for (int j = 1; j < parameters.size(); j++) { // starts at j = 1 to avoid initial expression
        if (!(parameters.get(j) instanceof ListStartNode) && !(parameters
            .get(j) instanceof ListEndNode)) {
          ret = parameters.get(j).getReturnValue(tracker); // ret is value of last command executed
        }
      }
    }
    return ret;
  }

  // get how many times we repeat
  private void getIndexing(BackEndTurtleTracker tracker) {
    // repeat fd 50 [ fd 50 ]
    // i = 1 is left bracket
    variable = new VariableNode(0, variables, ":repcount");
    repeatEnd = parameters.get(0).getReturnValue(tracker);
  }
}
