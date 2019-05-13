package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminExerciseController {

    @FXML
    private TableView<?> ExerciseTableView;

    @FXML
    private TableColumn<?, ?> columnOne;

    @FXML
    private TableColumn<?, ?> column2;

    @FXML
    private Button removeButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;

}

