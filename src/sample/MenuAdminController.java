package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuAdminController implements Initializable {

    @FXML
    private Button adminUserButton;

    @FXML
    private Button adminExerciseButton;

    @FXML
    private Button adminTaskButton;

    @FXML
    private Button adminExitButton;

    @FXML
    private Button adminViewUserButton;

    @FXML
    void adminExerciseButtonClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminExerciseTasksView.fxml"));
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
    void adminExitClicked(ActionEvent event) {

    }

    @FXML
    void adminTaskButtonClicked(ActionEvent event) {

    }

    @FXML
    void adminUserButtonClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminUserView.fxml"));
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
    void adminViewUserButtonClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Effects.inflateDeflate(adminExerciseButton);
        Effects.inflateDeflate(adminExitButton);
        Effects.inflateDeflate(adminTaskButton);
        Effects.inflateDeflate(adminUserButton);
        Effects.inflateDeflate(adminViewUserButton);

    }
}
