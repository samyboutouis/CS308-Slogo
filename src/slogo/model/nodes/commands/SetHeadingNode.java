package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetHeadingCommand;
import slogo.model.SlogoNode;

/**
 * Represents the node of the SlogoNode tree for a set heading command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetHeadingNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a set heading node takes (1)
   */
  public SetHeadingNode(int numParameters) {
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
      currTurtle.addCommand(new SetHeadingCommand(values.get(0)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.setDirection(values.get(0));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }
}
