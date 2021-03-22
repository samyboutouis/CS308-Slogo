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

public class ButtonDisplay extends ScrollingDisplay {

  private static final String TITLE = "ButtonsTitle";
  private static final String BUTTON_BOX_ID = "ButtonBoxID";

  private final VBox vbox;
  private final ButtonFactory buttonFactory;
  private final List<String> buttonList;
  private final GridPane pane;

  public ButtonDisplay(Workspace workspace, String resourcePackage,
      FrontEndController frontEndController) {
    super(workspace, resourcePackage);
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
      hbox.getChildren().add(buttonFactory.createTextFieldButton(property, textField));
      vbox.getChildren().add(hbox);
    }
  }

  private TextField makeTextField() {
    UnaryOperator<Change> filter = change -> {
      String text = change.getText();
      if (text.matches("[0-9]*")) {
        return change;
      }
      return null;
    };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    TextField textField = new TextField();
    textField.setTextFormatter(textFormatter);
    return textField;
  }

  public GridPane getPane() {
    return pane;
  }
}
