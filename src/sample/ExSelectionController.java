package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExSelectionController implements Initializable {

    @FXML
    private TableView<Exercise> tableView;
    @FXML
    private TableColumn<Exercise, String> exerciseName;


    @FXML
    void backPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        assert root != null;
        stage.setScene(new Scene(root));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database database = new Database();

        ArrayList<Exercise> exercises = database.getExcercises("Marijana");
        tableView.getItems().addAll(exercises);


        exerciseName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Exercise, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Exercise, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getName());
            }
        });

        tableView.setRowFactory( tv -> {
            TableRow<Exercise> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                   Exercise rowData = row.getItem();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskWindowView.fxml"));
                    try {

                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        Parent root = loader.load();
                        TaskWindowController oh = loader.getController();
                        oh.setData(rowData.getID());
                        Scene scene = new Scene(root);
                        stage.setScene(scene);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
            return row ;
        });


    }
}


/*
       tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + tableView.getSelectionModel().getSelectedItem());


                FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskWindowView.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));

            }
        });

 */


