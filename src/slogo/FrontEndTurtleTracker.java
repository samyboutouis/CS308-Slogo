package slogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;

public class FrontEndTurtleTracker implements SafeFrontEndTurtleTracker{
  // keep track of all the turtles in the front end
  private Map<Integer, FrontEndTurtle> allTurtles;
  private List<Integer> activeTurtles; // is this active turtles list needed? Where should be the "truth" of this information?

  public FrontEndTurtleTracker() {

  }

  public BackEndTurtleTracker passToBackEnd() {
    Map<Integer, BackEndTurtle> backEndAllTurtles = new HashMap<>();
    for(int i = 1; i <= allTurtles.size(); i++){
      backEndAllTurtles.put(i, new BackEndTurtle(allTurtles.get(i)));
    }
   return new BackEndTurtleTracker(backEndAllTurtles, new ArrayList<>(activeTurtles), this);
  }

  @Override
  public void setActive(int id) {
    if(!activeTurtles.contains(id)){
      activeTurtles.add(id);
    }
    allTurtles.get(id).setActive();
  }

  @Override
  public void setInactive(int id) {
    if(activeTurtles.contains(id)){
      activeTurtles.remove(Integer.valueOf(id));
    }
    allTurtles.get(id).setInactive();
  }

  public void addTurtle(FrontEndTurtle turtle) {
    int turtleID = 1;
    Set<Integer> allIDs = (TreeSet<Integer>) allTurtles.keySet();
    for(int id : allIDs) {
      if(turtleID == id) {
        turtleID++;
      } else {
        break;
      }
    }
    allTurtles.put(turtleID, turtle);
  }

  public void changeColor() {

  }
}
