package slogo.backend;
import slogo.backend.Command;
import java.util.IllegalFormatException;

public interface CommandParser {
  public Command convertCommand(String singleCommand) throws IllegalFormatException;
  // takes a string (part of the entire string the user typed) and converts it into a command object

  public Movement calculateMove(Command command); 
  // takes a command object and calculates the move from that command
}
