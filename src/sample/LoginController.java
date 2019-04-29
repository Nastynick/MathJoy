package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Database database = new Database();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    void loginButtonPressed(ActionEvent event) {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));


    }

    public String getUsername(){
        return username.getText();
    }

    public String getPassword(){
        return password.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
