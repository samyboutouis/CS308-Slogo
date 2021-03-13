package slogo.visualization;

import java.util.List;
import slogo.Command;
import slogo.Turtle;

public class AnimationManager {

  private final List<Command> commands;
  private final Turtle turtle;

  public AnimationManager(List<Command> commands, Turtle turtle){
    this.commands = commands;
    this.turtle = turtle;

    playAnimation();
  }

  private void playAnimation(){
    for(Command c : commands) {
      c.doCommand(turtle);
    }
  }
}
