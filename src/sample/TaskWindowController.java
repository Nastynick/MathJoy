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
import javafx.scene.control.Button;
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
    ObservableList<String> taskNames;
    boolean doContinue = true;
    int id;
    int i = 0;

    @FXML
    private ListView<String> taskList;

    @FXML
    private TextArea answerBox;

    @FXML
    private Text questionId;

    @FXML
    private Button nextButton;


    @FXML
    void backPressed(ActionEvent event) {


       switchScene.switchScene(event, "ExSelectionView.fxml");

    }

    @FXML
    void nextPressed(ActionEvent event) {


        nextButton.setDisable(true); //disable button to avoid ghosting
        String answer = answerBox.getText(); //gets answer from textbox
        Task task = tasks.get(i); // creates a task object to modify
        boolean correct = task.getAnswer().equals(answer); //saves if it was correct for final results
        task.setCorrectAnswer(correct); //sets the boolean from above
        task.setYourAnswer(answer);
        System.out.println(task.isCorrectAnswer()); // DEBUG PRINT
        tasks.set(i, task);

        if (doContinue) {
            i++; // increments the counter.
            taskNames.set(i-1, String.valueOf(i)); // sets the previous string value in the list to its original number
            taskNames.set(i, i+1 + " <-----"); //Marks current task
            questionId.setText(tasks.get(i).getQuestionText()); //updates the question
            nextButton.setDisable(false); //re-enables button

        } else {
            System.out.println("DONE"); // TODO: replace with changing scene
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ExerciseResultView.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = loader.load();
                ExerciseResultController ex = loader.getController();
                ex.setTasks(tasks);
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (Exception e){
                System.out.println(e);
            }

        }

        if (i+1 >= tasks.size()) { //prepares the next press as the final to avoid OOB.
            nextButton.setText("Finish");
            doContinue = false;
        }


    }


   // private Text text;

    public void setData(int idx){
        id = idx;
    }
    private void populateTaskView(){
        ArrayList<String> taskID = new ArrayList<>();

        for (int i = 1; i < tasks.size()+1; i++) {
            taskID.add(String.valueOf(i));
        }

        taskNames = FXCollections.observableArrayList(taskID);
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
            questionId.setText(tasks.get(i).getQuestionText());
            taskNames.set(i, taskNames.get(i) + " <-----");
            if (tasks.size() <= 1) {
                nextButton.setText("Finish");
                doContinue = false;
            }
        });
    }
}
