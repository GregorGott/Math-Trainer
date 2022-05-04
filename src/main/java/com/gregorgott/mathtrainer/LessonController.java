package com.gregorgott.mathtrainer;

import com.gregorgott.mathtrainer.lessonPanes.LessonPanes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The LessonController controls the main round system. It displays the question in the centre, a progress bar on the top
 * and a next button at the bottom.
 *
 * @author GregorGott
 * @version 0.0.4
 * @since 2022-05-04
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
        points = 0;

        checkButton = new Button("Check");
        checkButton.setOnAction(event -> checkInput());
    }

    public boolean isOperatorGiven() {
        if (lessons == Lessons.BASIC_OPERATIONS) {
            return operators.size() > 0;
        } else {
            return false;
        }
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
    public void nextQuestion(ActionEvent event) throws IOException {
        if (questionAnswered) {
            if (numberOfRounds == roundCounter) {
                showResults(event);
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

    /**
     * Show results with points and mistakes in the result Scene.
     * @param event         An ActionEvent to get the Scene.
     * @throws IOException  Exception when FXML file is not loadable.
     * @since 0.0.4
     */
    private void showResults(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("result-scene.fxml"));
        Parent root = fxmlLoader.load();

        ResultController resultController = fxmlLoader.getController();
        resultController.setPoints(points, numberOfRounds - points);

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Cancel the lesson and show the settings Scene of the lesson again.
     * @param event Get an ActionEvent of the Button to switch the Scene.
     */
    public void cancelLesson(ActionEvent event) {
        try {
            LessonSettingsController lessonSettingsController;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lesson-settings-scene.fxml"));
            Parent root = fxmlLoader.load();

            lessonSettingsController = fxmlLoader.getController();
            lessonSettingsController.setLesson(lessons);

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}