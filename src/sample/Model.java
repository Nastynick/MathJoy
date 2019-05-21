package sample;

public class Model { //class for calculation
    public double Calculate(double number1, double number2, String operator) { //The method for calculation

        switch (operator) { //Switch check to see what the operator is

            case "+": { //addition
                return number1 + number2;
            }
            case "-": { //subtraction
                return number1 - number2;
            }
            case "*": { //multiplication
                return number1 * number2;
            }
            case "/": { //division
                if (number2 == 0) { //checks if a division by 0 is happening
                    return 0;
                } else return number1 / number2;
            }
            default: { //in the events of operator being unspecified
                return 0;
            }
        }

    }
}
