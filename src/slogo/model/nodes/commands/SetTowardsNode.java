package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetTowardsCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a set towards command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetTowardsNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a set towards node takes (2)
   */
  public SetTowardsNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return degrees that turtle turned.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetTowardsCommand(values.get(0), values.get(1)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.towards(values.get(0), values.get(1));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }
}
