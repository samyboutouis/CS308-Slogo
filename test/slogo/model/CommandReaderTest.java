package slogo.model;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Command;
import slogo.turtlecommands.HomeCommand;
import slogo.turtlecommands.MovementCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CommandReaderTest {
  private CommandReader myReader;

  @BeforeEach
  void setUp() {
    myReader = new CommandReader("English");
  }

  // SECTION
  // test that values are correct based on Logo code
  @Test
  void testConditional () {
    assertEquals(List.of(0.0), myReader.testParseInput("if 0 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
    assertEquals(List.of(100.0), myReader.testParseInput("if 10 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
    assertEquals(List.of(100.0), myReader.testParseInput("if 10 [ if fd 10 [ fd 20 ] sum 50 50 ]"));
    assertEquals(List.of(100.0), myReader.testParseInput("ifelse fd 50 [ if fd 0 [ fd 20 ] sum 50 50 ] [ fd 20 ]"));
    assertEquals(List.of(20.0), myReader.testParseInput("ifelse 0 [ if fd 0 [ fd 20 ] sum 50 50 ] [ fd 20 ]"));
  }

  @Test
  void testSum () {
    assertEquals(List.of(150.0), myReader.testParseInput("sum 50 sum 50 50"));
  }

  @Test
  void testForward () {
    assertEquals(List.of(5.0), myReader.testParseInput("fd 5.0"));
  }

  @Test
  void testLoops () {
    assertEquals(List.of(5.0), myReader.testParseInput("for [ :a 1 5 1 ] [ fd :a ]"));
    assertEquals(List.of(50.0), myReader.testParseInput("repeat fd 50 [ fd :repcount ]"));
    assertEquals(List.of(45.0), myReader.testParseInput("dotimes [ :a sum 20 25 ] [ fd :a ]"));
  }

  @Test
  void testBooleans () {
    assertEquals(List.of(1.0), myReader.testParseInput("less? 30 50"));
    assertEquals(List.of(0.0), myReader.testParseInput("less? 50 30"));
    assertEquals(List.of(0.0), myReader.testParseInput("greater? 30 50"));
    assertEquals(List.of(1.0), myReader.testParseInput("greater? 50 30"));
    assertEquals(List.of(1.0), myReader.testParseInput("equal? 50 50"));
    assertEquals(List.of(0.0), myReader.testParseInput("equal? 50 30"));
    assertEquals(List.of(0.0), myReader.testParseInput("notequal? 50 50"));
    assertEquals(List.of(1.0), myReader.testParseInput("notequal? 50 30"));

    assertEquals(List.of(0.0), myReader.testParseInput("and 0 50"));
    assertEquals(List.of(1.0), myReader.testParseInput("and 20 30"));
    assertEquals(List.of(1.0), myReader.testParseInput("or 20 0"));
    assertEquals(List.of(0.0), myReader.testParseInput("or 0 0"));
    assertEquals(List.of(0.0), myReader.testParseInput("not 24"));
    assertEquals(List.of(1.0), myReader.testParseInput("not 0"));
  }

  // SECTION
  // test that command objects created are correct
  @Test
  void testHomeCommand () {
    assertTrue(myReader.parseInput("home").get(0) instanceof HomeCommand);
  }

  @Test
  void testLoopCommand () {
    List<Command> loop = myReader.parseInput("for [ :a 1 5 1 ] [ fd 1 bk 2 ]");
    assertEquals(10, loop.size());
    for(Command c : loop){
      assertTrue(c instanceof MovementCommand);
    }
  }

  // SECTION
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
