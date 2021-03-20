package slogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.FrontEndTurtle;
import slogo.visualization.TurtleObserver;

public class FrontEndTurtleTracker implements SafeFrontEndTurtleTracker{
  private Map<Integer, FrontEndTurtle> allTurtles;
  private List<Integer> activeTurtles;
  private List<TurtleObserver> turtleObservers;

  public FrontEndTurtleTracker() {
    allTurtles = new TreeMap<>();
    activeTurtles = new ArrayList<>();
    turtleObservers = new ArrayList<>();
  }

  public void addObserver(TurtleObserver turtleObserver) {
    turtleObservers.add(turtleObserver);
  }

  public void removeObserver(TurtleObserver turtleObserver) {
    turtleObservers.remove(turtleObserver);
  }

  public void notifyObservers(List<Integer> list) {
    for(TurtleObserver turtleObserver : turtleObservers) {
      turtleObserver.update(list);
    }
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

  public void setActive(FrontEndTurtle frontEndTurtle) {
    Set<Integer> turtleIDSet = getKeyByValue(allTurtles, frontEndTurtle);
    int turtleID = turtleIDSet.iterator().next();
    if(!activeTurtles.contains(turtleID)){
      activeTurtles.add(turtleID);
    }
  }

  @Override
  public void setInactive(int id) {
    if(activeTurtles.contains(id)){
      activeTurtles.remove(Integer.valueOf(id));
    }
    allTurtles.get(id).setInactive();
  }

  public void setInactive(FrontEndTurtle frontEndTurtle) {
    Set<Integer> turtleIDSet = getKeyByValue(allTurtles, frontEndTurtle);
    int turtleID = turtleIDSet.iterator().next();
    if(activeTurtles.contains(turtleID)){
      activeTurtles.remove(Integer.valueOf(turtleID));
    }
  }

  public void addTurtle(FrontEndTurtle turtle) {
    int turtleID = 1;
    Set<Integer> allIDs =  allTurtles.keySet();
    for(int id : allIDs) {
      if(turtleID == id) {
        turtleID++;
      } else {
        break;
      }
    }
    allTurtles.put(turtleID, turtle);
    if(turtle.isActive()) activeTurtles.add(turtleID);
    notifyObservers(getTurtleIDs());
  }

  public void changeColor() {

  }

  public void forward(double pixels) {
    for(int id : activeTurtles) {
      allTurtles.get(id).forward(pixels);
    }
  }

  public void back(double pixels) {
    for(int id : activeTurtles) {
      allTurtles.get(id).back(pixels);
    }
  }

  public void rotate(double pixels) {
    for(int id : activeTurtles) {
      allTurtles.get(id).rotate(pixels);
    }
  }

  public static <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
    return map.entrySet()
      .stream()
      .filter(entry -> Objects.equals(entry.getValue(), value))
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet());
  }

  public List<SafeTurtle> getSafeFrontEndTurtles() {
    return allTurtles.entrySet()
      .stream()
      .map(Map.Entry::getValue)
      .collect(Collectors.toUnmodifiableList());
  }

  private List<Integer> getTurtleIDs() {
    return allTurtles.entrySet()
      .stream()
      .map(Map.Entry::getKey)
      .collect(Collectors.toUnmodifiableList());
  }
}
