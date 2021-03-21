package slogo;

import java.util.List;
import java.util.Map;

public interface SafeBackEndTurtleTracker {

  Map<Integer, List<Command>> getAllTurtleCommands();
}
