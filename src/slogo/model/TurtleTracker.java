package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import slogo.BackEndTurtle;
import slogo.Command;

// manages all turtles in the backend,
public class TurtleTracker {

  // assume each new Tell overrides previous Tell
  private Map<Integer, BackEndTurtle> allTurtles;
  private List<Integer> activeTurtles;
  private int currTurtle;

  public TurtleTracker() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  public List<Command> getAllCommands(){
    List<Command> allCommands = new ArrayList<>();
    Iterator<Integer> itrn = getIterator();
    while (itrn.hasNext()){
      allCommands.addAll(getTurtle(itrn.next()).getCommands());
    }
    return allCommands;
  }

  // add a backend turtle to turtle tracker to both allTurtles and list of activeTurtles
  public void addTurtle(BackEndTurtle turtle){
    allTurtles.put(turtle.getIndex(), turtle);
    activeTurtles.add(turtle.getIndex());
  }

  // clear the list of activeTurtles
  public void clearActiveTurtles(){
    activeTurtles.clear();
  }

  public void clearAllCommands(){
    Iterator<Integer> itrn = getIterator();
    while (itrn.hasNext()){
      getTurtle(itrn.next()).clearCommands();
    }
  }

  // loops through active turtles for one node, will have many iterator instances out for nested commands e.g. fd fd 50
  public Iterator<Integer> getIterator() {
    // returns a new, different iterator each time
    return activeTurtles.iterator();
  }

  public BackEndTurtle getTurtle(int index) {
    // not possible for a turtle to not be in allTurtles when using iterator
    return allTurtles.get(index);
  }

  // represents current turtle globally
  public void setCurr(int index) {
    currTurtle = index;
  }

  // helpful for ID method
  public int getCurr() {
    return currTurtle;
    // tell [ 1 2 3 ] fd ID
  }

//  tell [ 1 2 3 ]
//  set :x 10
//  fd * id :x

  // tell [ 1 2 ]
  // 2 6, 3 12, 4 20
  // fd fd 20
  // order of things done: 1fd 21, 2fd 22, 1fd 22, fd



}
