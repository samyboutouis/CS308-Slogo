package slogo.turtlecommands;

import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;

public class SetShapeCommand implements Command {

  private int index;

  public SetShapeCommand(int index){
    this.index = index;
  }

  @Override
  public void doCommand(FrontEndTurtle frontEndTurtle) {
    frontEndTurtle.setShape(index);
  }

}
