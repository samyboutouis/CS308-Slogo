package slogo.visualization.observers;

import java.util.List;

/**
 * An observer interface that observes changes that occur due to changes in turtle states. In the
 * classes that implement this interface, they will update their states when certain actions or
 * events occur from changing the state of the turtles whether new turtles are added or certain
 * turtles change state.
 *
 * @author Samy Boutouis
 */
public interface TurtleObserver {

  /**
   * Notifies observers that a list of new IDs representing the number of turtles has been updated,
   * classes that implement this update themselves accordingly.
   *
   * @param list List representing all the IDs of turtles on the screen
   */
  void updateTurtleNumber(List<Integer> list);

  /**
   * Notifies observers that a turtle's state has been updated. Classes that implement this update
   * themselves to reflect changes in a turtle's state.
   *
   * @param id Integer corresponding to an ID of a turtle
   */
  void updateTurtleState(int id);
}
