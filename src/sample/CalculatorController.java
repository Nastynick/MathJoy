package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {


    @FXML
    private TextField screenLabel;
    @FXML
    private Label memoryLabel;

    private double number1 = 0;
    private double number2 = 0;
    private double number3 = 0;
    private String operator = "";
    private boolean start = true;
    private Model model = new Model();


    @FXML
    public void processNumbers(ActionEvent event) { //method for invoking number presses
        if (start) { //a flag to check whether a number has been pressed
            screenLabel.setText("");
            start = false;
        }
        String value = ((Button) event.getSource()).getText(); //gets the String value of a number pressed
        screenLabel.setText(screenLabel.getText() + value); //sets the string value

    }

    @FXML
    public void procssOperators(ActionEvent event) { // This action checks operations
        try {
            String value = ((Button) event.getSource()).getText(); //this action gets the String value from a button
            if (!value.equals("=")) { //checks if the operator is =
                if (!operator.isEmpty()) { //checks if the the operator is empty or not
                    return;
                }
                operator = value; //assigns the operator
                number1 = Double.parseDouble(screenLabel.getText()); //stores the first number in memory
                screenLabel.setText(""); //resets the label

            } else {
                if (operator.isEmpty()) { //checks if the operator is empty or not, if it's empty it does nothing
                    return;
                }
                number2 = Double.parseDouble(screenLabel.getText()); //sets the second number
                double output = model.Calculate(number1, number2, operator); //method for calculation
                screenLabel.setText(String.valueOf(output)); // puts the combined value
                operator = ""; //defaults the operator
                start = true; // readds the check so one can do newer calculations

            }
        } catch (Exception e) {
            e.printStackTrace();
            screenLabel.setText("Error in the operation"); //In the events of an error in when calculating
        }
    }

    @FXML
    void processClearMemory (ActionEvent event) { //this action clears the memory
        number3 = 0;
        memoryLabel.setText("Memory: ");
    }

    @FXML
    void processClear(ActionEvent event) { //this action clears all variables stored in the calculator and defaults them
        number1 = 0;
        number2 = 0;
        number3 = 0;
        screenLabel.setText("");
        memoryLabel.setText("Memory: ");

    }

    @FXML
    void processMemory(ActionEvent event) { //method for storing a number in memory
        try {

            if (number3 == 0) { //checks if the number3 is 0 or not, if it's 0 it will store it in memory
                number3 = Double.parseDouble(screenLabel.getText());
                screenLabel.setText("");
                memoryLabel.setText("Memory: " + number3);
            } else {
                screenLabel.setText(String.valueOf(number3)); //if it's not 0 it will display the number instead
            }

        } catch (Exception e) {
            screenLabel.setText("No value can be assigned to memory"); //in the events of an empty label
        }

    }
}

