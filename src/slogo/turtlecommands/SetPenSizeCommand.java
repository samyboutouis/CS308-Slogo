package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.FrontEndTurtle;

public class SetPenSizeCommand implements Command {

  private double thickness;

  public SetPenSizeCommand(double thickness) {
    this.thickness = thickness;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setPenThickness(thickness);
  }
}
