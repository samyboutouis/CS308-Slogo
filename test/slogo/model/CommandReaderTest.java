package slogo.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.turtlecommands.HomeCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CommandReaderTest {
  private CommandReader myReader;
  private List<Double> correct;

  @BeforeEach
  void setUp() {
    myReader = new CommandReader("English");
    correct = new ArrayList<>();
  }

  // test that values are correct based on Logo code
  @Test
  void testIf () {
    correct.add(0.0);
    assertEquals(correct, myReader.testParseInput("if 0 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
  }

  @Test
  void testSum () {
    correct.add(150.0);
    assertEquals(correct, myReader.testParseInput("sum 50 sum 50 50"));
  }

  // test that command objects created are correct
  @Test
  void testHomeCommand () {
    assertTrue(myReader.parseInput("home").get(0) instanceof HomeCommand);
  }

  // test exceptions/errors are handled correctly
  @Test
  void testBadInput () {
    try{
      myReader.parseInput("::"); // invalid syntax
    } catch(IllegalArgumentException e){
      assertEquals(e.getMessage(), "Input syntax is incorrect");
    }
  }
}
