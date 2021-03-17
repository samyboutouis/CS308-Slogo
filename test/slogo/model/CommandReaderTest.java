package slogo.model;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Command;
import slogo.BackEndTurtle;
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
  void testTurtleMovements () {
    assertEquals(List.of(5.0), myReader.testParseInput("fd 5.0"));
    assertEquals(List.of(100.0, 100.0), myReader.testParseInput("fd 100 home"));
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

  @Test
  void testQueries(){
    assertEquals(List.of(50.0,90.0,50.0,50.0), myReader.testParseInput("fd 50 rt 90 fd 50 xcor"));
    assertEquals(List.of(60.0, 60.0), myReader.testParseInput("fd 60 ycor"));
    assertEquals(List.of(50.0,90.0,60.0,90.0), myReader.testParseInput("fd 50 rt 90 fd 60 heading"));
    assertEquals(List.of(0.0,0.0), myReader.testParseInput("penup pendown?"));
    assertEquals(List.of(1.0,1.0), myReader.testParseInput("st showing?"));
  }

  @Test
  void testMath (){
    assertEquals(List.of(3.0), myReader.testParseInput("sum 1 2"));
    assertEquals(List.of(6.0), myReader.testParseInput("product 2 3"));
    assertEquals(List.of(1.0), myReader.testParseInput("difference 2 1"));
    assertEquals(List.of(1.5), myReader.testParseInput("quotient 3 2"));
    assertEquals(List.of(1.0), myReader.testParseInput("remainder 3 2"));
    assertEquals(List.of(-3.0), myReader.testParseInput("minus 3"));
    assertEquals(List.of(1.0), myReader.testParseInput("sin 90"));
    assertEquals(List.of(Math.cos(Math.toRadians(90))), myReader.testParseInput("cos 90"));
    assertEquals(List.of(Math.tan(Math.toRadians(68))), myReader.testParseInput("tan 68"));
    assertEquals(List.of(-90.0), myReader.testParseInput("minus 90"));
    assertEquals(List.of(8.0), myReader.testParseInput("pow 2 3"));
  }

  @Test
  void testUserDefined () {
    assertEquals(List.of(1.0, 50.0), myReader.testParseInput("to arc [ :a ]\n"
        + "[\n"
        + "  fd :a\n"
        + "]\n"
        + "arc 50"));
  }

  // SECTION
  // test that command objects created are correct
  @Test
  void testHomeCommand () {
    assertTrue(myReader.parseInput("home", new BackEndTurtle(0, 0, 0, true, true)).get(0) instanceof HomeCommand);
  }

  @Test
  void testLoopCommand () {
    List<Command> loop = myReader.parseInput("for [ :a 1 5 1 ] [ fd 1 bk 2 ]", new BackEndTurtle(0, 0, 0, true, true));
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
      myReader.testParseInput("::"); // invalid syntax
    } catch(IllegalArgumentException e){
      assertEquals(e.getMessage(), "Input syntax is incorrect");
    }
  }
}
