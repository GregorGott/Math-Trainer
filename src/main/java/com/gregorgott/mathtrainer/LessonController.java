package com.gregorgott.mathtrainer;

import com.gregorgott.mathtrainer.lessonPanes.LessonPanes;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * The LessonController controls the main round system. It displays the question in the center, a progress bar on the top
 * and a next button in the bottom.
 *
 * @author GregorGott
 * @version 0.0.1
 * @since 2022-05-01
 */
public class LessonController {
    private final LessonPanes lessonPanes;
    private final TextField textField;
    private final Button checkButton;
    @FXML
    private BorderPane borderPane;
    private Lessons lessons;
    private int numberOfRounds;
    private int max;
    private int min;
    private int points;
    private ArrayList<Operator> operators;

    /**
     * Constructor to initialize variables and set an action for the check button to check the input.
     */
    public LessonController() {
        lessonPanes = new LessonPanes();
        textField = new TextField();

        checkButton = new Button("Check");
        checkButton.setOnAction(event -> checkInput());
    }

    /**
     * Set the selected lesson to show a correct question.
     *
     * @param lessons The selected lesson.
     */
    public void setLesson(Lessons lessons) {
        this.lessons = lessons;
    }

    /**
     * Set number of rounds. This variable controls after how many rounds the lesson ends.
     *
     * @param numberOfRounds The number of selected rounds.
     */
    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    /**
     * Set max and min number (not of the solution, e.g. min:0, max:10 -> 10 + 5 = 15).
     *
     * @param max The max number.
     * @param min The min number.
     */
    public void setNumberRange(int max, int min) {
        this.max = max;
        this.min = min;
    }

    /**
     * Set operators.
     *
     * @param operators An ArrayList with all usable operators.
     */
    public void setOperators(ArrayList<Operator> operators) {
        this.operators = operators;
    }

    /**
     * Load the center of the border pane with a correct lesson question, a text field and a button to check
     * the entered input.
     */
    public void loadLesson() {
        // The correct lesson question as node
        Node lessonNode = null;

        switch (lessons) {
            case BASIC_OPERATIONS -> lessonNode = lessonPanes.basicOperationsLesson(operators, min, max);
        }

        HBox hBox = new HBox(lessonNode, textField, checkButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        borderPane.setCenter(hBox);
    }

    /**
     * Check if the input is correct. If it is, add a point.
     */
    private void checkInput() {
        if (Integer.parseInt(textField.getText()) == lessonPanes.getResult()) {
            points++;
            System.out.println("Right");
        } else {
            System.out.println("False");
        }
    }

    /**
     * This method is called if the 'Next' button in the Scene is clicked. It resets the textField and loads a
     * new question in the border pane.
     */
    public void nextQuestion() {
        textField.setText("");
        loadLesson();
    }
}