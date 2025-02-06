package lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kalkulator {

    private static JTextField resultField;
    private static int firstNumber = 0;
    private static int secondNumber = 0;
    private static String operator = "";
    private static boolean isOperatorClicked = false;
    private static boolean isEqualPressedLast = false;
    private static boolean devByZero = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setResizable(false);

        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        resultField = new JTextField("0");
        resultField.setEditable(false);
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setFont(new Font("Arial", Font.BOLD, 24));

        String[] buttons = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "0", "=", "C", "/"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new CalculatorActionListener());
            panel.add(button);
        }

        frame.setLayout(new BorderLayout());
        frame.add(resultField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    static class CalculatorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            System.out.println("[ActionListener] Button = " + command);

            if (Character.isDigit(command.charAt(0))) {
                System.out.println("DIGIT HANDLE, isEqualPressedLast =  "+isEqualPressedLast+", isOperatorClicked =  "+isOperatorClicked +", firstNumber = "+firstNumber+", second Number = "+secondNumber);
                handleDigit(command);
            } else if (command.equals("C")) {
            	System.out.println("CLEAR HANDLE, isEqualPressedLast =  "+isEqualPressedLast+", isOperatorClicked =  "+isOperatorClicked +", firstNumber = "+firstNumber+", second Number = "+secondNumber);
                resetCalculator();
            } else if (command.equals("=")) {
            	System.out.println("EQUAL HANDLE, isEqualPressedLast =  "+isEqualPressedLast+", isOperatorClicked =  "+isOperatorClicked +", firstNumber = "+firstNumber+", second Number = "+secondNumber);
                handleEqual();
            } else {
            	System.out.println("OPERATOR HANDLE, isEqualPressedLast =  "+isEqualPressedLast+", isOperatorClicked =  "+isOperatorClicked +", firstNumber = "+firstNumber+", second Number = "+secondNumber);
                handleOperator(command);
            }
        }

        private void handleDigit(String command) {
            System.out.println("[ActionListener] Button = " + command);
            if (devByZero == true) {
            	devByZero = false;
            	resetCalculator();
            }
            if (isEqualPressedLast) {
                resetCalculator();
                System.out.println("RESET "+isEqualPressedLast+" "+isOperatorClicked);
                                resultField.setText(command);
                isEqualPressedLast = false;
            } else if (isOperatorClicked) {
                System.out.println("OPERATOR "+isEqualPressedLast+" "+isOperatorClicked);
                resultField.setText(command);
                isOperatorClicked = false;
            } else {
                System.out.println("inne "+isEqualPressedLast+" "+isOperatorClicked);
                                               resultField.setText(resultField.getText().equals("0") ? command : resultField.getText() + command);
            }
        }

        private void resetCalculator() {
            resultField.setText("0");
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
            isOperatorClicked = false;
            isEqualPressedLast = false;
        }

        private void handleEqual() {
            if (!operator.isEmpty() && !resultField.getText().equals("Error: Division by zero")) {
                if (!isEqualPressedLast) {
                    secondNumber = Integer.parseInt(resultField.getText());
                }
                if (operator.equals("/") && secondNumber == 0) {   //Sprawdzanie czy występuje dzielenie przez 0
                    resultField.setText("Error: Division by zero");
                    devByZero = true;
                    return;
                }
                int result = performOperation(firstNumber, secondNumber, operator);
                resultField.setText(String.valueOf(result));
                firstNumber = result;
                isEqualPressedLast = true;
                isOperatorClicked = false;
            }
            else if (operator.isEmpty()) {
            	isEqualPressedLast = true;
            	isOperatorClicked = false;
            }
        }

        private void handleOperator(String command) {
            if (!isOperatorClicked && !isEqualPressedLast) {
                if (!operator.isEmpty()) {
                    secondNumber = Integer.parseInt(resultField.getText());
                    if (operator.equals("/") && secondNumber == 0) {
                        resultField.setText("Error: Division by zero");
                        devByZero = true;
                        return;
                    }
                    int result = performOperation(firstNumber, secondNumber, operator);
                    resultField.setText(String.valueOf(result));
                    firstNumber = result;
                } else {
                    firstNumber = Integer.parseInt(resultField.getText());
                }
            }
            operator = command;
            isOperatorClicked = true;
            isEqualPressedLast = false;
        }

        private int performOperation(int firstNumber, int secondNumber, String operator) {
            switch (operator) {
                case "+":
                    return firstNumber + secondNumber;
                case "-":
                    return firstNumber - secondNumber;
                case "*":
                    return firstNumber * secondNumber;
                case "/":
                    if (secondNumber == 0) {
                        resultField.setText("Error: Division by zero");
                        devByZero = true;
                        return firstNumber;
                    }
                    return firstNumber / secondNumber;
                default:
                    return firstNumber;
            }
        }
    }
}
