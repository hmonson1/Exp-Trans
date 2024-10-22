import java.io.FileNotFoundException;

//Harlea Monson, Assignment 4, Spring 24
//Main.java is a driver class that tests ExpressionEvaulator.java 4 times.
//Writing to output files called input#_output.txt


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ExpressionEvaluator ee = new ExpressionEvaluator();
        ee.convertToPostfix("input.txt");

        ee.convertToPostfix("input2.txt");

        ee.convertToPostfix("input3.txt");

        ee.convertToPostfix("input4.txt");

        ee.convertToPostfix("input5.txt");

    }
}