package slogo.visualization.displays;

import java.util.List;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import slogo.visualization.FrontEndController;
import slogo.visualization.ButtonFactory;
import slogo.visualization.Workspace;

/**
 * Display view class that implements movements for all active turtles on the screen graphically.
 * Dependent on the workspace and the front-end controller. Should be used to move all active
 * turtles on the screen or rotate them. Still works even if there are only inactive turtles on the
 * screen. This class incorporates good design as it shows use of the ButtonFactory class.
 * Specifically, by using the ButtonFactory in this class, one simply needs to write the properties
 * of each button as specified in the properties file, and the button factory will handle the creation
 * of the actual button and its event handlers. By incorporating this factory design, the code is much
 * more concise, easy to read, and flexible. If I wanted to add another button to the screen, all I
 * would have to do is add the new property string to the list, create the event handler for it, and
 * add the property to the properties file. It also keeps the implementation encapsulated.
 *
 * @author Samy Boutouis
 */
public class ButtonDisplay extends ScrollingDisplay {

  private static final String TITLE = "ButtonsTitle";
  private static final String BUTTON_BOX_ID = "ButtonBoxID";
  private static final String TEXT_FIELD_ID = "TextField";
  private static final String NUMBER_REGEX = "[0-9]*";

  private final VBox vbox;
  private final ButtonFactory buttonFactory;
  private final List<String> buttonList;
  private final GridPane pane;

  /**
   * Constructor for class.
   *
   * @param workspace Workspace object representing entire IDE
   * @param frontEndController Controller handling changes to entire workspace from buttons
   */
  public ButtonDisplay(Workspace workspace, FrontEndController frontEndController) {
    super(workspace);
    vbox = setupVBoxContainer(TITLE, BUTTON_BOX_ID);
    buttonFactory = new ButtonFactory(frontEndController);
    buttonList = List.of("UpButton", "DownButton", "RightButton", "LeftButton");
    pane = new GridPane();
    addFields();
    pane.add(vbox, 0, 0);
  }

  private void addFields() {
    for (String property : buttonList) {
      HBox hbox = new HBox();
      TextField textField = makeTextField();
      hbox.getChildren().add(textField);
      textField.setId(TEXT_FIELD_ID + property);
      hbox.getChildren().add(buttonFactory.createTextFieldButton(property, textField));
      vbox.getChildren().add(hbox);
    }
  }

  private TextField makeTextField() {
    UnaryOperator<Change> filter = change -> {
      String text = change.getText();
      if (text.matches(NUMBER_REGEX)) {
        return change;
      }
      return null;
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    TextField textField = new TextField();
    textField.setTextFormatter(textFormatter);
    return textField;
  }

  /**
   * Returns the root pane of the display view.
   *
   * @return Root pane of the display view
   */
  public GridPane getPane() {
    return pane;
  }
}
