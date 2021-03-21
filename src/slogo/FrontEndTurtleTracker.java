package slogo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import slogo.model.BackEndTurtleTracker;
import slogo.visualization.BackgroundObserver;
import slogo.visualization.FrontEndTurtle;
import slogo.visualization.PaletteDisplay;
import slogo.visualization.TurtleObserver;

public class FrontEndTurtleTracker implements SafeFrontEndTurtleTracker {

  private final Map<Integer, FrontEndTurtle> allTurtles;
  private final List<Integer> activeTurtles;
  private final List<TurtleObserver> turtleObservers;
  private final List<BackgroundObserver> backgroundObservers;
  private final PaletteDisplay paletteDisplay;

  public FrontEndTurtleTracker(PaletteDisplay paletteDisplay) {
    allTurtles = new TreeMap<>();
    activeTurtles = new ArrayList<>();
    turtleObservers = new ArrayList<>();
    backgroundObservers = new ArrayList<>();
    this.paletteDisplay = paletteDisplay;
  }

  public void addObserver(TurtleObserver turtleObserver) {
    turtleObservers.add(turtleObserver);
  }

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

  private void notifyUpdateTurtleState(int id) {
    for (TurtleObserver turtleObserver : turtleObservers) {
      turtleObserver.updateTurtleState(id);
    }
  }

  public void notifyBackgroundObservers(Color color) {
    for (BackgroundObserver backgroundObserver : backgroundObservers) {
      backgroundObserver.setBackgroundColor(color);
    }
  }

  public void setBackgroundColor(int index) {
    Color color = paletteDisplay.getColorFromIndex(index);
    notifyBackgroundObservers(color);
  }

  public BackEndTurtleTracker passToBackEnd() {
    Map<Integer, BackEndTurtle> backEndAllTurtles = new HashMap<>();
    for (int i = 1; i <= allTurtles.size(); i++) {
      backEndAllTurtles.put(i, new BackEndTurtle(allTurtles.get(i)));
    }
    return new BackEndTurtleTracker(backEndAllTurtles, new ArrayList<>(activeTurtles), this);
  }

  @Override
  public void setActive(int id) {
    if (!activeTurtles.contains(id)) {
      activeTurtles.add(id);
    }
    allTurtles.get(id).setActive();
  }

  public void setActive(FrontEndTurtle frontEndTurtle) {
    Set<Integer> turtleIDSet = getKeyByValue(allTurtles, frontEndTurtle);
    int turtleID = turtleIDSet.iterator().next();
    if (!activeTurtles.contains(turtleID)) {
      activeTurtles.add(turtleID);
    }
  }

  @Override
  public void setInactive(int id) {
    if (activeTurtles.contains(id)) {
      activeTurtles.remove(Integer.valueOf(id));
    }
    allTurtles.get(id).setInactive();
  }

  public void setInactive(FrontEndTurtle frontEndTurtle) {
    Set<Integer> turtleIDSet = getKeyByValue(allTurtles, frontEndTurtle);
    int turtleID = turtleIDSet.iterator().next();
    if (activeTurtles.contains(turtleID)) {
      activeTurtles.remove(Integer.valueOf(turtleID));
    }
  }

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

  public void updatePalette(int index, int r, int g, int b) {
    paletteDisplay.updatePaletteBox(index, r, g, b);
  }

  public void forward(double pixels) {
    for (int id : activeTurtles) {
      allTurtles.get(id).forward(pixels);
      notifyUpdateTurtleState(id);
    }
  }

  public void back(double pixels) {
    for (int id : activeTurtles) {
      allTurtles.get(id).back(pixels);
      notifyUpdateTurtleState(id);
    }
  }

  public void rotate(double pixels) {
    for (int id : activeTurtles) {
      allTurtles.get(id).rotate(pixels);
      notifyUpdateTurtleState(id);
    }
  }

  public <T, E> Set<T> getKeyByValue(Map<T, E> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> Objects.equals(entry.getValue(), value))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

  public SafeTurtle getSafeTurtle(int id) {
    return allTurtles.get(id);
  }

  private List<Integer> getTurtleIDs() {
    return allTurtles.keySet()
        .stream()
        .collect(Collectors.toUnmodifiableList());
  }

  public FrontEndTurtle getFrontEndTurtle(int id) {
    return allTurtles.get(id);
  }

  public boolean turtleExists(int id) {
    return allTurtles.containsKey(id);
  }

  public boolean isEmpty() {
    return allTurtles.isEmpty();
  }

  public Color getColorIndex(int index) {
    return paletteDisplay.getColorFromIndex(index);
  }
}
