package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExSelectionController implements Initializable {

    @FXML
    private ListView<String> listView;

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
        stage.setScene(new Scene(root));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listView.getItems().addAll("Exercise 1", "XE2");

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());


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
    }
}
