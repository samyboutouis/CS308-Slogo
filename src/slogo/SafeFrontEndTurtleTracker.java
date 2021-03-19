package slogo;

public interface SafeFrontEndTurtleTracker {
  // defines the methods that backend turtle tracker can call on the front end turtle
  void addTurtle(int id);

  // can probably but methods that change global color in here as well
}
