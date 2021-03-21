package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Command;
import slogo.BackEndTurtle;
import slogo.turtlecommands.HomeCommand;
import slogo.turtlecommands.MovementCommand;
import slogo.turtlecommands.TellCommand;

import static org.junit.jupiter.api.Assertions.*;

public class CommandReaderTest {
  private CommandReader myReader;
  private BackEndTurtleTracker tracker;

  @BeforeEach
  void setUp() {
    myReader = new CommandReader("English");
  }

  // SECTION
  // test that values are correct based on Logo code



  @Test
  void testAskWith(){
    assertEquals(List.of(10.0, 100.0, 50.0, 100.0, 200.0),myReader.testParseInput("tell [ 1 2 7 10 ] fd 100 ask [ 2 7 ] [ bk 50 ] askwith [ greater? ycor 50 ] [ fd 100 ] ycor"));
  }


  @Test
  void testConditional () {
    assertEquals(List.of(0.0), myReader.testParseInput("if 0 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
    assertEquals(List.of(100.0), myReader.testParseInput("if 10 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
    assertEquals(List.of(100.0), myReader.testParseInput("if 10 [ if fd 10 [ fd 20 ] sum 50 50 ]"));
    assertEquals(List.of(100.0), myReader.testParseInput("ifelse fd 50 [ if fd 0 [ fd 20 ] sum 50 50 ] [ fd 20 ]"));
    assertEquals(List.of(20.0), myReader.testParseInput("ifelse 0 [ if fd 0 [ fd 20 ] sum 50 50 ] [ fd 20 ]"));
  }

  @Test
  void testMultiTurtle(){
    assertEquals(List.of(5.0), myReader.testParseInput("tell [ 2 5 ]")); // note testParseInput adds a index 0 turtle by default
    assertEquals(List.of(5.0, 90.0, 50.0, 50.0), myReader.testParseInput(" tell [ 0 2 5 ] rt 90 fd 50 xcor"));
    assertEquals(List.of(3.0, 30.0, 1.0, 10.0, 2.0, 20.0, 3.0, 30.0), myReader.testParseInput("tell [ 1 2 3 ] fd product ID 10 tell [ 1 ] ycor tell [ 2 ] ycor tell [ 3 ] ycor"));

    // testing multi turtle different movement and home and cs commands
    assertEquals(List.of(3.0, 30.0, 1.0, 10.0, 2.0, 20.0, 3.0, 30.0), myReader.testParseInput("tell [ 1 2 3 ] fd product ID 10 tell [ 1 ] home tell [ 2 ] cs tell [ 3 ] home"));

    // testing ask command
    assertEquals(List.of(3.0, 50.0, 50.0, 50.0), myReader.testParseInput("tell [ 1 2 3 ] ask [ 1 4 ] [ fd 50 ycor ] fd 50 ycor "));
  }

  @Test
  void testSum () {
    assertEquals(List.of(150.0), myReader.testParseInput("sum 50 sum 50 50"));
  }

  @Test
  void testVariables () {
    assertEquals(List.of(10.0, 10.0), myReader.testParseInput("set :x 10 fd :x"));
    assertEquals(List.of(10.0, 20.0, 20.0), myReader.testParseInput("set :x 10 set :x sum :x :x fd :x"));
  }

  @Test
  void testTurtleMovements () {
    assertEquals(List.of(5.0), myReader.testParseInput("fd 5.0"));
    assertEquals(List.of(100.0, 100.0), myReader.testParseInput("fd 100 home"));
    assertEquals(List.of(100.0, 100.0), myReader.testParseInput("fd 100 cs"));
    assertEquals(List.of(50.0), myReader.testParseInput("setxy 30 40"));
    assertEquals(List.of(90.0), myReader.testParseInput("towards 1 0"));
    assertEquals(List.of(45.0, 45.0), myReader.testParseInput("rt 45 setheading 90"));
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
    assertEquals(List.of(1.0,1.0), myReader.testParseInput("pendown pendown?"));
    assertEquals(List.of(1.0,1.0), myReader.testParseInput("st showing?"));
    assertEquals(List.of(0.0,0.0), myReader.testParseInput("ht showing?"));
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

  @Test
  void testRecursion () {
    assertEquals(List.of(1.0, 50.0, 100.0), myReader.testParseInput("to example [ :x ]\n"
        + "[\n"
        + "  if greater? :x 10\n"
        + "  [\n"
        + "    example difference :x 10\n"
        + "    fd 50\n"
        + "  ]\n"
        + "]\n"
        + "\n"
        + "example 30 ycor\n"));
    assertEquals(List.of(0.0), myReader.testParseInput("to felix [ sum 50 50 ] [ fd 50 ]"));// unable to define method, variables are wrong


    // the last test is to test that the getUserDefinedCommandsinString returns the map correctly
    String ret = "";
    for (String key: myReader.getUserDefinedCommandsInString().keySet()){
      ret += key+": "+myReader.getUserDefinedCommandsInString().get(key)+"\n";
    }
    assertEquals("felix:  [ sum 50 50 ] [ fd 50 ]\n"
        + "example:  [ :x ] [ if greater? :x 10 [ example difference :x 10 fd 50 ] ]\n", ret);
  }

  // SECTION
  // test that command objects created are correct

  void setUpTracker() {
    tracker = new BackEndTurtleTracker();
    tracker.addTurtle(new BackEndTurtle(0, 0, 0, true, true,0));
  }

  @Test
  void testHomeCommand () {
    setUpTracker();
    assertTrue(myReader.parseInput("home", tracker).getAllCommands().get(0) instanceof HomeCommand);
  }

  @Test
  void testLoopCommand () {
    setUpTracker();
    List<Command> loop = myReader.parseInput("for [ :a 1 5 1 ] [ fd 1 bk 2 ]", tracker).getAllCommands();
    assertEquals(10, loop.size());
    for(Command c : loop){
      assertTrue(c instanceof MovementCommand);
    }
  }

  @Test
  void testMultiCommands () {
    setUpTracker();
//    List<Class<TellCommand>> turtle1 = List.of(TellCommand.class, TellCommand.class, TellCommand.class);
//    // for turtle1, first tell is in tell, then in ask, then once ask pops it comes up again
//    List<Class<TellCommand>> turtle2 = List.of(TellCommand.class, TellCommand.class, TellCommand.class);
//    List<Class<TellCommand>> turtle3 = List.of(TellCommand.class, TellCommand.class, TellCommand.class);
//    List<Class<TellCommand>> turtle4 = List.of(TellCommand.class, TellCommand.class);
//    List<List<Class<TellCommand>>> forTest = List.of(turtle1, turtle2, turtle3, turtle4);
    // can't test for instanceof by having a list of classes
    List<Integer> correctNumberOfCommands = List.of(3, 3, 3, 3, 2);
    Map<Integer, List<Command>> output = myReader.parseInput("tell [ 1 2 3 ] ask [ 1 4 ] [ ]", tracker).getAllTurtleCommands();
    for(int i = 0; i < output.size(); i++){
      assertTrue(output.get(i).size() == correctNumberOfCommands.get(i));
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
