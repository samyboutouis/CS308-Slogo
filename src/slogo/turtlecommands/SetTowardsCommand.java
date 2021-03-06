package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

// handles TOWARDS
public class SetTowardsCommand implements Command {

  // deals with turtle call that sets direction to somewhere absolute using coordinates
  private double x;
  private double y;

  public SetTowardsCommand(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.towards(x, y);
  }
}
