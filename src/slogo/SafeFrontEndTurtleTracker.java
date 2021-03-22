package slogo;

// what backend has access to and can pass to each Command object for the command to run
public interface SafeFrontEndTurtleTracker {

  // defines the methods that backend turtle tracker can call on the front end turtle
  void setActive(int id);

  void setInactive(int id);

  void setBackgroundColor(int index);

  void updatePalette(int index, int r, int g, int b);
}
