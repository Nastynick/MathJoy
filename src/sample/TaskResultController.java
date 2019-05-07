package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskResultController implements Initializable {
    Task task;

    @FXML
    private TextArea textArea1;

    @FXML
    private TextArea textArea2;

    @FXML
    private TextArea textArea3;

    public  void setTasks(Task sendTask){
        task = sendTask;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(()->{
            textArea1.setText(task.getQuestionText());
            textArea2.setText("Your answer: "+task.getYourAnswer());
            textArea3.setText("Real answer: "+task.getAnswer());
        });


    }
}
