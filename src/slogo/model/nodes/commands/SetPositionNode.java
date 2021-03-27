package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.SlogoNode;
import slogo.model.BackEndTurtleTracker;
import slogo.turtlecommands.SetPositionCommand;

/**
 * Represents the node of the SlogoNode tree for a set position command.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetPositionNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;

  /**
   * Constructor for node.
   *
   * @param numParameters number of parameters a set position node takes (2)
   */
  public SetPositionNode(int numParameters) {
    super(numParameters);
    parameters = super.getParameters();
  }

  /**
   *
   * @param tracker keeps track of all the turtles, allows commands that require receiving turtle
   *                information or adding commands to a turtle to do so with the parameter, rather
   *                than an instance variable present in every subclass.
   * @return distance turtle moved.
   */
  @Override
  public double getReturnValue(BackEndTurtleTracker tracker) {
    return super.loopThroughTurtles(tracker, parameters, (currTurtle, values) -> {
      currTurtle.addCommand(new SetPositionCommand(values.get(0), values.get(1)));
      double prevX = currTurtle.getX();
      double prevY = currTurtle.getY();
      currTurtle.setXY(values.get(0), values.get(1));
      return Math
          .sqrt(Math.pow(prevX - currTurtle.getX(), 2) + Math.pow(prevY - currTurtle.getY(), 2));
    });
  }
}
