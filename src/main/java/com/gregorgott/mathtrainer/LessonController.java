package com.gregorgott.mathtrainer;

import com.gregorgott.mathtrainer.lessonPanes.LessonPanes;
import com.gregorgott.mdialogwindows.MAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

/**
 * The LessonController controls the main round system. It displays the question in the centre, a progress bar on the top
 * and a next button at the bottom.
 *
 * @author GregorGott
 * @version 0.0.11
 * @since 2022-06-22
 */
public class LessonController {
    private final LessonPanes lessonPanes;
    private final TextField textField;
    private final Button checkButton;
    private LocalTime timerTimer;
    private TimerTask timerTask;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label roundLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private Label lessonNameLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button nextButton;
    private HBox lessonHBox;
    private Lessons lessons;
    private int numberOfRounds;
    private int roundCounter;
    private int max;
    private int min;
    private int exponent;
    private int points;
    private int countdownSeconds;
    private boolean questionAnswered;
    private boolean decimals;
    private boolean countdownGame;

    private ArrayList<Operator> operators;

    /**
     * Constructor to initialize variables and set an action for the check button to check the input.
     *
     * @since 0.0.1
     */
    public LessonController() {
        lessonPanes = new LessonPanes();
        textField = new TextField();
        timerTimer = LocalTime.MIN;

        roundCounter = 1;
        points = 0;

        checkButton = new Button("Check");
        checkButton.setOnAction(event -> checkInput());
    }

    /**
     * Set the selected lesson to show a correct question.
     *
     * @param lessons The selected lesson.
     * @since 0.0.1
     */
    public void setLesson(Lessons lessons) {
        this.lessons = lessons;
    }

    /**
     * Set number of rounds. This variable controls after how many rounds the lesson ends.
     *
     * @param numberOfRounds The number of selected rounds.
     * @since 0.0.1
     */
    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    /**
     * Set max and min number (not of the solution, e.g. min:0, max:10 -> 10 + 5 = 15).
     *
     * @param max The max number.
     * @param min The min number.
     * @since 0.0.1
     */
    public void setNumberRange(int max, int min) {
        this.max = max;
        this.min = min;
    }

    /**
     * The operators addition, subtraction, multiplication and division can be overhanded by this method.
     *
     * @param operators An ArrayList with all usable operators.
     * @since 0.0.1
     */
    public void setOperators(ArrayList<Operator> operators) {
        this.operators = operators;
    }

    /**
     * Set if decimal numbers are allowed.
     *
     * @param b A boolean if decimal numbers are allowed.
     * @since 0.0.6
     */
    public void setDecimals(boolean b) {
        this.decimals = b;
    }

    /**
     * Set the largest number for the exponent;
     *
     * @param exponent The largest number for the exponent is int.
     * @since 0.0.8
     */
    public void setMaxExponent(int exponent) {
        this.exponent = exponent;
    }

    /**
     * Sets if the user enabled countdown game and how much time the user has for each question.
     *
     * @param b a boolean if the game uses the countdown mode.
     * @since 0.0.11
     */
    public void setCountdownGame(boolean b, int seconds) {
        this.countdownGame = b;
        this.countdownSeconds = seconds;
    }

    /**
     * Load the centre of the border pane with a correct lesson question, a text field and a button to check
     * the entered input.
     *
     * @since 0.0.1
     */
    public void loadLesson() {
        setRoundLabel();
        setProgressBar();
        startTimer();

        questionAnswered = false;
        nextButton.setDisable(true);

        // The correct lesson question as node
        Node lessonNode = null;

        switch (lessons) {
            case BASIC_OPERATIONS -> {
                lessonNode = lessonPanes.basicOperationsLesson(operators, min, max, decimals);
                lessonNameLabel.setText("Calculate Basic Operations");
            }
            case EXPONENTIATION -> {
                lessonNode = lessonPanes.exponentiationLesson(max, exponent);
                lessonNameLabel.setText("Calculate Exponentiation");
            }
            case RECTANGLE_AREA -> {
                lessonNode = lessonPanes.rectangleAreaLesson(min, max);
                lessonNameLabel.setText("Calculate Rectangle Area");
            }
        }

        lessonHBox = new HBox(lessonNode, new Label("="), textField, checkButton);
        lessonHBox.setAlignment(Pos.CENTER);
        lessonHBox.setSpacing(15);

        borderPane.setCenter(lessonHBox);
    }

    /**
     * If the <code>countdownGame</code> is true, a number in the top of the Stage counts down until null
     * is reached.
     * Else a timer at the top of the Scene with seconds, minutes and hours is showing.
     *
     * @since 0.0.5
     */
    private void startTimer() {
        Timer timer = new Timer();

        if (countdownGame) {
            timerTask = new TimerTask() {
                int sec = countdownSeconds;

                @Override
                public void run() {
                    sec--;

                    if (sec < 0) {
                        Platform.runLater(() -> checkInput());
                    } else {
                        Platform.runLater(() -> timerLabel.setText("Left seconds: " + sec));
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        } else {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    timerTimer = timerTimer.plusSeconds(1);

                    Platform.runLater(() -> timerLabel.setText("Timer: " + timerTimer));
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }

    /**
     * Check if the input is correct. If it is, add a point.
     *
     * @since 0.0.1
     */
    private void checkInput() {
        timerTask.cancel();
        questionAnswered = true;
        nextButton.setDisable(false);

        // Check if the text field has content
        if (textField.getText().isEmpty()) {
            showResultImage("images/disable_x.png");
        } else {
            // Get the text of the text field as double
            try {
                // Check if the input is the correct result
                if (Double.parseDouble(textField.getText()) == lessonPanes.getResult()) {
                    points++;
                    showResultImage("images/check_box_green.png");
                } else {
                    showResultImage("images/disable_x.png");
                }
            } catch (NumberFormatException e) {
                MAlert mAlert = new MAlert(MAlert.MAlertType.ERROR, "Error", borderPane.getScene().getWindow());
                mAlert.setAlertStyle(MAlert.MAlertStyle.LIGHT_ROUNDED);
                mAlert.setHeadline("Please enter a valid number.");
                mAlert.setContentText("If you want to write decimals, please use dots instead of commas.");
                mAlert.addButton("OK", x -> mAlert.closeAlert(), true);

                mAlert.getStage().showAndWait();
            }
        }
    }

    /**
     * Is called when the answer from the user is correct. Show a green checkbox and disable input methods.
     *
     * @since 0.0.1
     */
    private void showResultImage(String pathToImage) {
        // Add a green checkbox beside the question
        Image image = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(pathToImage)));

        ImageView imageView = new ImageView(image);

        lessonHBox.getChildren().add(imageView);

        disableInputFields();
    }

    /**
     * Disable all input methods. Is called after user input.
     *
     * @since 0.0.1
     */
    private void disableInputFields() {
        textField.setDisable(true);
        checkButton.setDisable(true);
    }

    /**
     * Update the round label at the top of the Scene.
     *
     * @since 0.0.1
     */
    private void setRoundLabel() {
        roundLabel.setText("Round " + roundCounter + " of " + numberOfRounds);
    }

    /**
     * Update the progress bar which shows the round progress.
     *
     * @since 0.0.1
     */
    private void setProgressBar() {
        progressBar.setProgress((double) roundCounter / numberOfRounds);
    }

    /**
     * This method is called if the 'Next' button in the Scene is clicked. It resets the textField and loads a
     * new question in the border pane.
     *
     * @since 0.0.1
     */
    public void nextQuestion(ActionEvent event) throws IOException {
        if (questionAnswered) {
            if (numberOfRounds == roundCounter) {
                showResults(event);
            } else {
                roundCounter++;

                textField.setText("");
                textField.setDisable(false);
                checkButton.setDisable(false);

                loadLesson();
            }
        }
    }

    /**
     * Show results with points and mistakes in the result Scene.
     *
     * @param event An ActionEvent to get the Scene.
     * @throws IOException Exception when FXML file is not loadable.
     * @since 0.0.4
     */
    private void showResults(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("result-scene.fxml"));
        Parent root = fxmlLoader.load();

        ResultController resultController = fxmlLoader.getController();
        resultController.setPoints(points, numberOfRounds - points, timerTimer);

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Cancel the lesson and show the settings Scene of the lesson again.
     *
     * @param event Get an ActionEvent of the Button to switch the Scene.
     * @since 0.0.1
     */
    public void cancelLesson(ActionEvent event) {
        MAlert cancelAlert = new MAlert(MAlert.MAlertType.CONFIRMATION, "Cancel lesson",
                ((Node) event.getSource()).getScene().getWindow());
        cancelAlert.setAlertStyle(MAlert.MAlertStyle.LIGHT_ROUNDED);
        cancelAlert.setHeadline("You can do it!");
        cancelAlert.setContentText("Do you really want to cancel this lesson. You have made it right away.");
        cancelAlert.addButton("I continue", x -> cancelAlert.closeAlert(), true);
        cancelAlert.addButton("Cancel lesson", x -> {
            try {
                LessonSettingsController lessonSettingsController;

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lesson-settings-scene.fxml"));
                Parent root = fxmlLoader.load();

                lessonSettingsController = fxmlLoader.getController();
                lessonSettingsController.setLesson(lessons);

                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            cancelAlert.closeAlert();
        }, false);
        cancelAlert.getStage().show();
    }
}