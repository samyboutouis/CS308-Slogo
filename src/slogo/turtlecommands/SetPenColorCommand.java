package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.FrontEndTurtle;

public class SetPenColorCommand implements Command {

  private int index;

  public SetPenColorCommand(int index){
    this.index = index;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setPenColor(index);
  }
}
