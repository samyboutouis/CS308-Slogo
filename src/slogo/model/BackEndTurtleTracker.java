package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;

// manages all turtles in the backend,
public class BackEndTurtleTracker {

  // assume each new Tell overrides previous Tell
  private Map<Integer, BackEndTurtle> allTurtles;
  private List<Integer> activeTurtles;
  private int currTurtle;
  private SafeFrontEndTurtleTracker safeTurtleTracker;

  // frontend would pass these in so we can create a back end turtle
  public BackEndTurtleTracker(Map<Integer, BackEndTurtle> allTurtles, List<Integer> activeTurtles, SafeFrontEndTurtleTracker safeTurtleTracker) {
    this.allTurtles = allTurtles;
    this.activeTurtles = activeTurtles;
    this.safeTurtleTracker = safeTurtleTracker;
    currTurtle = activeTurtles.get(0); // assumes at least one turtle is on the screen
  }

  public BackEndTurtleTracker() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  // FIX: front end needs to know which turtle to run each list of commands on, so we can't
  // just concatenate all command lists
  public List<Command> getAllCommands(){
    List<Command> allCommands = new ArrayList<>();
    Iterator<Integer> itrn = getIterator();
    while (itrn.hasNext()){
      allCommands.addAll(getTurtle(itrn.next()).getCommands());
    }
    return allCommands;
  }

  public void deleteAllData(){
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  // FIX: does this make sure last turtle is last in the activeTurtles list?
  // If activeTurtles contains the last turtle, its position won't be moved to the back
  // add a backend turtle to turtle tracker to both allTurtles and list of activeTurtles
  public void addTurtle(BackEndTurtle turtle){
    // I (felix) edited the code so that if the turtle was already active, it would be put at the end of the list
    // then extracted common parts in the if else statements
    if (allTurtles.containsKey(turtle.getIndex())){
      if (activeTurtles.contains(turtle.getIndex())){
        activeTurtles.remove(Integer.valueOf(turtle.getIndex())); // remove object Integer, not at index
      }
    }
    else {
      allTurtles.put(turtle.getIndex(), turtle);
    }
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

  public int turtles() {
    return allTurtles.keySet().size();
  }

//  tell [ 1 2 3 ]
//  set :x 10
//  fd * id :x

  // tell [ 1 2 ]
  // 2 6, 3 12, 4 20
  // fd fd 20
  // order of things done: 1fd 21, 2fd 22, 1fd 22, fd



}