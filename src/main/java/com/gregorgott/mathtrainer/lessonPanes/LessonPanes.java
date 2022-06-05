package com.gregorgott.mathtrainer.lessonPanes;

import com.gregorgott.mathtrainer.Operator;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Every lesson has its node. These nodes are defined in this class. This class also calculates the correct result of
 * the given question.
 *
 * @author GregorGott
 * @version 0.0.4
 * @since 2022-05-19
 */
public class LessonPanes {
    private final Random random;
    private ArrayList<Operator> operators;
    private double result;

    /**
     * Initialize variables.
     *
     * @since 0.0.1
     */
    public LessonPanes() {
        random = new Random();
        operators = new ArrayList<>();
    }

    /**
     * Returns the result of a question.
     *
     * @return The result of a question as int.
     * @since 0.0.1
     */
    public double getResult() {
        return result;
    }

    /**
     * @return A random operator from the operators ArrayList.
     * @since 0.0.1
     */
    private Operator getRandomOperator() {
        return operators.get(random.nextInt(operators.size()));
    }

    /**
     * Returns a lesson node with a randomly generated question.
     *
     * @param operators The operators that may be used.
     * @param min       The min number.
     * @param max       The max number.
     * @param decimals  If decimal numbers are allowed.
     * @return A node with a randomly generated question.
     * @since 0.0.1
     */
    public Node basicOperationsLesson(ArrayList<Operator> operators, int min, int max, boolean decimals) {
        this.operators = operators;

        // Generate two random numbers
        double number1;
        double number2;

        if (decimals) {
            number1 = Math.round((min + (max - min) * random.nextDouble()) * 100.0) / 100.0;
            number2 = Math.round((min + (max - min) * random.nextDouble()) * 100.0) / 100.0;
        } else {
            number1 = random.nextInt(max - min) + min;
            number2 = random.nextInt(max - min) + min;
        }

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

        return new Label(number1 + " " + operatorAsChar + " " + number2);
    }

    /**
     * Returns a node with a random base and exponent between given numbers
     *
     * @param maxBase     an int which gives the max number for the base.
     * @param maxExponent an int which gives the max number for the exponent.
     * @return a node with randomly generated questions.
     * @since 0.0.3
     */
    public Node exponentiationLesson(int maxBase, int maxExponent) {
        // Random base and exponent
        double base = random.nextInt(maxBase);
        int exponent = random.nextInt((maxExponent + 1) - 1) + 1;

        result = Math.pow(base, exponent);

        return new Label(base + " ^" + exponent);
    }

    /**
     * Returns with a random rectangle between zero and the given number
     *
     * @param max an int which gives the maximum side length.
     * @param min an int which gives the minimum side length.
     * @since 0.0.4
     */
    public Node rectangleAreaLesson(int min, int max) {
        // side1 for rectangle width
        // side2 for rectangle height
        int side1 = random.nextInt(max - min) + min;
        int side2 = random.nextInt(max - min) + min;

        result = side1 * side2;

        // Change rectangle dimensions in dependence of the side lengths
        Rectangle rectangle;
        if (side1 < side2) {
            rectangle = new Rectangle(30, 60);
        } else {
            rectangle = new Rectangle(60, 30);
        }

        rectangle.setFill(Paint.valueOf("#63A4FF"));

        // side1Label for rectangle width
        Label side1Label = new Label(String.valueOf(side1));
        // side2Label for rectangle height
        Label side2Label = new Label(String.valueOf(side2));

        VBox vBox = new VBox(rectangle, side1Label);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(vBox, side2Label);
        hBox.setSpacing(10);
        hBox.setMaxHeight(50);

        return hBox;
    }
}