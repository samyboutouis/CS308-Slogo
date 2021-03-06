package slogo.model.nodes.commands;

import java.util.List;
import slogo.model.BackEndTurtle;

@FunctionalInterface
public interface TurtleAction {

  double turtleAction(BackEndTurtle turtle, List<Double> values);
}
