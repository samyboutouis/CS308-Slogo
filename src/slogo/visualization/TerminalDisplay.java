package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TerminalDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static String BUTTON_LABEL = "TerminalButton";

  private final ResourceBundle resourceBundle;
  private final HBox pane;

  public TerminalDisplay(HBox pane, String resourcePackage){
    this.pane = pane;

    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));

    pane.setSpacing(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH, PADDING_LENGTH));

    initializeTextField();
    initializeButton();
  }

  private void initializeTextField(){
    TextArea textBox = new TextArea();
    //textBox.setMaxWidth(100);
    textBox.setMaxHeight(Double.MAX_VALUE);
    textBox.setWrapText(true);
    //textBox.setId("TerminalTextArea");

    HBox.setHgrow(textBox, Priority.ALWAYS);

    pane.getChildren().add(textBox);
  }

  private void initializeButton(){
    Button button = new Button(resourceBundle.getString(BUTTON_LABEL));
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMaxHeight(Double.MAX_VALUE);

    HBox.setHgrow(button, Priority.ALWAYS);

    pane.getChildren().add(button);
  }
}
