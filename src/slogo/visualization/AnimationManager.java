package slogo.visualization;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import slogo.Command;
import slogo.visualization.turtle.FrontEndTurtle;
import slogo.visualization.turtle.FrontEndTurtleTracker;

public class AnimationManager {

  private static final int FPS = 100;
  private static final double SECOND_DELAY = 1.0 / FPS;

  private final Map<Integer, List<Command>> commands;
  private final FrontEndTurtleTracker turtleTracker;

  private Timeline animation;
  private Iterator<Integer> turtleIterator;
  private int frameIndex;
  private int turtleID;

  public AnimationManager(Map<Integer, List<Command>> map,
      FrontEndTurtleTracker frontEndTurtleTracker) {
    this.commands = map;
    this.turtleTracker = frontEndTurtleTracker;

    setupTimeline();
  }

  private void setupTimeline() {
    frameIndex = -1;
    turtleIterator = commands.keySet().iterator();
    addTurtles();

    KeyFrame keyframe = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
      stepIndex();
    });

    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(keyframe);
    animation.play();
  }

  private void stepIndex() {
    if (frameIndex == -1 || frameIndex >= commands.get(turtleID).size()) {
      if (!turtleIterator.hasNext()) {
        animation.stop();
        return;
      } else {
        turtleID = turtleIterator.next();
        frameIndex = 0;
      }
    }
    stepAnimation();
  }

  private void stepAnimation() {
    FrontEndTurtle turtle = turtleTracker.getFrontEndTurtle(turtleID);
    List<Command> commandList = commands.get(turtleID);
    if (frameIndex < commandList.size()) {
      commandList.get(frameIndex).doCommand(turtle);
      turtleTracker.notifyUpdateTurtleState(turtleID);
    }
    frameIndex++;
  }

  private void addTurtles() {
    for (int id : commands.keySet()) {
      if (!turtleTracker.turtleExists(id)) {
        FrontEndTurtle turtle = new FrontEndTurtle(turtleTracker);
        turtleTracker.addTurtle(turtle);
      }
    }
  }
}