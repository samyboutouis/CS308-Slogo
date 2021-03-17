package slogo.model.nodes.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.Turtle;
import slogo.model.TurtleTracker;
import slogo.turtlecommands.SetTowardsCommand;
import slogo.model.SlogoNode;

public class SetTowardsNode extends TurtleCommandNode {

  private List<SlogoNode> parameters;
  private List<Double> values;
  private Turtle turtle;

  public SetTowardsNode(int numParameters, BackEndTurtle turtle){
    super(numParameters);
    parameters = super.getParameters();
    this.turtle = turtle;
  }

  @Override
  public double getReturnValue(TurtleTracker tracker) {
    return super.loopThroughTurtles(tracker,parameters, (currTurtle, values)->{
      currTurtle.addCommand(new SetTowardsCommand(values.get(0),values.get(1)));
      double prevHeading = currTurtle.getDirection();
      currTurtle.towards(values.get(0), values.get(1));
      return Math.abs(prevHeading - currTurtle.getDirection());
    });
  }

  /*@Override
  public double getReturnValue(List<Command> commands) {
    values = super.getValues(commands, parameters);
    createMovement(commands);
    double prevHeading = turtle.getDirection();
    turtle.towards(values.get(0), values.get(1));
    return Math.abs(prevHeading - turtle.getDirection());
  }

  private void createMovement(List<Command> commands) {
    commands.add(new SetTowardsCommand(values.get(0), values.get(1)));
  }*/


}
