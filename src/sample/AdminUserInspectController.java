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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminUserInspectController implements Initializable {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<?, ?> userColumn;

    @FXML
    private TableView<Result> resultTableView;

    @FXML
    private TableColumn<?, ?> resultIdColumn;

    @FXML
    private TableColumn<?, ?> exerciseIDColumn;

    @FXML
    private TableColumn<?, ?> exerciseNameColumn;

    @FXML
    private TableColumn<?, ?> resultScoreColumn;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private Button backButton;

    private ObservableList<User> userData = null;
    private ObservableList<Result> resultData = null;
    private Database database = new Database();


    @FXML
    void onBackButtonpressed(ActionEvent event) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(()->{ // runs code after initialize is done, in order to use visual elements

            userColumn.setCellValueFactory(new PropertyValueFactory<>("username")); //links the cell to the specific variable name
            exerciseIDColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseID"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            resultIdColumn.setCellValueFactory(new PropertyValueFactory<>("resultID"));
            exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseName"));
            resultScoreColumn.setCellValueFactory(new PropertyValueFactory<>("resultScore"));

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
                        resultData = FXCollections.observableArrayList(database.getMathResults(rowData.getUsername()));
                        resultTableView.setItems(resultData);
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

