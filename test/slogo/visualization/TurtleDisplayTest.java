package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class TurtleDisplayTest extends DukeApplicationTest {
  private final static String RESOURCE_PACKAGE = "resources";

  private ImageView turtle;

  @Override
  public void start(Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    new ScreenManager(root, scene, stage);
    ResourceBundle idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", RESOURCE_PACKAGE, "stylesheets", "CSS_IDs"));
    turtle = lookup("#" + idBundle.getString("Turtle")).query();
  }

  @Test
  void testTurtleLoad() {
    assertTrue(turtle.isVisible());
  }
}
