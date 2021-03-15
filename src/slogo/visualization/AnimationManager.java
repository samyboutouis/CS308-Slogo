package slogo.visualization;

import java.util.List;
import slogo.Command;
import slogo.FrontEndTurtle;

public class AnimationManager {

  private final List<Command> commands;
  private final FrontEndTurtle frontEndTurtle;

  public AnimationManager(List<Command> commands, FrontEndTurtle frontEndTurtle){
    this.commands = commands;
    this.frontEndTurtle = frontEndTurtle;

    playAnimation();
  }

  private void playAnimation(){
    for(Command c : commands) {
      c.doCommand(frontEndTurtle);
    }
  }
}
