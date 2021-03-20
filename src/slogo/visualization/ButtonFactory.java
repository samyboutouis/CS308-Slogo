package slogo.visualization;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import slogo.controller.FrontEndController;

public class ButtonFactory {
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String IMAGE_PROPERTY = "reflection/Image";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String METHODS_PROPERTY = "reflection/ButtonMethods";

  private final ResourceBundle imageBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle commandBundle;
  private final FrontEndController controller;

  public ButtonFactory(FrontEndController controller) {
    this.imageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + IMAGE_PROPERTY);
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.commandBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + METHODS_PROPERTY);
    this.controller = controller;
  }

  public Button createDisableableButton(String property) {
    Button button = makeButton(property);
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
          .getDeclaredMethod(commandBundle.getString(property), Button.class);
        m.invoke(controller, button);
      } catch (Exception e) {
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return button;
  }

  public Button createTextButton(String property, TextField textField) {
    Button button = makeButton(property);
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
          .getDeclaredMethod(commandBundle.getString(property), TextField.class);
        m.invoke(controller, textField);
      } catch (Exception e) {
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return button;
  }

  private Button makeButton(String property) {
    Button button = new Button();
    button.setId(idBundle.getString(property));
    String label = imageBundle.getString(property);
    button.setGraphic(new ImageView(
      new Image(DEFAULT_RESOURCE_FOLDER + label, ICON_WIDTH,
        ICON_HEIGHT, false, false)));
    return button;
  }
}
