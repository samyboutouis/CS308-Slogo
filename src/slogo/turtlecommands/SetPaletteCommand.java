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

  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    // safe.setPalette(index, r, g, b);
  }
}
