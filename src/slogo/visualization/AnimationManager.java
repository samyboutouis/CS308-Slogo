package slogo.visualization;

import java.sql.Time;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import slogo.Command;
import slogo.Turtle;

public class AnimationManager {

  private final List<Command> commands;
  private final Turtle turtle;

  private Timeline animation;

  public AnimationManager(List<Command> commands, Turtle turtle){
    this.commands = commands;
    this.turtle = turtle;

    setupTimeline();
  }

  private void setupTimeline(){
    int FPS = 10;
    double secondDelay = 1.0 / FPS;

    int framesCount = 0;

    KeyFrame keyframe = new KeyFrame(Duration.seconds(secondDelay), e -> {
      stepAnimation(framesCount);
    });

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
    animation.play();
  }

  private void stepAnimation(int index){
    commands.get(index).doCommand(turtle);
    index++;
    if (index >= commands.size()){
      animation.stop();
    }
  }
}
