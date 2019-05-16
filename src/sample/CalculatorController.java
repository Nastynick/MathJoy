package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController {


    @FXML
    private Label screenLabel;
    @FXML
    private Label memoryLabel;
    private double number1 = 0;
    private double number2 = 0;
    private double number3 = 0;
    private String operator = "";
    private boolean start = true;
    private Model model = new Model();


    @FXML
    public void processNumbers(ActionEvent event) {
        if (start) {
            screenLabel.setText("");
            start = false;
        }
        String value = ((Button) event.getSource()).getText();
        screenLabel.setText(screenLabel.getText() + value);

    }

    @FXML
    public void procssOperators(ActionEvent event) {
        try {
            String value = ((Button) event.getSource()).getText();
            if (!value.equals("=")) {
                if (!operator.isEmpty()) {
                    return;
                }
                operator = value;
                number1 = Double.parseDouble(screenLabel.getText());
                screenLabel.setText("");

            } else {
                if (operator.isEmpty()) {
                    return;
                }
                number2 = Double.parseDouble(screenLabel.getText());
                double output = model.Calculate(number1, number2, operator);
                screenLabel.setText(String.valueOf(output));
                operator = "";
                start = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
            screenLabel.setText("Error in the operation");
        }
    }

    @FXML
    void processClearMemory (ActionEvent event) {
        number3 = 0;
        memoryLabel.setText("Memory: ");
        screenLabel.setText("");
    }

    @FXML
    void processClear(ActionEvent event) {
        number1 = 0;
        number2 = 0;
        number3 = 0;
        screenLabel.setText("");
        memoryLabel.setText("Memory: ");

    }

    @FXML
    void processMemory(ActionEvent event) {
        try {

            if (number3 == 0) {
                number3 = Double.parseDouble(screenLabel.getText());
                screenLabel.setText("");
                memoryLabel.setText("Memory: " + number3);
            } else {
                screenLabel.setText(String.valueOf(number3));
            }

        } catch (Exception e) {
            screenLabel.setText("No value can be assigned to memory");
        }

    }
}

