package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.FrontEndTurtle;

// handles SETXY and GOTO
public class SetPositionCommand implements Command {
  private double x;
  private double y;

  public SetPositionCommand(double x, double y){
    this.x = x;
    this.y = y;
  }
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setXY(x, y);
  }
}
