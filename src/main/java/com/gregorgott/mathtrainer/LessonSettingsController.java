package com.gregorgott.mathtrainer;

import com.gregorgott.mathtrainer.lessonPanes.LessonSettingsPanes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A Scene in which the user can change various settings, e.g. number of rounds, max number and min number.
 * The content of the Scene varies between the different types of lessons.
 *
 * @author GregorGott
 * @version 0.0.1
 * @since 2022-05-01
 */
public class LessonSettingsController {
    private final LessonSettingsPanes lessonSettingsPanes;
    private final ArrayList<Operator> operators;
    @FXML
    private BorderPane borderPane;
    private LessonController lessonController;
    private Lessons lessons;

    /**
     * Initialize variables.
     */
    public LessonSettingsController() {
        operators = new ArrayList<>();
        lessonSettingsPanes = new LessonSettingsPanes();
    }

    /**
     * Set the lesson to show the correct settings pane in the center.
     *
     * @param lessons Is the selected lesson in the main menu.
     */
    public void setLesson(Lessons lessons) {
        this.lessons = lessons;
        setBorderPaneCenter();
    }

    /**
     * Get lessons and set the correct borderPane center.
     */
    private void setBorderPaneCenter() {
        switch (lessons) {
            case BASIC_OPERATIONS -> borderPane.setCenter(lessonSettingsPanes.basicOperationsSettings());
        }
    }

    /**
     * Get the selected checkboxes from the settings pane and add it to the operators ArrayList.
     */
    private void setOperators() {
        if (lessonSettingsPanes.isAddition()) {
            operators.add(Operator.ADDITION);
        }

        if (lessonSettingsPanes.isSubtraction()) {
            operators.add(Operator.SUBTRACTION);
        }

        if (lessonSettingsPanes.isMultiplication()) {
            operators.add(Operator.MULTIPLICATION);
        }

        if (lessonSettingsPanes.isDivision()) {
            operators.add(Operator.DIVISION);
        }
    }

    /**
     * Start the lesson by loading the lesson Scene FXML file and set various variables.
     *
     * @param event Is the button event to replace the current scene (switch Scenes).
     */
    public void start(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lesson-scene.fxml"));
            Parent root = fxmlLoader.load();

            setOperators();

            lessonController = fxmlLoader.getController();
            lessonController.setLesson(lessons);
            lessonController.setNumberOfRounds(lessonSettingsPanes.getNumberOfRounds());
            lessonController.setNumberRange(lessonSettingsPanes.getMaxNumber(), lessonSettingsPanes.getMinNumber());
            lessonController.setOperators(operators);
            lessonController.loadLesson();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}