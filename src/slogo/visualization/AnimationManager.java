package slogo.visualization;

import java.sql.Time;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import slogo.Command;
import slogo.Turtle;

public class AnimationManager {

  private final List<Command> commands;
  private final Turtle turtle;
  private int frameIndex;

  private Timeline animation;

  public AnimationManager(List<Command> commands, Turtle turtle){
    this.commands = commands;
    this.turtle = turtle;

    setupTimeline();
  }

  private void setupTimeline(){
    int FPS = 10;
    double secondDelay = 1.0 / FPS;

    frameIndex = 0;

    KeyFrame keyframe = new KeyFrame(Duration.seconds(secondDelay), e -> {
      stepAnimation();
    });

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
    animation.play();
  }

  private void stepAnimation(){
    if(turtle != null){
      commands.get(frameIndex).doCommand(turtle);
      frameIndex++;
      if (frameIndex >= commands.size()){
        animation.stop();
      }
    } else {
      animation.stop();
    }

  }
}
