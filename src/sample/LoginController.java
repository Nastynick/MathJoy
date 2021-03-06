package sample;

import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Database database = new Database();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    private Button emergencyDemoButton;

    @FXML
    void onEmergencyDemoButtonPressed(ActionEvent event) {
        database.setDatabasethings();
        errorLabel.setText("DEMO: Set to local database");
        emergencyDemoButton.setDisable(true);
    }


    @FXML
    void loginButtonPressed(ActionEvent event) {
        boolean loginAttempt = true;

        if (username.getText().length() < 3) {
            loginAttempt = false;
        }

        if (password.getText().length() < 3) {
            loginAttempt = false;
        }

        try {
            if (database.login(username.getText(), password.getText()) && loginAttempt) {
                Database.setUsername(username.getText());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("sample/Button.css");
                stage.setScene(scene);
            } else {
                errorLabel.setText("Invalid username/password!");
            }
        } catch (Exception e) {
            errorLabel.setText("ERROR - GEARHOST OFFLINE.");
            emergencyDemoButton.setVisible(true);
            e.printStackTrace();
        }



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
