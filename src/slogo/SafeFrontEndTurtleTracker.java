package slogo;

public interface SafeFrontEndTurtleTracker {
  // defines the methods that backend turtle tracker can call on the front end turtle
  void addTurtle(int id);

  // can probably but methods that change global color in here as well
  // Actually since color changes can occur in the middle of a long command, those need to be ran at the correct times, so either turtle class should have access to changing colors and adding turtles, or we pass in a bigger object that does what each turtle does but also changes the state of display
  // OR we could pass in this class into those Command interface classes when we construct them, then it will be called in sync and no other code will require any changes. 
}