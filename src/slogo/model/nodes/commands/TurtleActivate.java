package slogo.model.nodes.commands;

import slogo.BackEndTurtle;

@FunctionalInterface
public interface TurtleActivate {

  void activate(BackEndTurtle currTurtle, int id, boolean isActive);
}
