package slogo.backend;
import slogo.Movement;

public interface Command{
  
  /*
  * each command implements their own getMovement method that returns a movement given their * command and value (if any)
  *
  * also possible to just pass in a movement object and get one back (initial movement      * object is from frontend)
  */
  public Movement getMovement(double turtleX, double turtleY, double turtleRotation);



}

