package slogo;

import java.util.List;
import java.util.Map;

/**
 * What backend passes to front end, limiting front end from other backend turtle tracker methods.
 *
 *    controller.parseProgram(command, backEndTurtleTracker).getAllTurtleCommands()
 *
 * parseProgram() returns a SafeBackEndTurtleTracker, and then it calls the single method of this
 * interface.
 *
 * @author Felix Jiang
 */
public interface SafeBackEndTurtleTracker {

  /**
   * For front end to receive map of turtle ID and the list of commands it should run on that
   * turtle.
   *
   * @return map of turtle ID to list of commands.
   */
  Map<Integer, List<Command>> getAllTurtleCommands();
}
