package slogo.model.nodes.control;

import java.util.ArrayList;
import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a group start symbol.
 *
 * What is created when a group start "(" is seen, represents the entire grouping structure
 *
 * Grouping is basically done by replacing each multiple of parameters in the command with the
 * group values until there are none left, and then returning the sum of outputs for each command
 * executed with its parameters.
 *
 * @author Felix Jiang
 */
public class GroupStartNode extends SlogoNode {

  private List<SlogoNode> parameters;
  private SlogoNode command;
  private int arguments;

  /**
   * Constructor for Group Start.
   *
   * @param numParameters 0 expected parameters
   */
  public GroupStartNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   * Group Start is full once we see a matching group end.
   * @return true if the last node in our children is a group end node.
   */
  @Override
  public boolean isFull() {
    return !parameters.isEmpty() && parameters.get(parameters.size() - 1) instanceof GroupEndNode;
  }

  /**
   * Group node will find all the parameter values, and then run the initial command on each
   * multiple of those parameters.
   *
   * Uses helper methods to get the argument values and to find the next set of parameters to run
   * on the initial command.
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return sum of return value of each multiple of parameters ran on the initial command.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    command = parameters.get(0);
    getArguments(tracker);
    double ret = command.getReturnValue(tracker);
    for (int i = 1; i < parameters.size() - 1; i = i + arguments) {
      // i represents start of subList of parameters
      // i = 0 was the command
      List<SlogoNode> newParams = getNewParams(i, i + arguments);
      command.replaceParameters(newParams);
      ret = ret + command.getReturnValue(tracker);
      // call command again with new parameters
    }
    return ret;
  }

  // ( sum 50 49 48 47 46 45 )
  // i = 0 is command node, i = 1 and on is extra values (since command node takes in its original parameters)
  // values: 48 47 46 45
  // tree:       sum  48  47  46  45  )
  //            50  49

  // plan to just sum up the output values, since sum grouping is very different than palette grouping
  // grouping is equivalent to calling the same command multiple times, but I assume the outputs are summed up

  private List<SlogoNode> getNewParams(int start, int end) {
    List<SlogoNode> ret = new ArrayList<>();
    for (int i = start; i < end; i++) {
      ret.add(parameters.get(i));
      // to be sent to command again
    }
    return ret;
  }

  private void getArguments(BackEndTurtleTracker tracker) {
    arguments = command.getNumParameters();
    if ((parameters.size() - 2) % arguments != 0) {
      // subtract off command node and group end
      throw new IllegalArgumentException("Grouping does not have correct multiple of arguments!");
    }
  }

}
