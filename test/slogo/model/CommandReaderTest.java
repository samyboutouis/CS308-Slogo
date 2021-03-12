package slogo.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandReaderTest {
  private CommandReader myReader;

  @BeforeEach
  void setUp() {
    myReader = new CommandReader("English");
  }

  @Test
  void testIf () {
    List<Double> correct = new ArrayList<>();
    correct.add(0.0);
    assertEquals(correct, myReader.testParseInput("if 0 [ if fd 0 [ fd 20 ] sum 50 50 ]"));
  }
}
