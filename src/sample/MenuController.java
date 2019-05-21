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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button exerciseButton;

    @FXML
    private Button adminButton;

    @FXML
    private Button resultButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button calculatorButton;



    @FXML
    void CalculatorButton(ActionEvent event) {
/*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Placeholder1View.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

 */

        }


    @FXML
    void exersicesButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExSelectionView.fxml"));
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
    void exitButton(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void calculatorButton(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculatorView.fxml"));
        Parent root = null;
        try {
            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Calculator");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML

    void onAdminButtonPressed(ActionEvent event) {
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



    @FXML
    void resultsButton(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultView.fxml"));
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

        Database database = new Database();
        try {
            if (database.isTeacher(Database.getUsername())) {
                adminButton.setVisible(true);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        Effects.inflateDeflate(exerciseButton);
        Effects.inflateDeflate(adminButton);
        Effects.inflateDeflate(calculatorButton);
        Effects.inflateDeflate(resultButton);
        Effects.inflateDeflate(exitButton);

        /*
        exerciseButton.setStyle("-fx-background-color: #4286f4");
        placeholderButton.setStyle("-fx-background-color: #ed4040");
        calculatorButton.setStyle("-fx-background-color: #43ed3d");
        resultButton.setStyle("-fx-background-color: #ed3fed");
        exitButton.setStyle("-fx-background-color: #bab6b7");

         */


    }
}
