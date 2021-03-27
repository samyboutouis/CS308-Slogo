package slogo;

/**
 * Allow easy transfer from frontend turtle to backend turtle; restricts what methods
 * backend turtle can call to create itself.
 *
 * Also allows flexibility if front end decides another turtle class can also be used, as long
 * as it implements this interface, backend turtle can be created.
 *
 *      new BackEndTurtle(allTurtles.get(id), id);
 *
 * FrontEndTurtle type automatically cast to SafeTurtle interface since that is the type defined
 * in the back end turtle constructor.
 *
 * Interface of getters for back end turtle to copy the state of the front end turtle.
 *
 * @author Felix Jiang
 */
public interface SafeTurtle {

  /**
   * Get x position of front end turtle
   * @return x position of front end turtle
   */
  double getX();

  /**
   * Get y position of front end turtle
   * @return y position of front end turtle
   */
  double getY();

  /**
   * Get direction of front end turtle
   * @return direction of front end turtle
   */
  double getDirection();

  /**
   * Get pen down boolean of front end turtle
   * @return pen down boolean of front end turtle
   */
  boolean isPenDown();

  /**
   * Get isShowing boolean of front end turtle
   * @return isShowing boolean of front end turtle
   */
  boolean isShowing();

  /**
   * Get current pen color index of front end turtle.
   *
   * @return index of palette for front end turtle pen color.
   */
  int getPenColorIndex();

  /**
   * Get current shape index of front end turtle.
   *
   * @return index of palette for front end turtle shape.
   */
  int getShapeIndex();
}
