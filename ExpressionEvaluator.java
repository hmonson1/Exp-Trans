import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

//Harlea Monson, Assignment 4, Spring 24
//ExpressionEvaluator.java is a file that reads an expression from an input file,
//converts it to postfix notation evaluates, and prints the results to an output file.

public class ExpressionEvaluator {
    String operand = "[0-9]"; // Regular expression defining operands as digits


    // Method to determine operator precedence
    static int hierarchy(char c) {
        if (c == '^')
            return 3;
        else if (c == '/' || c == '*')
            return 2;
        else if (c == '+' || c == '-')
            return 1;
        else
            return -1; // Return -1 for unknown operators
    }

    // Method to determine operator associativity
    char associativity(char c) {
        if (c == '^')
            return 'R'; // Right associativity for exponentiation
        return 'L'; // Left associativity for other operators
    }

    // Method to convert infix expression to postfix notation
    public void convertToPostfix(String input) throws FileNotFoundException {
        StringBuilder postfix = new StringBuilder(); // StringBuilder to build postfix expression
        Stack<Character> stack = new Stack<Character>(); // Stack to handle operators
        String[] filenameSubstring = input.split("\\.");

        File myObj = new File(input);
        Scanner myReader = new Scanner(myObj);
        String s = myReader.nextLine(); // Read the first line from the file
        myReader.close();

        s = s.replaceAll("\\s+",""); // Remove whitespace characters

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);

            if (String.valueOf(current).matches(operand)) {
                postfix.append(current); // Append operands directly to postfix expression
            } else if (current == '(') {
                stack.push(current); // Push opening parentheses to stack
            } else if (current == ')') {
                // Pop operators from stack until reaching the corresponding opening parenthesis
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()); // Append operators to postfix expression
                }
                stack.pop(); // Discard the '(' from the stack
            } else {
                // Pop operators from stack based on precedence and associativity rules
                while (!stack.isEmpty() && (hierarchy(current) < hierarchy(stack.peek()) || hierarchy(current) == hierarchy(stack.peek()) && associativity(current) == 'L')) {
                    postfix.append(stack.pop()); // Append operators to postfix expression
                }
                stack.push(current); // Push current operator to the stack
            }
        }
        // Append remaining operators from stack to postfix expression
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        try {
            FileWriter myWriter = new FileWriter(filenameSubstring[0] + "_output.txt"); // Create FileWriter for output file
            myWriter.write("Infix expression: " + s + "\n"); // Write input infix expression to file
            myWriter.write("Postfix expression: " + postfix.toString() + "\n"); // Write postfix expression to file
            myWriter.write("Evaluation: " + evaluate(postfix.toString()) + "\n"); // Write evaluation result to file
            myWriter.close(); // Close FileWriter
            System.out.println("Successfully wrote to output file");
            System.out.println();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Method to evaluate a postfix expression
    public double evaluate(String postfix) {
        Stack<Double> stack = new Stack<>(); // Stack to store operands

        for (int i = 0; i < postfix.length(); i++) {
            char current = postfix.charAt(i);

            if (String.valueOf(current).matches(operand)) {
                stack.push(Double.valueOf(current - '0')); // Push operand to stack
            } else {
                double val1 = stack.pop();
                double val2 = stack.pop();

                // Perform arithmetic operations based on the operator
                switch (current) {
                    case '^':
                        stack.push(Math.pow(val1, val2));
                        break;
                    case '+':
                        stack.push(val2 + val1);
                        break;
                    case '-':
                        stack.push(val2 - val1);
                        break;
                    case '/':
                        stack.push(val2 / val1);
                        break;
                    case '*':
                        stack.push(val2 * val1);
                        break;
                }
            }
        }
        return stack.pop(); // Return the final result
    }
}
