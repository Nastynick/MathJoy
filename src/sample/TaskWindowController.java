package sample;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskWindowController implements Initializable {
    SwitchScene switchScene = new SwitchScene();
    ArrayList<Task> tasks = new ArrayList<>();
    Database database = new Database();
    int id;

    @FXML
    private ListView<String> taskList;

    @FXML
    private TextArea answerBox;

    @FXML
    private Text questionId;


    @FXML
    void backPressed(ActionEvent event) {


       switchScene.switchScene(event, "ExSelectionView.fxml");

    }


   // private Text text;

    public void setData(int idx){
        id = idx;
    }
    public void populateTaskView(){
        ArrayList<String> taskID = new ArrayList<>();

        for (int i = 1; i < tasks.size()+1; i++) {
            taskID.add(String.valueOf(i));
        }

        ObservableList<String> taskNames = FXCollections.observableArrayList(taskID);
       // taskList = new ListView<>(taskNames);
        taskList.setItems(taskNames);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //text.setText("Question 1");
        Platform.runLater(()-> {
            System.out.println(id);
            tasks = database.getTasks(id);
            populateTaskView();
            questionId.setStyle("-fx-font: 24 arial;");
            questionId.setText(tasks.get(0).getQuestionText());

        });
    }
}
