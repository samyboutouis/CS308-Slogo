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
 * @author Felix Jiang
 * @author Andre Wang
 */
public interface Command {

  void doCommand(FrontEndTurtle frontEndTurtle);
}
