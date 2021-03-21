package slogo.visualization;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import slogo.SafeTurtle;
import slogo.controller.FrontEndController;

public class ButtonFactory {
  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String IMAGE_PROPERTY = "reflection/Image";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String METHODS_PROPERTY = "reflection/ButtonMethods";
  private static final String LABELS_PROPERTY = "reflection/ButtonText";

  private final ResourceBundle imageBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle commandBundle;
  private final ResourceBundle labelBundle;
  private final FrontEndController controller;

  public ButtonFactory(FrontEndController controller) {
    this.imageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + IMAGE_PROPERTY);
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.commandBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + METHODS_PROPERTY);
    this.labelBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + LABELS_PROPERTY);
    this.controller = controller;
  }

  public Button createDefaultButton(String property) {
    Button button = new Button();
    button.setText(labelBundle.getString(property));
    button.setId(idBundle.getString(property));
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
          .getDeclaredMethod(commandBundle.getString(property));
        m.invoke(controller);
      } catch (Exception e) {
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return button;
  }

  public Button createTurtleButton(String property, SafeTurtle turtle) {
    Button button = makeButton(property);
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
          .getDeclaredMethod(commandBundle.getString(property), Button.class, SafeTurtle.class);
        m.invoke(controller, button, turtle);
      } catch (Exception e) {
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return button;
  }

  public Button createToggleButton(String property) {
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

  public Button createTextFieldButton(String property, TextField textField) {
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
    setImage(button, property);
    return button;
  }

  public void setImage(Button button, String property) {
    String label = imageBundle.getString(property);
    button.setGraphic(new ImageView(
      new Image(DEFAULT_RESOURCE_FOLDER + label, ICON_WIDTH,
        ICON_HEIGHT, false, false)));
  }
}
