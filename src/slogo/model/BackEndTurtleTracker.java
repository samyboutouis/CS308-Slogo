package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import slogo.Command;
import slogo.SafeBackEndTurtleTracker;
import slogo.SafeFrontEndTurtleTracker;
import slogo.turtlecommands.TellCommand;

/**
 * Manages all the turtles in the backend. Each BackEndTurtle is responsible for keeping track
 * of the list of commands the corresponding frontEndTurtle will have to do. Turtles are
 * distinguished based on their ID as the key to the map allTurtles.
 *
 * Purpose of safeTurtleTracker instance is to provide for Command objects that need to call the
 * action on the frontEndTracker rather than a front end turtle (for changes such as set
 * background).
 *
 * In the front end, a backEndTurtleTracker is created in the FrontEndTurtleTracker.
 *
 *      BackEndTurtleTracker backEndTurtleTracker = turtleTracker.passToBackEnd();
 *      // turtleTracker is of type FrontEndTurtleTracker
 *
 * currTurtle represents the current turtle that is being looped through in a tell command or
 * ask/askwith command.
 *
 * A new tell command replaces the list of active turtles, and tell can never nest another tell.
 * However, ask/askwith can nest other ask/askwith, so those must have a stack that dynamically
 * keep track of previous active lists.
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public class BackEndTurtleTracker implements SafeBackEndTurtleTracker {

  // assume each new Tell overrides previous Tell
  private Map<Integer, BackEndTurtle> allTurtles;
  private List<Integer> activeTurtles; // current active turtles, from Ask or Tell
  private List<Integer> tellActiveTurtles; // most recent tell command defined tell
  private Stack<List<Integer>> askActiveTurtles; // represents all active turtles defined by asks (could be nested asks)
  private int currTurtle;
  private SafeFrontEndTurtleTracker safeTurtleTracker;

  /**
   * Creates a backend turtle tracker. Uses the safe tracker interface to easily get values and
   * also gets the list of all turtle and active turtles from the current state of the front
   * end.
   *
   * This constructor is used in the front end turtle tracker.
   *
   * @param allTurtles map of each turtle ID to the back end turtle corresponding to it
   * @param activeTurtles holds list of current active turtles set by user clicks/code
   * @param safeTurtleTracker controls group of turtles in the front end, passed to command objects
   */
  public BackEndTurtleTracker(Map<Integer, BackEndTurtle> allTurtles, List<Integer> activeTurtles,
      SafeFrontEndTurtleTracker safeTurtleTracker) {
    this.allTurtles = allTurtles;
    this.activeTurtles = activeTurtles;
    this.safeTurtleTracker = safeTurtleTracker;
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>(activeTurtles);
    currTurtle = activeTurtles.get(0); // assumes at least one turtle is on the screen
  }

  // mainly used for testing since we don't create a frontEndTracker
  // default turtle has Id 0 in this case
  public BackEndTurtleTracker() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  public Map<Integer, List<Command>> getAllTurtleCommands() {
    Map<Integer, List<Command>> ret = new HashMap<>();
    for (Integer id : allTurtles.keySet()) {
      ret.put(id, allTurtles.get(id).getCommands());
    }
    return ret;
  }

  public Set<Integer> getAllTurtles() {
    return allTurtles.keySet();
  }

  public SafeFrontEndTurtleTracker getSafe() {
    return safeTurtleTracker;
  }

  // used for tests that run multiple times; don't want previous test to affect next one
  public void deleteAllData() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  // just in case future tracker given to us has previous commands
  public void clearAllCommands() {
    Iterator<Integer> itr = getIterator();
    while (itr.hasNext()) {
      getTurtle(itr.next()).clearCommands();
    }
  }


  // if this turtleId already exists, we only update activeTurtles, and not allTurtles
  public void addTurtle(BackEndTurtle turtle) {
    if (allTurtles.containsKey(turtle.getIndex())) {
      if (activeTurtles.contains(turtle.getIndex())) {
        activeTurtles
            .remove(Integer.valueOf(turtle.getIndex())); // remove object Integer, not at index
      }
    } else {
      allTurtles.put(turtle.getIndex(), turtle);
    }
    activeTurtles.add(turtle.getIndex());
  }

  // only used in AskWithNode, to temporarily set the active turtle list to a specific turtle, and run
  // the logic expression to see if that specific turtle satisfies the requirement in AskWith.
  public void checkOneTurtle(int id) {
    activeTurtles.clear();
    activeTurtles.add(id);
  }

  public void setTellList(List<Integer> tellList) {
    // clear the previous active list of turtles, to prepare room for new list of active turtles.
    activeTurtles.clear();
    setCurr(tellList.get(0)); // default curr is first id
    for (Integer i : tellList) {
      addTurtle(getBasicTurtle(i));
    }
    addTellCommands();
    tellActiveTurtles = new ArrayList<>(activeTurtles);
  }

  public void setAskList(List<Integer> askList) {
    activeTurtles.clear(); // tellActiveTurtles should have this handled
    setCurr(askList.get(0)); // default curr is first id
    for (Integer i : askList) {
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
    if (askActiveTurtles.isEmpty()) {
      activeTurtles = new ArrayList<>(tellActiveTurtles);
    } else {
      activeTurtles = new ArrayList<>(askActiveTurtles.peek());
    }
    addTellCommands();
  }

  // loops through active turtles for one node, will have many iterator instances out for nested commands e.g. fd fd 50
  public Iterator<Integer> getIterator() {
    // returns a new, different iterator each time
    return activeTurtles.iterator();
  }

  // used by turtleCommandNode loop to access the turtle at this index of activeTurtles
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
  private void addTellCommands() {
    for (Integer i : allTurtles.keySet()) {
      allTurtles.get(i).addCommand(new TellCommand(getSafe(), i, activeTurtles.contains(i)));
    }
  }

  private BackEndTurtle getBasicTurtle(int id) {
    return allTurtles.getOrDefault(id, new BackEndTurtle(0, 0, 0, true, true, id));
  }

//  tell [ 1 2 3 ]
//  set :x 10
//  fd * id :x

  // tell [ 1 2 ]
  // 2 6, 3 12, 4 20
  // fd fd 20
  // order of things done: 1fd 21, 2fd 22, 1fd 22, fd


}
