package slogo.visualization;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class VariablesDisplay {

  private final static int PADDING_LENGTH = 10;
  private final static int ROW_COUNT = 10;
  private final static String VARIABLES_TITLE = "VariablesTitle";
  private final static String VARIABLES_BOX_ID = "VariablesBoxID";
  private final static String VARIABLES_TAG_ID = "VariablesTagID";

  private final GridPane pane;
  private ScrollPane scrollPane;
  private VBox variablesBox;

  private final ResourceBundle resourceBundle;

  public VariablesDisplay(GridPane pane, String resourcePackage){
    this.pane = pane;

    pane.setMaxWidth(Double.MAX_VALUE);
    pane.setMaxHeight(Double.MAX_VALUE);

    pane.setVgap(PADDING_LENGTH);
    pane.setPadding(new Insets(PADDING_LENGTH));

    String language = "English";
    this.resourceBundle = ResourceBundle.getBundle(String.format("%s/%s/%s", resourcePackage, "languages", language));

    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    col.setPercentWidth(100.0);
    pane.getColumnConstraints().add(col);

    for(int i = 0; i < ROW_COUNT; i++){
      RowConstraints row = new RowConstraints();
      row.setVgrow(Priority.ALWAYS);
      row.setPercentHeight(100.0 / ROW_COUNT);
      pane.getRowConstraints().add(row);
    }

    initializeTitleLabel();
    initializeVariablesBox();
  }

  private void initializeTitleLabel(){
    Label title = new Label(resourceBundle.getString(VARIABLES_TITLE));
    pane.add(title, 0, 0, 1, 1);
  }

  private void initializeVariablesBox(){
    scrollPane = new ScrollPane();
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(1);

    scrollPane.setId(resourceBundle.getString(VARIABLES_BOX_ID));
    pane.add(scrollPane, 0, 1, 1, 9);

    variablesBox = new VBox();
    variablesBox.setFillWidth(true);
    variablesBox.setMaxHeight(Double.MAX_VALUE);
    variablesBox.setSpacing(PADDING_LENGTH);
    variablesBox.setPadding(new Insets(PADDING_LENGTH));

    scrollPane.setContent(variablesBox);
  }

  public void updateVariablesBox(String[] variablesList){
    variablesBox.getChildren().clear();

    for(int i = 0; i < variablesList.length; i++){
      String variableName = variablesList[i];
      addNewVariablesTab(variableName);
    }
  }

  private void addNewVariablesTab(String variableName){
    Button variablesTab = new Button(variableName);
    variablesTab.setMaxWidth(Double.MAX_VALUE);
    variablesTab.setMaxHeight(Double.MAX_VALUE);
    variablesTab.setId(resourceBundle.getString(VARIABLES_TAG_ID));

    variablesBox.getChildren().add(variablesTab);

    scrollPane.setVvalue(1.0); // doesnt auto scroll all the way down... wtf
  }
}
