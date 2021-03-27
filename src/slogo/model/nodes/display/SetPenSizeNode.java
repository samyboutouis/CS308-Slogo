package slogo.model.nodes.display;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.model.SlogoNode;
import slogo.model.nodes.commands.TurtleCommandNode;
import slogo.turtlecommands.SetPenSizeCommand;

/**
 * Represents the node of the SlogoNode tree for a set pen size command.
 *
 * @author Felix Jiang
 */
public class SetPenSizeNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor.
   *
   * @param numParameters number of parameters the command takes in
   */
  public SetPenSizeNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return value of pen size we want to set turtle to.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetPenSizeCommand(values.get(0)));
      return values.get(0);
    });
  }
}
