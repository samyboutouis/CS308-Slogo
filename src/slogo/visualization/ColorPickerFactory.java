package slogo.visualization;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import slogo.controller.Controller;

public class ColorPickerFactory {
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String METHODS_PROPERTY = "methods/Methods";

  private final ResourceBundle idBundle;
  private final ResourceBundle commandBundle;
  private final Controller controller;

  public ColorPickerFactory(Controller controller) {
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.commandBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + METHODS_PROPERTY);
    this.controller = controller;
  }

  public ColorPicker makeColorPicker(String property, Button button) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setId(idBundle.getString(property));

    colorPicker.setOnAction(handler -> {
      try {
        Method m = this.getClass()
          .getDeclaredMethod(commandBundle.getString(property), Button.class);
        m.invoke(controller, button, colorPicker);
      } catch (Exception e) {
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return colorPicker;
  }
}
