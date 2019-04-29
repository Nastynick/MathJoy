package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    void CalculatorButton(ActionEvent event) {

    }

    @FXML
    void exersicesButton(ActionEvent event) {

    }

    @FXML
    void exitButton(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void placeholder1Button(ActionEvent event) {

    }

    @FXML
    void placeholder2Button(ActionEvent event) {

    }

    @FXML
    void loginButtonPressed(ActionEvent event) {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginView.fxml"));
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

    }
}
