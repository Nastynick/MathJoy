package sample;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskWindowController implements Initializable {
    private SwitchScene switchScene = new SwitchScene();
    private ArrayList<Task> tasks = new ArrayList<>();
    Database database = new Database();
    private ObservableList<String> taskNames;
    private boolean doContinue = true;
    int id;
    private int i = 0;


    @FXML
    private ListView<String> taskList;

    @FXML
    private TextField answerBox;

    @FXML
    private TextArea questionId;

    @FXML
    private Button nextButton;

    @FXML
    private Button fontSizePlusButton;

    @FXML
    private Button fontSizeMinusButton;

    @FXML
    private Button textToSpeechButton;

    private int fontsize = 12;


    @FXML
    void onTextToSpeechButtonPressed(ActionEvent event) {
        Voice voice;
        VoiceManager vm=VoiceManager.getInstance();
        voice=vm.getVoice("kevin");
        voice.allocate();
        voice.speak(questionId.getText());

    }

    @FXML
    void fontSizeMinusButtonPressed(ActionEvent event) {
        if (fontsize <= 8) return;

        fontsize = fontsize - 2;
        questionId.setStyle("-fx-font: " + fontsize + " arial;");
        answerBox.setStyle("-fx-font: " + fontsize + " arial;");
    }

    @FXML
    void fontSizePlusButtonPressed(ActionEvent event) {
        if (fontsize >= 72) return;

        fontsize = fontsize + 2;
        questionId.setStyle("-fx-font: " + fontsize + " arial;");
        answerBox.setStyle("-fx-font: " + fontsize + " arial;");
    }

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
        answerBox.setText("");
        answerBox.requestFocus();

        if (doContinue) {
            i++; // increments the counter.
            taskNames.set(i-1, String.valueOf(i)); // sets the previous string value in the list to its original number
            taskNames.set(i, i+1 + " <-----"); //Marks current task
            questionId.setText(tasks.get(i).getQuestionText()); //updates the question
            nextButton.setDisable(false); //re-enables button

        } else {
            System.out.println("DONE");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ExerciseResultView.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = loader.load();
                ExerciseResultController ex = loader.getController();
                ex.setTasks(tasks);

                insertAllTaskResultsIntoDatabase();



                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (Exception e){
                e.printStackTrace();
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

    private void insertAllTaskResultsIntoDatabase(){

        int correctAmount = 0;

        for (Task task : tasks){
            if (task.isCorrectAnswer()){
                correctAmount++;
            }
        }

        String correctAmountString = correctAmount +"/"+ tasks.size();

        try {
            database.insertMathResult(Database.getUsername(), id, correctAmountString);
            System.out.println("Username: "+Database.getUsername() +" ExcerciseId: "+ id+" Result: "+ correctAmountString);

        } catch (Exception e) {
            e.printStackTrace();
        }



       /* for (Task t : tasks) {
            try {
                //database.insertMathResult(Database.getUsername(), id, t.getYourAnswer());
                System.out.println("Username: "+Database.getUsername() +" ExcerciseId: "+ id+" Result"+ t.getYourAnswer());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        */
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //text.setText("Question 1");
        Platform.runLater(()-> {
            questionId.setStyle("-fx-font: 12 arial;");
            answerBox.setStyle("-fx-font: 12 arial;");
            System.out.println(id);
            tasks = database.getTasks(id);

            populateTaskView();
            questionId.setText(tasks.get(i).getQuestionText());
            taskNames.set(i, taskNames.get(i) + " <-----");
            if (tasks.size() <= 1) {
                nextButton.setText("Finish");
                doContinue = false;
            }
            answerBox.requestFocus();

        });
    }
}
