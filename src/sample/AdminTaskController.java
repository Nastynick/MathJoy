package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminTaskController {

    @FXML
    private TableView<?> userTableView;

    @FXML
    private TableColumn<?, ?> columnOne;

    @FXML
    private TableColumn<?, ?> column2;

    @FXML
    private Button removeButton;

    @FXML
    private TextField idField;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

    @FXML
    private TextArea answerField;

    @FXML
    private TextArea questionField;

    @FXML
    private ChoiceBox<?> exerciseChoiceBox;

}

