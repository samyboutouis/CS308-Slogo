package slogo;

import java.util.List;
import java.util.Map;

// what backend passes to front end, limiting front end from other backend turtle tracker methods
public interface SafeBackEndTurtleTracker {

  Map<Integer, List<Command>> getAllTurtleCommands(); // for front end to get map
}
