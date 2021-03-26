package slogo.visualization;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import slogo.visualization.turtle.Turtle;

/**
 * Responsible for creating different types of buttons and assigning different event handlers using
 * reflection. Dependent on the FrontEndController class for the button event handlers and property
 * files to receive the correct method names and image names.
 *
 * @author Samy Boutouis
 */
public class ButtonFactory {

  private static final int ICON_WIDTH = 30;
  private static final int ICON_HEIGHT = 30;
  private static final String DEFAULT_RESOURCE_FOLDER = "resources/";
  private static final String IMAGE_PROPERTY = "reflection/Image";
  private static final String ID_PROPERTY = "stylesheets/CSS_IDs";
  private static final String METHODS_PROPERTY = "reflection/ButtonMethods";
  private static final String LABELS_PROPERTY = "reflection/ButtonText";
  private static final String ERROR = "Improper configuration";

  private final ResourceBundle imageBundle;
  private final ResourceBundle idBundle;
  private final ResourceBundle commandBundle;
  private final ResourceBundle labelBundle;
  private final FrontEndController controller;

  /**
   * Constructor for class.
   *
   * @param controller FrontEndController responsible for having all the button event handler methods.
   */
  public ButtonFactory(FrontEndController controller) {
    this.imageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + IMAGE_PROPERTY);
    this.idBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + ID_PROPERTY);
    this.commandBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + METHODS_PROPERTY);
    this.labelBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_FOLDER + LABELS_PROPERTY);
    this.controller = controller;
  }

  /**
   * Creates a default button with plain text.
   *
   * @param property Key value in the label bundle containing the text of the button
   * @return Button object containing the correct text and associated event handler
   */
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
        throw new RuntimeException(ERROR, e);
      }
    });
    return button;
  }

  /**
   * Create a button that performs actions on the turtle.
   *
   * @param property Key value in the resource bundle containing image names, method names, etc.
   * @param turtle Turtle object that is having methods called on it on the screen
   * @return Button containing correct image and event handler
   */
  public Button createTurtleButton(String property, Turtle turtle) {
    Button button = makeButton(property);
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
            .getDeclaredMethod(commandBundle.getString(property), Button.class, Turtle.class);
        m.invoke(controller, button, turtle);
      } catch (Exception e) {
        throw new RuntimeException(ERROR, e);
      }
    });
    return button;
  }

  /**
   * Create a button object that is able to toggle itself by passing itself in to its event handler
   *
   * @param property Key value in the resource bundle containing image names, method names, etc.
   * @return Button containing correct image and event handler
   */
  public Button createToggleButton(String property) {
    Button button = makeButton(property);
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
            .getDeclaredMethod(commandBundle.getString(property), Button.class);
        m.invoke(controller, button);
      } catch (Exception e) {
        throw new RuntimeException(ERROR, e);
      }
    });
    return button;
  }

  /**
   * Create a button object that uses information from a text field to call its event handler
   *
   * @param property Key value in the resource bundle containing image names, method names, etc.
   * @param textField TextField that contains information that the user inputs into the box
   * @return Button containing correct image and event handler
   */
  public Button createTextFieldButton(String property, TextField textField) {
    Button button = makeButton(property);
    button.setOnAction(handler -> {
      try {
        Method m = controller.getClass()
            .getDeclaredMethod(commandBundle.getString(property), TextField.class);
        m.invoke(controller, textField);
      } catch (Exception e) {
        throw new RuntimeException(ERROR, e);
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

  /**
   * Set the image associated with a button by passing in a property in the Image ResourceBundle
   *
   * @param button Button that is having its image graphic changed
   * @param property Key value in the resource bundle containing the image name
   */
  public void setImage(Button button, String property) {
    String label = imageBundle.getString(property);
    button.setGraphic(new ImageView(
        new Image(DEFAULT_RESOURCE_FOLDER + label, ICON_WIDTH,
            ICON_HEIGHT, false, false)));
  }
}