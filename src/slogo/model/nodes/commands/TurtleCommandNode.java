package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import slogo.model.BackEndTurtle;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;

/**
 * Second level of abstraction for all command nodes that affect individual turtles.
 *
 * Commands that must run on each active turtle generally extend this class rather than SlogoNode,
 * since this class provides a lambda for looping through all turtles that are active.
 *
 * Most nodes in this package of commands extend this class.
 *
 *      return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
 *       currTurtle.forward(values.get(0));
 *       currTurtle.addCommand(new MovementCommand(values.get(0), 0));
 *       return values.get(0);
 *     });
 *
 * Use of this class in forward node.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public abstract class TurtleCommandNode extends SlogoNode {

  /**
   * Constructor for this middle abstraction node.
   *
   * Still needs to call super since it extends SlogoNode.
   *
   * @param numParameters number of parameters/bracket pairs in this command.
   */
  public TurtleCommandNode(int numParameters) {
    super(numParameters);
  }

  /**
   * Loop through all the current active turtles and passes back the calculated values of the
   * parameters of the command to the calling node in order to use them to create commands.
   *
   * @param tracker tracker used to call getReturnValue on each of the parameters.
   * @param parameters List of parameters that need to be calculated by the node calling this
   *                   method.
   * @param action functional interface that describes what actions to do once those values are
   *               calculated by this class. This interface is defined in the method call for this
   *               method. Uses Java lambda expressions.
   * @return value of the command executed on the last turtle. Each turtle return value is passed
   * back to this method, which then returns back the return value of the last turtle.
   */
  protected double loopThroughTurtles(BackEndTurtleTracker tracker, List<SlogoNode> parameters,
      TurtleAction action) {
    Iterator<Integer> itr = tracker.getIterator();
    double ret = 0.0;
    while (itr.hasNext()) {
      int id = itr.next();
      tracker.setCurr(id);
      BackEndTurtle currTurtle = tracker.getTurtle(id);
      ret = action.turtleAction(currTurtle, getValues(tracker, parameters));
    }
    return ret;
  }

  private List<Double> getValues(BackEndTurtleTracker tracker, List<SlogoNode> parameters) {
    List<Double> values = new ArrayList<>();
    for (SlogoNode node : parameters) {
      values.add(node.getReturnValue(tracker));
      // runs on current turtle that was just set in while loop
    }
    return values;
  }
}
