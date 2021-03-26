package slogo;

import slogo.visualization.turtle.FrontEndTurtle;

/**
 * Represents an action to do on a turtle in the SLogo program. Classes that implement this interface
 * will be able to pass in a turtle to this method and see the action performed on the turtle. 
 * 
 * Defining this interface allows the portion of the project responsible for displaying changes to the
 * turtle to operate with the type Command rather than a `SetPositionCommand` so that the 
 * implementation details are hidden. 
 *
 * Interface assumes the frontEndTurtle passed in is the correct turtle to do the action on. Note that
 * some commands affect the entire program, not just a single turtle, so the passed in turtle does not
 * directly matter. Those Command objects will do the action when the doCommand method is called, but
 * do not use the turtle. In that case, the purpose of this interface is to provide an action that
 * should be completed at the appropriate time the doCommand method is called. 
 *
 * The interface does not depend on any other classes or packages, as it only defines a method. However
 * the frontEnd will call this method and it takes in a frontEndTurtle. 
 *
 *     FrontEndTurtle turtle ...
 *     List<Command> commands ...
 *     for(Command action : commands) {
 *        action.doCommand(turtle);  
 *     }
 * 
 * Loop through the list of commands created from parsing a string input, and pass the appropriate (in
 * cases where multiple turtles are involved) turtle to the doCommand method. 
 *
 * These Command objects are created after the SlogoNode tree has been built and all the exact values
 * for each command have been calculated (from the children nodes). 
 *
 * @author Felix Jiang
 * @author Andre Wang
 */
public interface Command {

  /**
   * Perform an action that affects the passed in frontEndTurtle or the general UI that can be changed
   * from commands. 
   *
   * Classes that implement this interface must provide an implementation for this method, or have a
   * subclass that provides the implementation. 
   *
   * Providing a Command object that calls other methods on the turtle allows the method to be ran
   * at the time the front end wants, rather than whenever the back end finishes parsing.
   *
   * Allows the front end to step through turtle commands if a UI was provided for the user. 
   *
   * @param frontEndTurtle the turtle that will do the command implemented in the Command object.
   */
  void doCommand(FrontEndTurtle frontEndTurtle);
}
