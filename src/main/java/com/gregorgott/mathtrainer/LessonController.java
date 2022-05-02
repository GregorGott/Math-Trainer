package com.gregorgott.mathtrainer;

import com.gregorgott.mathtrainer.lessonPanes.LessonPanes;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The LessonController controls the main round system. It displays the question in the centre, a progress bar on the top
 * and a next button at the bottom.
 *
 * @author GregorGott
 * @version 0.0.2
 * @since 2022-05-02
 */
public class LessonController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label roundLabel;
    @FXML
    private ProgressBar progressBar;

    private HBox lessonHBox;
    private final LessonPanes lessonPanes;
    private final TextField textField;
    private final Button checkButton;

    private Lessons lessons;

    private int numberOfRounds;
    private int roundCounter;
    private int max;
    private int min;
    private int points;

    private boolean questionAnswered;

    private ArrayList<Operator> operators;

    /**
     * Constructor to initialize variables and set an action for the check button to check the input.
     */
    public LessonController() {
        lessonPanes = new LessonPanes();
        textField = new TextField();

        questionAnswered = false;
        roundCounter = 1;

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
     * Load the centre of the border pane with a correct lesson question, a text field and a button to check
     * the entered input.
     */
    public void loadLesson() {
        setRoundLabel();
        setProgressBar();
        // The correct lesson question as node
        Node lessonNode = null;

        switch (lessons) {
            case BASIC_OPERATIONS -> lessonNode = lessonPanes.basicOperationsLesson(operators, min, max);
        }

        lessonHBox = new HBox(lessonNode, textField, checkButton);
        lessonHBox.setAlignment(Pos.CENTER);
        lessonHBox.setSpacing(15);

        borderPane.setCenter(lessonHBox);
    }

    /**
     * Check if the input is correct. If it is, add a point.
     */
    private void checkInput() {
        // Check if the text field has content
        if (!textField.getText().isEmpty()) {
            questionAnswered = true;

            // Get the text of the text field as int
            // Check if the input is the correct result
            if (Double.parseDouble(textField.getText()) == lessonPanes.getResult()) {
                points++;
                correctAnswer();
            } else {
                wrongAnswer();
            }
        }
    }

    /**
     * Is called when the answer from the user is correct. Show a green checkbox and disable input methods.
     */
    private void correctAnswer() {
        // Add a green checkbox beside the question
        Image image = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("images/check_box_green.png")));

        ImageView imageView = new ImageView(image);

        lessonHBox.getChildren().add(imageView);

        disableInput();
    }

    /**
     * Is called when the answer from the user is wrong. Show a red x and disable input methods.
     */
    private void wrongAnswer() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/disable_x.png")));

        ImageView imageView = new ImageView(image);

        lessonHBox.getChildren().add(imageView);

        disableInput();
    }

    /**
     * Disable all input methods. Is called after user input.
     */
    private void disableInput() {
        textField.setDisable(true);
        checkButton.setDisable(true);
    }

    /**
     * Update the round label at the top of the Scene.
     */
    private void setRoundLabel() {
        roundLabel.setText("Round " + roundCounter + " of " + numberOfRounds);
    }

    /**
     * Update the progress bar which shows the round progress.
     */
    private void setProgressBar() {
        progressBar.setProgress((double) roundCounter / numberOfRounds);
    }

    /**
     * This method is called if the 'Next' button in the Scene is clicked. It resets the textField and loads a
     * new question in the border pane.
     */
    public void nextQuestion() {
        if (questionAnswered) {
            System.out.println(numberOfRounds);
            System.out.println(roundCounter);
            if (numberOfRounds == roundCounter) {
                System.out.println("Finished");
            } else {
                roundCounter++;
                setRoundLabel();
                setProgressBar();

                textField.setText("");
                loadLesson();

                questionAnswered = false;
                textField.setDisable(false);
                checkButton.setDisable(false);
            }
        }
    }
}