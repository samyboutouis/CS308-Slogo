package slogo.turtlecommands;

import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents the Set Palette command done on a turtle.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class SetPaletteCommand implements Command {

  private SafeFrontEndTurtleTracker safe;
  private int index;
  private int r;
  private int g;
  private int b;

  /**
   * Constructor for command. Needs access to the tracker, index of palette, and new RGB values.
   *
   * @param safe tracker in order to call updatePalette on
   * @param index index of new palette color to add/update
   * @param r red value
   * @param g green value
   * @param b blue value
   */
  public SetPaletteCommand(SafeFrontEndTurtleTracker safe, int index, int r, int g, int b) {
    this.safe = safe;
    this.index = index;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Update palette of the workspace.
   *
   * @param frontEndTurtle unused in this command.
   */
  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    safe.updatePalette(index, r, g, b);
  }
}
