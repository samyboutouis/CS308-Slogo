package slogo.visualization.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import slogo.model.BackEndTurtle;
import slogo.SafeFrontEndTurtleTracker;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.observers.BackgroundObserver;
import slogo.visualization.observers.TurtleObserver;
import slogo.visualization.displays.PaletteDisplay;

/**
 * This class is responsible for keeping track of all the turtles on the front-end currently displayed
 * on the screen. It is dependent on the PaletteDisplay for colors and images with their corresponding
 * IDs from the palette that the user specifies. This is one of the classes used as an external API
 * to be called in the back-end to produce certain changes on the front-end. This class also
 * incorporates multiple observer interfaces to notify other classes when certain aspects change
 * about the turtles or workspace.
 *
 * @author Samy Boutouis
 * @author Donghan Park
 *
 */
public class FrontEndTurtleTracker implements SafeFrontEndTurtleTracker {

  private final Map<Integer, FrontEndTurtle> allTurtles;
  private final List<Integer> activeTurtles;
  private final List<TurtleObserver> turtleObservers;
  private final List<BackgroundObserver> backgroundObservers;
  private final PaletteDisplay paletteDisplay;

  /**
   * Constructor for class.
   *
   * @param paletteDisplay PaletteDisplay class that contains information about the palette the user
   *                       sets up.
   */
  public FrontEndTurtleTracker(PaletteDisplay paletteDisplay) {
    allTurtles = new TreeMap<>();
    activeTurtles = new ArrayList<>();
    turtleObservers = new ArrayList<>();
    backgroundObservers = new ArrayList<>();
    this.paletteDisplay = paletteDisplay;
  }

  /**
   * Adds a class that implements the TurtleObserver interface. A class that implements this is
   * interested in being updated when the states of turtles change.
   *
   * @param turtleObserver Interface when implemented updates itself when aspects of turtle states
   *                       is changed.
   */
  public void addObserver(TurtleObserver turtleObserver) {
    turtleObservers.add(turtleObserver);
  }

  /**
   * Adds a class that implements the BackgroundObserver interface. A class that implements this is
   * interested in being updated when the state of the background of the workspace changes.
   *
   * @param backgroundObserver Interface when implemented updates itself when aspects of the
   *                           background is changed.
   */
  public void addObserver(BackgroundObserver backgroundObserver) {
    backgroundObservers.add(backgroundObserver);
  }

  private void notifyAddTurtle(List<Integer> list, FrontEndTurtle turtle) {
    for (TurtleObserver turtleObserver : turtleObservers) {
      turtleObserver.updateTurtleNumber(list);
    }
    for (BackgroundObserver backgroundObserver : backgroundObservers) {
      backgroundObserver.addToBackground(turtle);
    }
  }

  /**
   * Notifies all turtle observers that a specific turtle has been updated with a specific id and
   * handles them accordingly.
   *
   * @param id Integer representing the ID of the turtle whose state has changed
   */
  public void notifyUpdateTurtleState(int id) {
    for (TurtleObserver turtleObserver : turtleObservers) {
      turtleObserver.updateTurtleState(id);
    }
  }

  /**
   * Notifies all background color observers that the background color has changed.
   *
   * @param color Color object representing the new color the background is being set to.
   */
  public void notifyBackgroundObservers(Color color) {
    for (BackgroundObserver backgroundObserver : backgroundObservers) {
      backgroundObserver.setBackgroundColor(color);
    }
  }

  /**
   * Sets the background color of the turtle display to a certain color based on an index from the
   * palette.
   *
   * @param index Integer representing an index in the palette that corresponds to a color
   */
  public void setBackgroundColor(int index) {
    Color color = paletteDisplay.getColorFromIndex(index);
    notifyBackgroundObservers(color);
  }

  /**
   * Creates a BackEndTurtleTracker from the FrontEndTurtleTracker and its information that the
   * back-end can use while parsing and executing commands.
   *
   * @return BackEndTurtleTracker object that manages the current turtle states in the back-end
   */
  public BackEndTurtleTracker passToBackEnd() {
    Map<Integer, BackEndTurtle> backEndAllTurtles = new HashMap<>();
    for (Integer id : allTurtles.keySet()) {
      backEndAllTurtles.put(id, new BackEndTurtle(allTurtles.get(id), id));
    }
    return new BackEndTurtleTracker(backEndAllTurtles, new ArrayList<>(activeTurtles), this);
  }

  /**
   * Sets a turtle with a specific ID to active status.
   *
   * @param id ID corresponding to a turtle in the map of all turtles.
   */
  @Override
  public void setActive(int id) {
    if (!activeTurtles.contains(id)) {
      activeTurtles.add(id);
    }
    allTurtles.get(id).setActive();
  }

  /**
   * Adds a turtle to the active list if it is not already there.
   *
   * @param frontEndTurtle Turtle object that is being set active by the user.
   */
  public void setActive(FrontEndTurtle frontEndTurtle) {
    Set<Integer> turtleIDSet = getKeyByValue(allTurtles, frontEndTurtle);
    int turtleID = turtleIDSet.iterator().next();
    if (!activeTurtles.contains(turtleID)) {
      activeTurtles.add(turtleID);
    }
  }

  /**
   * Sets a turtle with a specific ID to inactive status.
   *
   * @param id ID corresponding to a turtle in the map of all turtles.
   */
  @Override
  public void setInactive(int id) {
    if (activeTurtles.contains(id)) {
      activeTurtles.remove(Integer.valueOf(id));
    }
    allTurtles.get(id).setInactive();
  }

  /**
   * Remove a turtle from the active list if it is set inactive.
   *
   * @param frontEndTurtle Turtle object that is being set inactive by the user.
   */
  public void setInactive(FrontEndTurtle frontEndTurtle) {
    Set<Integer> turtleIDSet = getKeyByValue(allTurtles, frontEndTurtle);
    int turtleID = turtleIDSet.iterator().next();
    if (activeTurtles.contains(turtleID)) {
      activeTurtles.remove(Integer.valueOf(turtleID));
    }
  }

  /**
   * Adds a turtle to the turtle tracker list when created on the front-end and assigns it an ID. Method
   * would fail if passed in a null or invalid turtle.
   *
   * @param turtle Turtle object that is being created by the user on the front-end.
   */
  public void addTurtle(FrontEndTurtle turtle) {
    int turtleID = 1;
    Set<Integer> allIDs = allTurtles.keySet();
    for (int id : allIDs) {
      if (turtleID == id) {
        turtleID++;
      } else {
        break;
      }
    }
    allTurtles.put(turtleID, turtle);
    if (turtle.isActive()) {
      activeTurtles.add(turtleID);
    }
    notifyAddTurtle(getTurtleIDs(), turtle);
  }

  /**
   * Updates or adds a new color to the palette with an associated index and its corresponding r, g
   * and b values.
   * 
   * @param index Integer representing an index in the palette that corresponds to a color
   * @param r Value corresponding to the amount of red in the color
   * @param g Value corresponding to the amount of green in the color
   * @param b Value corresponding to the amount of blue in the color
   */
  public void updatePalette(int index, int r, int g, int b) {
    paletteDisplay.updatePaletteBox(index, r, g, b);
  }

  /**
   * Moves all active turtles forward.
   *
   * @param pixels amount representing distance the turtle moves
   */
  public void forward(double pixels) {
    for (int id : activeTurtles) {
      allTurtles.get(id).forward(pixels);
      notifyUpdateTurtleState(id);
    }
  }

  /**
   * Moves all active turtles backward.
   *
   * @param pixels amount representing distance the turtle moves
   */
  public void back(double pixels) {
    for (int id : activeTurtles) {
      allTurtles.get(id).back(pixels);
      notifyUpdateTurtleState(id);
    }
  }

  /**
   * Rotates all active turtles by a certain amount.
   *
   * @param pixels amount representing the angle the turtle rotates
   */
  public void rotate(double pixels) {
    for (int id : activeTurtles) {
      allTurtles.get(id).rotate(pixels);
      notifyUpdateTurtleState(id);
    }
  }

  /**
   * Returns a set of possible keys given a value in the map.
   *
   * @param map Map object that contains the desired keys and values.
   * @param value Value that is being used to look for potential keys.
   * @param <T> Interface that implements the different types of keys.
   * @param <E> Interface that implements the different types of values.
   * @return Set that contains all the keys that correspond to a certain value
   */
  public <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

  /**
   * Gets a turtle object that corresponds to a certain ID in the allTurtles map. Assumes that one
   * provides a correct ID to access the turtle.
   *
   * @param id Integer corresponding to the ID of a turtle in the map.
   * @return Turtle object corresponding to a certain ID in the map.
   */
  public Turtle getTurtle(int id) {
    return allTurtles.get(id);
  }

  /**
   * Gets a list of all the turtles currently in the map of turtles being tracked on the front-end.
   * Assumes that all turtles on the screen are in the turtle tracker.
   *
   * @return Unmodifiable list containing all the IDs of turtles on the screen.
   */
  public List<Integer> getTurtleIDs() {
    return allTurtles.keySet()
        .stream()
        .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Gets a front-end turtle object that corresponds to a certain ID in the allTurtles map. Assumes
   * that one provides a correct ID to access the turtle.
   *
   * @param id Integer corresponding to the ID of a turtle in the map.
   * @return Front-end turtle object corresponding to a certain ID in the map.
   */
  public FrontEndTurtle getFrontEndTurtle(int id) {
    return allTurtles.get(id);
  }

  /**
   * Returns the status of whether a turtle at a certain id exists or not in the map.
   *
   * @param id Integer corresponding to the ID of a turtle in the map.
   * @return boolean determining whether a turtle of a certain ID exists (true if it exists)
   */
  public boolean turtleExists(int id) {
    return allTurtles.containsKey(id);
  }

  /**
   * Determines whether there are currently any turtles on the screen in teh front-end.
   *
   * @return Boolean describing whether the list of turtles is empty.
   */
  public boolean isEmpty() {
    return allTurtles.isEmpty();
  }

  /**
   * Gets the color associated with a certain index in the palette.
   *
   * @param index Integer representing the index in the palette of a certain color.
   * @return Color object representing the color at a certain index in the palette.
   */
  public Color getColorFromIndex(int index) {
    return paletteDisplay.getColorFromIndex(index);
  }

  /**
   * Gets the current color of the background in the turtle display.
   *
   * @return Color object representing the current color of the background
   */
  public Color getBackgroundColor() {
    return backgroundObservers.get(0).getBackgroundColor();
  }

  /**
   * Gets the shape associated with a certain index in the palette.
   *
   * @param index Integer representing the index in the palette of a certain shape.
   * @return String representing the file path of the shape/image
   */
  public String getShapeFromIndex(int index) {
    return paletteDisplay.getImagePathFromIndex(index);
  }
}