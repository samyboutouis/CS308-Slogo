package slogo;

/**
 * Interface for backend to have access to in order for it to pass this type to each Command object
 * for the command to run.
 *
 * Defines the methods that backend can call on the front end turtle tracker.
 *
 *      new SetBackgroundCommand(tracker.getSafe(), values.get(0).intValue())
 *
 * Pass in this type with tracker.getSafe(), which can be used by the setbg command once it is ran.
 *
 * @author Felix Jiang
 * @author Samy Boutouis
 * @author Donghan Park
 */
public interface SafeFrontEndTurtleTracker {

  /**
   * Set turtle active with this id.
   *
   * @param id id of turtle to be set active.
   */
  void setActive(int id);

  /**
   * Set turtle inactive with this id.
   *
   * @param id id of turtle to be set inactive.
   */
  void setInactive(int id);

  /**
   * Set background color to the index of the palette.
   *
   * @param index index of palette to set the background color to.
   */
  void setBackgroundColor(int index);

  /**
   * Add or update an entry on the palette.
   *
   * @param index index to add or update the palette
   * @param r amount of red for this new palette entry.
   * @param g amount of green for this new palette entry.
   * @param b amount of blue for this new palette entry.
   */
  void updatePalette(int index, int r, int g, int b);
}
