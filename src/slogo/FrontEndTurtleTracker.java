package slogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.BackEndTurtleTracker;

public class FrontEndTurtleTracker implements SafeFrontEndTurtleTracker{
  // keep track of all the turtles in the front end
  private Map<Integer,FrontEndTurtle> allTurtles;
  private List<Integer> activeTurtles; // is this active turtles list needed? Where should be the "truth" of this information?

  public FrontEndTurtleTracker() {

  }

  public BackEndTurtleTracker passToBackEnd() {
    Map<Integer, BackEndTurtle> backEndAllTurtles = new HashMap<>();
    for(Integer i : allTurtles.keySet()){
      backEndAllTurtles.put(i, new BackEndTurtle(allTurtles.get(i)));
    }
   return new BackEndTurtleTracker(backEndAllTurtles, new ArrayList<>(activeTurtles), this);
  }

  @Override
  public void addTurtle(int id) {

  }
}
