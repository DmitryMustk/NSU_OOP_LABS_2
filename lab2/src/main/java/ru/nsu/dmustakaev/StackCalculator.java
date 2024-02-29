package ru.nsu.dmustakaev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackCalculator {
    private Stack<Double> stack;
    private Map<String, Double> variables;

    public StackCalculator() {
        stack = new Stack<>();
        variables = new HashMap<>();
    }

    public void processCommands(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 0) {
            return; // Empty line or invalid command
        }

        switch (parts[0]) {
            case "POP":
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    System.out.println("Error: Stack is empty");
                }
                break;
            case "PUSH":
                if (parts.length < 2) {
                    System.out.println("Error: PUSH command requires an argument");
                    break;
                }
                Double value = getValue(parts[1]);
                if (value != null) {
                    stack.push(value);
                } else {
                    System.out.println("Error: Variable '" + parts[1] + "' is not defined");
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (stack.size() < 2) {
                    System.out.println("Error: Insufficient operands for arithmetic operation");
                    break;
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (parts[0]) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        if (b != 0) {
                            stack.push(a / b);
                        } else {
                            System.out.println("Error: Division by zero");
                        }
                        break;
                }
                break;
            case "SQRT":
                if (!stack.isEmpty()) {
                    double x = stack.pop();
                    if (x >= 0) {
                        stack.push(Math.sqrt(x));
                    } else {
                        System.out.println("Error: Cannot take square root of a negative number");
                    }
                } else {
                    System.out.println("Error: Stack is empty");
                }
                break;
            case "PRINT":
                if (!stack.isEmpty()) {
                    System.out.println(stack.peek());
                } else {
                    System.out.println("Error: Stack is empty");
                }
                break;
            case "DEFINE":
                if (parts.length < 3) {
                    System.out.println("Error: DEFINE command requires two arguments");
                    break;
                }
                try {
                    double val = Double.parseDouble(parts[2]);
                    variables.put(parts[1], val);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid value for variable '" + parts[1] + "'");
                }
                break;
            default:
                // Ignore comments or unrecognized commands
                if (!parts[0].startsWith("#")) {
                    System.out.println("Error: Unrecognized command '" + parts[0] + "'");
                }
        }
    }

    private Double getValue(String variableName) {
        if (variables.containsKey(variableName)) {
            return variables.get(variableName);
        }
        try {
            return Double.parseDouble(variableName);
        } catch (NumberFormatException e) {
            return null; // Not a number nor defined variable
        }
    }
}
