package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.FrontEndTurtle;

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
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.forward(forward);
    frontEndTurtle.rotate(directionChange);
  }
}




