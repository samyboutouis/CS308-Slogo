package slogo.visualization;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ToolbarDisplayTest extends DukeApplicationTest {
  public static final String LANGUAGE = "English";
  private final static String RESOURCE_PACKAGE = "resources";

  private Button myPenColorButton;
  private Button myBackgroundColorButton;
  private Button myTurtleImageButton;
  private ComboBox<String> myLanguageDropdown;
  private ComboBox<String> myHelpDropdown;

  @Override
  public void start (Stage stage) {
    Pane root = new Pane();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    new ScreenManager(root, scene, stage);
    ResourceBundle idBundle = ResourceBundle
      .getBundle(String.format("%s/%s/%s", RESOURCE_PACKAGE, "stylesheets", "CSS_IDs"));
    myPenColorButton = lookup("#" + idBundle.getString("PenColorButton")).query();
    myBackgroundColorButton = lookup("#" + idBundle.getString("BackgroundColorButton")).query();
    myTurtleImageButton = lookup("#" + idBundle.getString("TurtleImageButton")).query();
    myLanguageDropdown = lookup("#" + idBundle.getString("LanguageDropdown")).query();
    myHelpDropdown = lookup("#" + idBundle.getString("HelpDropdown")).query();
  }

  @Test
  void testChangePenColor() {
    assertTrue(myPenColorButton.isVisible());
    clickOn(myPenColorButton);
    assertTrue(myPenColorButton.isDisabled());
    assertFalse(myPenColorButton.isVisible());
  }

  @Test
  void testChangeBackgroundColor() {
    assertTrue(myBackgroundColorButton.isVisible());
    clickOn(myBackgroundColorButton);
    assertTrue(myBackgroundColorButton.isDisabled());
    assertFalse(myBackgroundColorButton.isVisible());
  }

  @Test
  void testChangeTurtleImage() {
    assertTrue(myTurtleImageButton.isVisible());
    clickOn(myTurtleImageButton);
  }

  @Test
  void testChangeLanguage() {
    assertTrue(myLanguageDropdown.isVisible());
    assertEquals(myLanguageDropdown.getValue(), LANGUAGE);
    select(myLanguageDropdown, "Français");
    assertEquals(myLanguageDropdown.getValue(), "Français");
  }

  @Test
  void testSelectHelp() {
    assertTrue(myHelpDropdown.isVisible());
    select(myLanguageDropdown, "atan");
  }
}
