package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtle;

/**
 * Functional Interface used to describe turtle action in calling class rather than in the
 * turtle command node.
 *
 *    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
 *       currTurtle.forward(values.get(0));
 *       currTurtle.addCommand(new MovementCommand(values.get(0), 0));
 *       return values.get(0);
 *     });
 *
 * () -> {} is the definition of the method turtleAction.
 *
 * @autho Felix Jiang
 */
@FunctionalInterface
public interface TurtleAction {

  /**
   * Defines the framework for creating the Command objects for the specific turtle using the
   * list of values.
   *
   * @param turtle turtle to have new command objects created.
   * @param values list of values that are the parameters of that command.
   * @return
   */
  double turtleAction(BackEndTurtle turtle, List<Double> values);
}
