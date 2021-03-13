package slogo.turtlecommands;

import slogo.Command;
import slogo.Turtle;

public class MovementCommand implements Command {
  private double forward;
  private double directionChange;

  // forward and directionChange are relative values
  // handles Forward, Backward, Right, Left
  public MovementCommand(double forward, double directionChange){
    this.forward = forward;
    this.directionChange = directionChange;
  }

  @Override
  public void doCommand(Turtle turtle) {
    turtle.forward(forward);
    turtle.rotate(directionChange);
  }
}




