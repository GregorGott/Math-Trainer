package com.gregorgott.mathtrainer.lessonPanes;

import com.gregorgott.mathtrainer.Operator;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Random;

/**
 * Every lesson has his own node. These nodes are defined in this class.
 * This class also calculates the result, that the class which initialice this class only needs to compare a value with
 * the return value of getResult().
 *
 * @author GregorGott
 * @version 0.0.1
 * @since 2022-05-01
 */
public class LessonPanes {

    private final Random random;
    private ArrayList<Operator> operators;
    private int result;

    /**
     * Initialize variables.
     */
    public LessonPanes() {
        random = new Random();
        operators = new ArrayList<>();
    }

    /**
     * Returns the result of a question.
     *
     * @return The result of a question as int.
     */
    public int getResult() {
        return result;
    }

    /**
     * @return A random operator from the operators ArrayList.
     */
    private Operator getRandomOperator() {
        return operators.get(random.nextInt(operators.size()));
    }

    /**
     * Returns a lesson node with a random generated question.
     *
     * @param operators The operators that may be used.
     * @param min       The min number.
     * @param max       The max number.
     * @return A node with a random generated question.
     */
    public Node basicOperationsLesson(ArrayList<Operator> operators, int min, int max) {
        this.operators = operators;

        // Generate two random numbers
        int number1 = random.nextInt(max - min) + min;
        int number2 = random.nextInt(max - min) + min;

        // Get a random operator
        Operator operator = getRandomOperator();
        char operatorAsChar = 0;

        // Set a char for the operator for the output label and calculate the result
        switch (operator) {
            case ADDITION -> {
                operatorAsChar = '+';
                result = number1 + number2;
            }

            case SUBTRACTION -> {
                operatorAsChar = '-';
                result = number1 - number2;
            }

            case MULTIPLICATION -> {
                operatorAsChar = '*';
                result = number1 * number2;
            }

            case DIVISION -> {
                operatorAsChar = ':';
                result = number1 / number2;
            }
        }

        return new Label(number1 + " " + operatorAsChar + " " + number2 + " =");
    }
}