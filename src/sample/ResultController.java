package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResultController implements Initializable {


        @FXML
        private TableView<Result> ResultTableView;

        @FXML
        private TableColumn<Result, String> Results;

        @FXML
        private TableColumn<Result, String> Date;

        @FXML
        private TableColumn<Result, String> ExerciseId;

        @FXML
        void backButton(ActionEvent event) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            try {

                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = loader.load();
                                Scene scene = new Scene(root);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        ArrayList<Result> results;
    Database database = new Database();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        results = database.getMathResults(Database.getUsername());

        ResultTableView.getItems().setAll(results);

        Results.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Result, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Result, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getResultScore());
            }
        });

        Date.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Result, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Result, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getDate());
            }
        });

        ExerciseId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Result, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Result, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getExerciseName());
            }
        });

        ResultTableView.setRowFactory( tv -> {
            TableRow<Result> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Result rowData = row.getItem();



                }
            });
            return row ;
        });


    }
}
