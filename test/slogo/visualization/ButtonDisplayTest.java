package slogo.visualization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import slogo.Main;
import util.DukeApplicationTest;

class ButtonDisplayTest extends DukeApplicationTest {
  public static final String LANGUAGE = "English";
  private final static String RESOURCE_PACKAGE = "resources";

  private ResourceBundle idBundle;
  private Button myRightButton;

  @Override
  public void start (Stage stage) {
    Main main = new Main();
    main.start(stage);
    idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", RESOURCE_PACKAGE, "stylesheets", "CSS_IDs"));
    myRightButton = lookup("#" + idBundle.getString("RightButton")).query();
    addTurtle();
  }

//  @Test
//  void testRight() {
//    clickOn(myRightButton);
//  }

  private void addTurtle() {
    Button addTurtleButton = lookup("#" + idBundle.getString("AddTurtleButton")).query();
    clickOn(addTurtleButton);
  }
}
