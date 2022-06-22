package com.gregorgott.mathtrainer;

import com.gregorgott.mathtrainer.lessonPanes.LessonSettingsPanes;
import com.gregorgott.mdialogwindows.MAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * A Scene in which the user can change various settings, e.g. the number of rounds, max number and min number.
 * The content of the Scene varies between the different types of lessons.
 *
 * @author GregorGott
 * @version 0.0.7
 * @since 2022-06-07
 */
public class LessonSettingsController {
    private final LessonSettingsPanes lessonSettingsPanes;
    private final ArrayList<Operator> operators;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label headerLabel;
    private Lessons lessons;

    /**
     * Initialize variables.
     *
     * @since 0.0.1
     */
    public LessonSettingsController() {
        operators = new ArrayList<>();
        lessonSettingsPanes = new LessonSettingsPanes();
    }

    /**
     * Set the lesson to show the correct settings pane in the centre.
     *
     * @param lessons Is the selected lesson in the main menu.
     * @since 0.0.1
     */
    public void setLesson(Lessons lessons) {
        this.lessons = lessons;
        setBorderPane();
    }

    /**
     * Get lessons and set the correct borderPane centre.
     *
     * @since 0.0.1
     */
    private void setBorderPane() {
        switch (lessons) {
            case BASIC_OPERATIONS -> {
                borderPane.setCenter(lessonSettingsPanes.basicOperationsSettings());
                headerLabel.setText("Basic Operations");
            }
            case EXPONENTIATION -> {
                borderPane.setCenter(lessonSettingsPanes.exponentiationSettings());
                headerLabel.setText("Exponentiation");
            }
            case RECTANGLE_AREA -> {
                borderPane.setCenter(lessonSettingsPanes.rectangleAreaSettings());
                headerLabel.setText("Rectangle Area");
            }
        }
    }

    /**
     * Get the selected checkboxes from the settings pane and add them to the operators ArrayList.
     *
     * @since 0.0.1
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
     * Start the lesson by loading the lesson Scene FXML file and setting various variables.
     *
     * @param event Is the button event to replace the current scene (switch Scenes).
     * @throws IOException If the FXML file is not found.
     * @since 0.0.1
     */
    public void start(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lesson-scene.fxml"));
        Parent root = fxmlLoader.load();

        setOperators();

        LessonController lessonController = fxmlLoader.getController();
        lessonController.setLesson(lessons);
        lessonController.setNumberOfRounds(lessonSettingsPanes.getNumberOfRounds());
        lessonController.setNumberRange(lessonSettingsPanes.getMaxNumber(), lessonSettingsPanes.getMinNumber());
        lessonController.setOperators(operators);
        lessonController.setDecimals(lessonSettingsPanes.isDecimals());
        lessonController.setCountdownGame(lessonSettingsPanes.isCountdown(), lessonSettingsPanes.getSeconds());
        lessonController.setMaxExponent(lessonSettingsPanes.getMaxExponent());

        if (lessons != Lessons.BASIC_OPERATIONS || operators.size() > 0) {
            lessonController.loadLesson();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } else {
            MAlert mAlert = new MAlert(MAlert.MAlertType.INFORMATION, "Math Trainer", borderPane.getScene().getWindow());
            mAlert.setAlertStyle(MAlert.MAlertStyle.LIGHT_ROUNDED);
            mAlert.setHeadline("Please select operators.");
            mAlert.setContentText("Math Trainer can not start a lesson when you do not select operators.");
            mAlert.addButton("OK", x -> mAlert.closeAlert(), true);

            mAlert.getStage().showAndWait();
        }
    }

    /**
     * The help button in the right bottom corner opens a small alert with a quick help. This quick help is
     * written in the <code>lesson_help.properties</code>. In this alert is also a wiki button, which opens
     * a wikipedia article about this topic.
     *
     * @throws IOException if the properties file could not be found.
     * @since 0.0.7
     */
    public void help() throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(Objects.requireNonNull(
                Main.class.getResourceAsStream("other/lesson_help.properties"))));

        String modeAsString;

        switch (lessons) {
            case BASIC_OPERATIONS -> modeAsString = "basic operations";
            case EXPONENTIATION -> modeAsString = "exponentiation";
            case RECTANGLE_AREA -> modeAsString = "rectangle area";
            default -> modeAsString = "unknown";
        }

        MAlert mAlert = new MAlert(MAlert.MAlertType.INFORMATION, "Help");
        mAlert.setAlertStyle(MAlert.MAlertStyle.LIGHT_ROUNDED);
        mAlert.setHeadline("Help for " + modeAsString + ".");
        mAlert.setContentText(properties.getProperty(lessons + "_EXPLANATION"));
        mAlert.addButton("I need more help", x -> {
            try {
                Desktop.getDesktop().browse(new URI(properties.getProperty(lessons + "_WIKI")));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }, false);
        mAlert.addButton("Thanks", x -> mAlert.closeAlert(), true);
        mAlert.getStage().show();
    }

    /**
     * Is called when the back button is pushed. Then show the main menu again.
     *
     * @param event Get an action event from the button to switch the Scene.
     * @throws IOException If the FXML file is not found.
     * @since 0.0.4
     */
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu-scene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}