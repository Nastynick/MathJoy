package sample;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
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

public class AdminUserController implements Initializable {

    private Database database = new Database();
    private ObservableList<User> userData = null;
    private ObservableList<Exercise> exerciseData = null;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<?, ?> columnOne;

    @FXML
    private TableColumn<?, ?> columnTwo;

    @FXML
    private Button removeButton;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private CheckBox isTeacherBox;

    @FXML
    private Button editButton;

    @FXML
    private Button addButton;


    @FXML
    private Label feedbackLabel; //the label used to tell if success or failure


    @FXML
    private Button onBackButtonPressed;

    @FXML
    private Button exerciseAddButton;

    @FXML
    private ComboBox<Exercise> exerciseAddComboBox;

    @FXML
    void onExerciseAddButtonPressed(ActionEvent event) throws SQLException {
        Exercise ex = exerciseAddComboBox.getValue(); //gets the value from the box
        User user = new User(userNameField.getText(), passwordField.getText(), "false");
        boolean success = database.addUserToExercise(user, ex.getID());
        if (success) {
            feedbackLabel.setText("User added to exercise!");
        } else {
            feedbackLabel.setText("User does not exist or is already added!");
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
    void onUserAddButtonPressed(ActionEvent event) throws SQLException {
        if (emptyCheck()) {
            return;
        }
        lockAllButtons();
        User user = new User(userNameField.getText(), passwordField.getText(), "false");
        boolean success = database.insertUser(user);
        if (success) {
            feedbackLabel.setText("User added to database!");
            try {
                userData = FXCollections.observableArrayList(database.getAllUsers());
                userTableView.setItems(userData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            feedbackLabel.setText("User already exists!");
        }
        unlockAllButtons();
    }

    @FXML
    void onUserEditButtonPressed(ActionEvent event) throws SQLException {
        if (emptyCheck()) {
            return;
        }
        boolean duplicate = false;
        User user = new User(userNameField.getText(), passwordField.getText(), "false");

        boolean success = database.editUser(user);

        for (User user1 : userData) {
            if (user1.getUsername().equals(userNameField.getText()) && user1.getPassword().equals(passwordField.getText())) {
                duplicate = true;
            }

        }
        if (duplicate) {
            feedbackLabel.setText("Please change password before pressing edit");
            return;
        }
        lockAllButtons();
        if (success) {
            feedbackLabel.setText("User has been updated!");
            userData = FXCollections.observableArrayList(database.getAllUsers());
            userTableView.setItems(userData);
        } else {
            feedbackLabel.setText("User does not exist!");
        }
        unlockAllButtons();
    }

    @FXML
    void onUserRemoveButtonPressed(ActionEvent event) throws SQLException {
        if (emptyCheck()) {
            return;
        }
        lockAllButtons();
        User user = new User(userNameField.getText(), passwordField.getText(), "false");
        boolean success = database.removeUser(user);
        if (success) {
            feedbackLabel.setText("User removed!");
            userData = FXCollections.observableArrayList(database.getAllUsers());
            userTableView.setItems(userData);
        } else {
            feedbackLabel.setText("User does not exist!");
        }
        unlockAllButtons();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isTeacherBox.setDisable(true); //disables the box, only made to show if they are a teacher

        Platform.runLater(()->{ // runs code after initialize is done, in order to use visual elements
            exerciseData = FXCollections.observableArrayList(database.getAllExcercises()); //casts exercises to an observable array.
            exerciseAddComboBox.setItems(exerciseData); //add the array to the combobox
            exerciseAddComboBox.getSelectionModel().selectFirst(); //makes the first choice the default one

            columnOne.setCellValueFactory(new PropertyValueFactory<>("username")); //links the cell to the specific variable name
            columnTwo.setCellValueFactory(new PropertyValueFactory<>("isTeacher")); // same

            try {
                userData = FXCollections.observableArrayList(database.getAllUsers());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            userTableView.setItems(userData);

            userTableView.setRowFactory((TableView<User> tv) -> { // click event.
                TableRow<User> row = new TableRow<>();
                row.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 1 && (!row.isEmpty())) {
                        User rowData = row.getItem();
                        userNameField.setText(rowData.getUsername()); //sets the data to the selected row
                        passwordField.setText(rowData.getPassword());

                        if (rowData.getIsTeacher().equals("true")) { //shows if they are a teacher or not
                            isTeacherBox.setSelected(true);
                        } else {
                            isTeacherBox.setSelected(false);
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

        });



    }

    private void lockAllButtons() { //ghosting protection
        addButton.setDisable(true);
        editButton.setDisable(true);
        removeButton.setDisable(true);
        onBackButtonPressed.setDisable(true);
    }

    private void unlockAllButtons() { //ghosting protection, resets fields.
        addButton.setDisable(false);
        editButton.setDisable(false);
        removeButton.setDisable(false);
        onBackButtonPressed.setDisable(false);
        userNameField.setText("");
        passwordField.setText("");
        isTeacherBox.setSelected(false);
    }

    private boolean emptyCheck () { // checks if fields are empty, returns true if they are
        if (userNameField.getText().equals("") || passwordField.getText().equals("")) {
            feedbackLabel.setText("Username or password can not be empty!");
            return true;
        }
        return false;
    }
}
