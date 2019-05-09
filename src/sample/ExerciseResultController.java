package sample;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExerciseResultController implements Initializable {

    ArrayList<Task> tasks = new ArrayList<>();

    @FXML
    private TableView<Task> taskResults;

    @FXML
    private TableColumn<Task, Integer> taskColumn;

    @FXML
    private TableColumn<Task, Boolean> resultColumn;

    @FXML
    private ImageView imageViiew;

    @FXML
    private Text text;

    public void setTasks(ArrayList<Task> tasksArrayList){
        tasks = tasksArrayList;
    }

    @FXML
    void backToMainMenuPressed(ActionEvent event) {
        SwitchScene switchScene = new SwitchScene();
        switchScene.switchScene(event, "MenuView.fxml");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater( ()->{

            taskResults.getItems().addAll(tasks);

        taskColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Task, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getQuestionID());
            }
        });

        resultColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, Boolean>, ObservableValue<Boolean>>() {
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Task, Boolean> p) {
                return new ReadOnlyObjectWrapper(p.getValue().isCorrectAnswer());
            }
        });

        taskResults.setRowFactory( tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Task rowData = row.getItem();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskResultView.fxml"));
                        Parent root = loader.load();

                        TaskResultController ex = loader.getController();
                        ex.setTasks(rowData);

                        Stage stage = new Stage();
                        stage.setTitle("Task details");
                        stage.setScene(new Scene(root));
                        stage.show();
                        // Hide this current window (if this is what you want)
                       // ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            });
            return row ;
        });

        int correctAmount = 0;

        for (Task task : tasks){
            if (task.isCorrectAnswer()){
                correctAmount++;
            }
        }

        text.setText("Great attempt!\nYou got:\n"+correctAmount+" / "+ tasks.size());

        });





    }
}
