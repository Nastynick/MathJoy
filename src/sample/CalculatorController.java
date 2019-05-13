package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController {


    @FXML
    private Label screenLabel;
    private long number1 = 0;
    private String operator = "";
    private boolean start = true;
    private Model model = new Model();


    @FXML
    public void processNumbers (ActionEvent event) {
        if (start) {
            screenLabel.setText("");
            start = false;
        }
        String value = ((Button)event.getSource()).getText();
        screenLabel.setText(screenLabel.getText()+ value);

    }

    @FXML
    public void procssOperators (ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        if (!value.equals("=")) {
            if (!operator.isEmpty()) {
                return;
            }
            operator = value;
            number1 = Long.parseLong(screenLabel.getText());
            screenLabel.setText("");

        } else {
            if (operator.isEmpty()) {
                return;
            }
                long number2 = Long.parseLong(screenLabel.getText());
                float output = model.Calculate(number1, number2, operator);
                screenLabel.setText(String.valueOf(output));
                operator = "";
                start = true;

            }
        }

    }

