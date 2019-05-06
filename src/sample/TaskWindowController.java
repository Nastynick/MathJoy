package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaskWindowController implements Initializable {
    SwitchScene switchScene = new SwitchScene();

    @FXML
    void backPressed(ActionEvent event) {


       switchScene.switchScene(event, "ExSelectionView.fxml");

    }


   // private Text text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //text.setText("Question 1");
    }
}
