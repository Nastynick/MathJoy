package sample;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminExerciseTasksController implements Initializable {

    @FXML
    private TableView<Exercise> exerciseTableView;

    @FXML
    private TableColumn<?, ?> exerciseNameColumn;

    @FXML
    private TableColumn<?, ?> exerciseIDColumn;

    @FXML
    private TableView<Task> taskTableView;

    @FXML
    private TableColumn<?, ?> taskIDColumn;

    @FXML
    private TextField exerciseAddField;

    @FXML
    private Button exerciseAddButton;

    @FXML
    private TextField exerciseIDField;

    @FXML
    private TextArea taskAnswerTextArea;

    @FXML
    private TextArea taskQuestionTextArea;
    @FXML
    private TextField exerciseNameField;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> modeSelector;

    @FXML
    private Label feedBackLabel;

    private Database database = new Database();

    private ObservableList<Exercise> exerciseData = null;
    private ObservableList<Task> taskData = null;

    private int selectedID = -1;

    @FXML
    void onAddButtonPressed(ActionEvent event) throws SQLException {
        if (taskAnswerTextArea.getText().equals("") || taskQuestionTextArea.getText().equals("") || exerciseIDField.getText().equals("")) {
            feedBackLabel.setText("Fields empty!");
            return;
        }
        Task task = new Task(-1, taskQuestionTextArea.getText(), taskAnswerTextArea.getText());
        if (database.insertTask(task, Integer.valueOf(exerciseIDField.getText()))) {
            feedBackLabel.setText("Task added!");
            System.out.println("Added");
            taskData = FXCollections.observableArrayList(database.getTasks(Integer.valueOf(exerciseIDField.getText())));
            taskTableView.setItems(taskData);
            taskAnswerTextArea.setText("");
            taskQuestionTextArea.setText("");
        } else {
            feedBackLabel.setText("Something went wrong, please try again.");
        }

    }

    @FXML
    void onBackButtonPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuAdminView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void onDeleteButtonPressed(ActionEvent event) throws SQLException {
        if (taskAnswerTextArea.getText().equals("") || taskQuestionTextArea.getText().equals("") || exerciseIDField.getText().equals("")) {
            feedBackLabel.setText("Fields empty!");
            return;
        }
        Task task = new Task(selectedID, taskQuestionTextArea.getText(), taskAnswerTextArea.getText());
        if (database.deleteTask(task)) {
            feedBackLabel.setText("Task deleted!");
            taskData = FXCollections.observableArrayList(database.getTasks(Integer.valueOf(exerciseIDField.getText())));
            taskTableView.setItems(taskData);
            taskAnswerTextArea.setText("");
            taskQuestionTextArea.setText("");
            selectedID = -1;
        } else {
            feedBackLabel.setText("Something went wrong, please try again.");
        }

    }

    @FXML
    void onExerciseAddButtonPressed(ActionEvent event) throws SQLException {
        if (exerciseAddField.getText().equals("")) {
            feedBackLabel.setText("Exercise field empty!");
            return;
        }


        Exercise exercise = new Exercise(-1, exerciseAddField.getText());
        if (database.addExercise(exercise)) {
            System.out.println("Added");
            exerciseData = FXCollections.observableArrayList(database.getAllExcercises());
            exerciseTableView.setItems(exerciseData);
            exerciseAddField.setText("");
            feedBackLabel.setText("Exercise Added!");
        } else {
            feedBackLabel.setText("Something went wrong!");
        }
    }

    @FXML
    void onUpdateButtonPressed(ActionEvent event) throws SQLException {
        if (taskAnswerTextArea.getText().equals("") || taskQuestionTextArea.getText().equals("") || exerciseIDField.getText().equals("")) {
            feedBackLabel.setText("Fields empty!");
            return;
        }

        Task task = new Task(selectedID, taskQuestionTextArea.getText(), taskAnswerTextArea.getText());
        if (database.editTask(task)) {
            feedBackLabel.setText("Task edited!");
            taskData = FXCollections.observableArrayList(database.getTasks(Integer.valueOf(exerciseIDField.getText())));
            taskTableView.setItems(taskData);
            selectedID = -1;
            taskQuestionTextArea.setText("");
            taskAnswerTextArea.setText("");
        } else {
            feedBackLabel.setText("Something went wrong");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> { // runs code after initialize is done, in order to use visual elements
            modeSelector.getItems().setAll("Edit", "Add");
            modeSelector.getSelectionModel().selectFirst();
            updateButton.setDisable(false);
            addButton.setDisable(true);
            deleteButton.setDisable(false);

            modeSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    if (modeSelector.getItems().get((Integer) number2).equals("Add")) {
                        updateButton.setDisable(true);
                        addButton.setDisable(false);
                        deleteButton.setDisable(true);
                    } else {
                        updateButton.setDisable(false);
                        addButton.setDisable(true);
                        deleteButton.setDisable(false);
                    }
                }
            });
            exerciseData = FXCollections.observableArrayList(database.getAllExcercises()); //casts exercises to an observable array.
            exerciseTableView.setItems(exerciseData); //add the array to the combobox

            exerciseIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID")); //links the cell to the specific variable name
            exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // same
            taskIDColumn.setCellValueFactory(new PropertyValueFactory<>("QuestionID"));

            exerciseTableView.setRowFactory((TableView<Exercise> tv) -> { // click event.
                TableRow<Exercise> row = new TableRow<>();
                row.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 1 && (!row.isEmpty())) {
                        Exercise rowData = row.getItem();

                        exerciseNameField.setText(rowData.getName());
                        exerciseIDField.setText(String.valueOf(rowData.getID()));

                        taskData = FXCollections.observableArrayList(database.getTasks(rowData.getID()));
                        taskTableView.setItems(taskData);
                        taskQuestionTextArea.setText("");
                        taskAnswerTextArea.setText("");
                    } else if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Exercise rowData = row.getItem();
                        try {
                            if (database.deleteExercise(rowData)) {
                                feedBackLabel.setText("Exercise deleted!");
                                exerciseData = FXCollections.observableArrayList(database.getAllExcercises());
                                exerciseTableView.setItems(exerciseData);
                                exerciseIDField.setText("");
                                exerciseNameField.setText("");
                            } else {
                                feedBackLabel.setText("Something went wrong!");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

                row.setOnMouseEntered(event -> {

                    if (!row.isEmpty()) {
                        ScaleTransition scaleTransition = new ScaleTransition();
                        scaleTransition.setNode((Node)event.getSource());
                        scaleTransition.setDuration(Duration.millis(100));
                        scaleTransition.setToX(1.02);
                        scaleTransition.setToY(1.05);
                        scaleTransition.play();

                    }
                });

                row.setOnMouseExited(event -> {

                    if (!row.isEmpty()) {
                        ScaleTransition scaleTransition = new ScaleTransition();
                        scaleTransition.setNode((Node)event.getSource());
                        scaleTransition.setDuration(Duration.millis(100));
                        scaleTransition.setToX(1);
                        scaleTransition.setToY(1);
                        scaleTransition.play();

                    }
                });

                return row; //returns row
            });

            taskTableView.setRowFactory((TableView<Task> tv) -> { // click event.
                TableRow<Task> row = new TableRow<>();
                row.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 1 && (!row.isEmpty())) {
                        Task rowData = row.getItem();
                        taskQuestionTextArea.setText(rowData.getQuestionText());
                        taskAnswerTextArea.setText(rowData.getAnswer());
                        selectedID = rowData.getQuestionID();
                    }
                });

                row.setOnMouseEntered(event -> {

                    if (!row.isEmpty()) {
                        ScaleTransition scaleTransition = new ScaleTransition();
                        scaleTransition.setNode((Node)event.getSource());
                        scaleTransition.setDuration(Duration.millis(100));
                        scaleTransition.setToX(1.02);
                        scaleTransition.setToY(1.05);
                        scaleTransition.play();

                    }
                });

                row.setOnMouseExited(event -> {

                    if (!row.isEmpty()) {
                        ScaleTransition scaleTransition = new ScaleTransition();
                        scaleTransition.setNode((Node)event.getSource());
                        scaleTransition.setDuration(Duration.millis(100));
                        scaleTransition.setToX(1);
                        scaleTransition.setToY(1);
                        scaleTransition.play();

                    }
                });

                return row; //returns row
            });

        });


    }
}

