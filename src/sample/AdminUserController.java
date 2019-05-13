package sample;

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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminUserController implements Initializable {

    private Database database = new Database();
    private ObservableList<User> userData = null;

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
    private Label feedbackLabel;


    @FXML
    private Button onBackButtonPressed;

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
    void onUserEditButtonPressed(ActionEvent event) {

    }

    @FXML
    void onUserRemoveButtonPressed(ActionEvent event) throws SQLException {
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
        isTeacherBox.setDisable(true);

        Platform.runLater(()->{
            columnOne.setCellValueFactory(new PropertyValueFactory<>("username"));
            columnTwo.setCellValueFactory(new PropertyValueFactory<>("isTeacher"));

            try {
                userData = FXCollections.observableArrayList(database.getAllUsers());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            userTableView.setItems(userData);

            userTableView.setRowFactory((TableView<User> tv) -> {
                TableRow<User> row = new TableRow<>();
                row.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 1 && (!row.isEmpty())) {
                        User rowData = row.getItem();
                        userNameField.setText(rowData.getUsername());
                        passwordField.setText(rowData.getPassword());

                        if (rowData.getIsTeacher().equals("true")) {
                            isTeacherBox.setSelected(true);
                        } else {
                            isTeacherBox.setSelected(false);
                        }
                    }
                });
                return row;
            });




        });



    }

    private void lockAllButtons() {
        addButton.setDisable(true);
        editButton.setDisable(true);
        removeButton.setDisable(true);
        onBackButtonPressed.setDisable(true);
    }

    private void unlockAllButtons() {
        addButton.setDisable(false);
        editButton.setDisable(false);
        removeButton.setDisable(false);
        onBackButtonPressed.setDisable(false);
    }
}
