package com.gregorgott.mathtrainer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

/**
 * This Scene shows a result Scene with the points, mistakes and a grade.
 *
 * @author GregorGott
 * @version 0.0.2
 * @since 2022-05-21
 */
public class ResultController {
    @FXML
    private Label pointsLabel;
    @FXML
    private Label mistakesLabel;
    @FXML
    private Label gradeLabel;
    @FXML
    private Label timeLabel;

    private int points;
    private int mistakes;

    /**
     * Set the points, and mistakes and add those values to the labels.
     *
     * @param points   Number of reached points.
     * @param mistakes Number of mistakes.
     * @since 0.0.1
     */
    public void setPoints(int points, int mistakes, LocalTime time) {
        this.points = points;
        this.mistakes = mistakes;

        pointsLabel.setText(getPoints());
        mistakesLabel.setText("Mistakes: " + mistakes);
        gradeLabel.setText("Grade: " + calculateGrade());
        timeLabel.setText("Needed time: " + time);
    }

    /**
     * Calculate a grade from A to F.
     *
     * @return A String with the grade (A - F).
     * @since 0.0.1
     */
    private String calculateGrade() {
        int totalPoints = points + mistakes;
        double oneGrade = (double) totalPoints / 6;

        if (points == oneGrade * 6) {
            return "A";
        } else if (points >= oneGrade * 5) {
            return "B";
        } else if (points >= oneGrade * 4) {
            return "C";
        } else if (points >= oneGrade * 3) {
            return "D";
        } else if (points >= oneGrade * 2) {
            return "E";
        } else {
            return "F";
        }
    }

    /**
     * Sets the pointsLabel. If only one point is reached the singular form of "point" is used.
     *
     * @since 0.0.1
     */
    private String getPoints() {
        if (points == 0) {
            return "No Points reached.";
        } else if (points == 1) {
            return points + " Point.";
        } else {
            return points + " Points.";
        }
    }

    /**
     * This action is called when the "Back to menu" button is pushed. It shows the main menu again.
     *
     * @param event An ActionEvent to load the Scene on it.
     * @throws IOException Exception when FXML file is not loadable.
     * @since 0.0.1
     */
    public void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu-scene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}