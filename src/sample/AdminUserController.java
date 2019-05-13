package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminUserController implements Initializable {

    Database database = new Database();

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
    void onUserAddButtonPressed(ActionEvent event) {

    }

    @FXML
    void onUserEditButtonPressed(ActionEvent event) {

    }

    @FXML
    void onUserRemoveButtonPressed(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(()->{
            columnOne.setCellValueFactory(new PropertyValueFactory<>("username"));
            columnTwo.setCellValueFactory(new PropertyValueFactory<>("isTeacher"));

            ObservableList<User> userData = null;
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
}
