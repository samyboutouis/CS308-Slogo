package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import slogo.BackEndTurtle;
import slogo.Command;
import slogo.SafeFrontEndTurtleTracker;
import slogo.turtlecommands.TellCommand;

// manages all turtles in the backend,
public class BackEndTurtleTracker {

  // assume each new Tell overrides previous Tell
  private Map<Integer, BackEndTurtle> allTurtles;
  private List<Integer> activeTurtles; // current active turtles, from Ask or Tell
  private List<Integer> tellActiveTurtles; // most recent tell command defined tell
  private Stack<List<Integer>> askActiveTurtles; // represents all active turtles defined by asks (could be nested asks)
  private int currTurtle;
  private SafeFrontEndTurtleTracker safeTurtleTracker;

  // frontend would pass these in so we can create a back end turtle
  public BackEndTurtleTracker(Map<Integer, BackEndTurtle> allTurtles, List<Integer> activeTurtles, SafeFrontEndTurtleTracker safeTurtleTracker) {
    this.allTurtles = allTurtles;
    this.activeTurtles = activeTurtles;
    this.safeTurtleTracker = safeTurtleTracker;
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>(activeTurtles);
    currTurtle = activeTurtles.get(0); // assumes at least one turtle is on the screen
  }

  public BackEndTurtleTracker() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>();
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

  public Map<Integer, List<Command>> getAllTurtleCommands() {
    Map<Integer, List<Command>> ret = new HashMap<>();
    for(Integer id : allTurtles.keySet()) {
      ret.put(id, allTurtles.get(id).getCommands());
    }
    return ret;
  }

  public Set<Integer> getAllTurtles(){
    return allTurtles.keySet();
  }

  public SafeFrontEndTurtleTracker getSafe() {
    return safeTurtleTracker;
  }

  public void deleteAllData(){
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  // if this turtleId already exists, we only update activeTurtles, and not allTurtles
  public void addTurtle(BackEndTurtle turtle){
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

  // only used in AskWithNode, to temporarily set the active turtle list to a specific turtle, and run
  // the logic expression to see if that specific turtle satisfies the requirement in AskWith.
  public void checkOneTurtle(int id){
    clearActiveTurtles();
    activeTurtles.add(id);
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

  public void setTellList(List<Integer> tellList) {
    activeTurtles.clear();
    for(Integer i : tellList) {
      addTurtle(getBasicTurtle(i));
    }
    addTellCommands();
    tellActiveTurtles = new ArrayList<>(activeTurtles);
  }

  public void setAskList(List<Integer> askList) {
    activeTurtles.clear(); // tellActiveTurtles should have this handled
    for(Integer i : askList) {
      addTurtle(getBasicTurtle(i));
    }
    addTellCommands();
    askActiveTurtles.push(new ArrayList<>(activeTurtles));
    // at this point, top of askActiveTurtles == activeTurtles
  }

  // after ask is done, revert to previous tell list
  public void revertAskList() {
    // ask [ 2 3 ] [ fd 50 tell [ 1 4 ] bk 50 ]
    // turtles 2, 3 do fd 50, while turtles 1 4 do bk 50
    askActiveTurtles.pop();
    if(askActiveTurtles.isEmpty()) {
      activeTurtles = new ArrayList<>(tellActiveTurtles);
    }
    else {
      activeTurtles = new ArrayList<>(askActiveTurtles.peek());
    }
    addTellCommands();
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

  // add tellCommands to each turtle whenever tell, ask, and askWith are created
  private void addTellCommands () {
    for(Integer i : allTurtles.keySet()) {
      allTurtles.get(i).addCommand(new TellCommand(getSafe(), i, activeTurtles.contains(i)));
    }
  }

  private BackEndTurtle getBasicTurtle(int id) {
    return new BackEndTurtle(0,0,0,true,true, id);
  }

//  tell [ 1 2 3 ]
//  set :x 10
//  fd * id :x

  // tell [ 1 2 ]
  // 2 6, 3 12, 4 20
  // fd fd 20
  // order of things done: 1fd 21, 2fd 22, 1fd 22, fd



}
