package slogo.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.turtlecommands.HomeCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CommandReaderTest {
  private CommandReader myReader;

  @BeforeEach
  void setUp() {
    myReader = new CommandReader("English");
  }

  // test that values are correct based on Logo code
  @Test
  void testIf () {
    List<Double> correct = new ArrayList<>();
    correct.add(0.0);
    assertEquals(correct, myReader.testParseInput("if 0 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
  }

  // test that command objects created are correct
  @Test
  void testHomeCommand () {
    assertTrue(myReader.parseInput("home").get(0) instanceof HomeCommand);
  }
}
