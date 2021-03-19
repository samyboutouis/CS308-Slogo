package slogo;

import java.util.List;
import java.util.Map;
import slogo.model.BackEndTurtleTracker;

public class FrontEndTurtleTracker {
  // keep track of all the turtles in the front end
  private Map<Integer,FrontEndTurtle> allTurtles;
  private List<Integer> activeTurtles;

  public FrontEndTurtleTracker() {

  }

  public BackEndTurtleTracker passToBackEnd() {
   return null;
  }
}
