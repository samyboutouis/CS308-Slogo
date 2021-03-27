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

  /**
   * Constructor only called during tests, since we do not create a front end tracker in tests.
   *
   * Default turtle has ID 0 in our back end tests (setUpTracker() method in CommandReaderTest
   */
  public BackEndTurtleTracker() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  /**
   * Gives map of turtle ID to the List<Command> it should run on that turtle.
   *
   * Single method implemented from the SafeBackEndTurtleTracker interface. Allows the front
   * end to receive this map without access to or knowledge of the underlying implementing
   * class.
   *
   * @return map of turtle ID to list of commands that should be run on the turtle.
   */
  public Map<Integer, List<Command>> getAllTurtleCommands() {
    Map<Integer, List<Command>> ret = new HashMap<>();
    for (Integer id : allTurtles.keySet()) {
      ret.put(id, allTurtles.get(id).getCommands());
    }
    return ret;
  }

  /**
   * Gives all of the turtle IDs currently in this tracker.
   *
   * Used in AskWith command to check which turtles satisfy the expression.
   *
   * @return set of turtle IDs for all turtles in this tracker.
   */
  public Set<Integer> getAllTurtles() {
    return allTurtles.keySet();
  }

  /**
   * Get the safe turtle tracker instance. Allows commands to affect all turtles, rather than just
   * the turtle that the command is called on.
   *
   * Used in certain display commands.
   *
   * @return reference to the safe front end turtle tracker
   */
  public SafeFrontEndTurtleTracker getSafe() {
    return safeTurtleTracker;
  }

  // used for tests that run multiple times; don't want previous test to affect next one

  /**
   * Deletes all data stored in this tracker.
   *
   * Used for tests that run multiple times; don't want previous test to affect next one that
   * are in the same test method.
   */
  public void deleteAllData() {
    allTurtles = new HashMap<>();
    activeTurtles = new ArrayList<>();
    askActiveTurtles = new Stack<>();
    tellActiveTurtles = new ArrayList<>();
    currTurtle = 0;
  }

  /**
   * Clears all lists of commands that each backend turtle keeps.
   *
   * Just in case future tracker given to us has previous commands, also helpful to ensure
   * previous tests don't affect new ones ran in the same test method.
   */
  public void clearAllCommands() {
    Iterator<Integer> itr = getIterator();
    while (itr.hasNext()) {
      getTurtle(itr.next()).clearCommands();
    }
  }

  /**
   * Adds this turtle instance to our tracker. If this turtleId already exists, we only update
   * activeTurtles, and not allTurtles.
   *
   * Makes sure that newly added turtles are at the end of the active turtles list, in order
   * for the tell and ask/askwith commands to return the ouptut of the command ran on the last
   * turtle.
   *
   * Assumes tracker ID field is accurate.
   *
   * @param turtle backend turtle to be added to our tracker
   */
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

  /**
   * Clears active turtles and replaces it with one turtle ID.
   *
   * Only used in AskWithNode, to temporarily set the active turtle list to a specific turtle, and
   * run the logic expression to see if that specific turtle satisfies the requirement in AskWith.
   *
   * @param id id of current turtle to check in askWith Node
   */
  public void checkOneTurtle(int id) {
    activeTurtles.clear();
    activeTurtles.add(id);
  }

  /**
   * Updates the active turtles list with the list of IDs that a tell command has issued.
   *
   * @param tellList list of IDs from a tell command
   */
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

  /**
   * Updates the active turtles list with the list of IDs that a ask or askwith command has issued.
   *
   * @param askList list of IDs from a ask or askwith command
   */
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

  /**
   * Reverts the activeTurtles list back to the previous active list. Generally used to
   * revert to previous tell list after ask/askwith is done, but also applies to nested ask/askwith
   * commands.
   */
  //
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

  /**
   * Returns an iterator of activeTurtles.
   *
   * Used for looping through active turtles for one node, will have many iterator instances out
   * for nested commands e.g. fd fd 50
   *
   * Does lead to potential concurrent modification exceptions when ask/askwith is nested in a
   * command that affects all turtles.
   *
   * @return iterator of activeTurtles list.
   */
  //
  public Iterator<Integer> getIterator() {
    // returns a new, different iterator each time
    return activeTurtles.iterator();
  }

  /**
   * Return the turtle with passed in id (from the activeTurtles iterator)
   *
   * Used by turtleCommandNode loop to access the turtle at this index of activeTurtles.
   *
   * @param id id of turtle to get
   * @return backEndTurtle instance to add a command to
   */
  public BackEndTurtle getTurtle(int id) {
    // not possible for a turtle to not be in allTurtles when using iterator
    return allTurtles.get(id);
  }

  /**
   * Set the current turtle to id.
   *
   * Represents current turtle globally.
   *
   * @param id turtle id to be set as current turtle.
   */
  public void setCurr(int id) {
    currTurtle = id;
  }

  /**
   * Returns current active turtle.
   *
   * Helpful for ID command that returns the ID of the current turtle.
   *
   * @return ID of current active turtle.
   */
  public int getCurr() {
    return currTurtle;
    // tell [ 1 2 3 ] fd ID
  }

  /**
   * Returns how many turtles are currently in the tracker.
   *
   * Helpful for the turtles command.
   *
   * @return number of turtles in the tracker.
   */
  public int turtles() {
    return allTurtles.keySet().size();
  }

  // add tellCommands to each turtle whenever tell, ask, and askWith are created
  private void addTellCommands() {
    for (Integer i : allTurtles.keySet()) {
      allTurtles.get(i).addCommand(new TellCommand(getSafe(), i, activeTurtles.contains(i)));
    }
  }

  // return either the turtle in the map or a new turtle with id
  private BackEndTurtle getBasicTurtle(int id) {
    return allTurtles.getOrDefault(id, new BackEndTurtle(0, 0, 0, true, true, id));
  }
}

//  tell [ 1 2 3 ]
//  set :x 10
//  fd * id :x

//  tell [ 1 2 ]
//  2 6, 3 12, 4 20
//  fd fd 20
//  order of things done: 1fd 21, 2fd 22, 1fd 22, fd
