package slogo.visualization;

import java.util.List;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import slogo.controller.FrontEndController;

public class ButtonView implements View {
  private VBox vbox;
  private ButtonFactory buttonFactory;
  private final List<String> buttonList;
  private final GridPane pane;

  public ButtonView(GridPane gridPane, FrontEndController frontEndController) {
    vbox = new VBox();
    buttonFactory = new ButtonFactory(frontEndController);
    buttonList = List.of("UpButton", "DownButton", "RightButton", "LeftButton");
    pane = gridPane;
    addFields();
    pane.add(vbox, 0, 0);
  }

  private void addFields() {
    for(String property : buttonList) {
      HBox hbox = new HBox();
      TextField textField = makeTextField();
      hbox.getChildren().add(textField);
      hbox.getChildren().add(buttonFactory.createTextButton(property, textField));
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

  public void hide() {
    vbox.setVisible(false);
  }

  public void show() {
    vbox.setVisible(true);
  }

  public void setPosition(double xPosition, double yPosition) {

  }
}
