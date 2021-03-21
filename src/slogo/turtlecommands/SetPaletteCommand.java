package slogo.turtlecommands;

import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;

public class SetPaletteCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private int index;
  private int r;
  private int g;
  private int b;

  public SetPaletteCommand(SafeFrontEndTurtleTracker safe, int index, int r, int g, int b) {
    this.safe = safe;
    this.index = index;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    safe.updatePalette(index, r, g, b);
  }
}
