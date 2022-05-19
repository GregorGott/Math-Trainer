package com.gregorgott.mathtrainer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * In the main menu the user can select between different lessons. Every lesson is a big button in a flow pane.
 *
 * @author GregorGott
 * @version 0.0.3
 * @since 2022-05-17
 */
public class MainMenuController implements Initializable {
    @FXML
    private FlowPane flowPane;
    private Lessons lessons;

    /**
     * Add all lesson buttons to the flow pane.
     *
     * @since 0.0.1
     */
    private void createLessonButtons() {
        Button[] lessonButtons = new Button[3];
        lessonButtons[0] = newLessonButton("Basic Operations", "Train their math basics with simple questions.",
                Lessons.BASIC_OPERATIONS);
        lessonButtons[1] = newLessonButton("Exponentiation", "Train their knowledge of exponentiation.",
                Lessons.EXPONENTIATION);
        lessonButtons[2] = newLessonButton("Rectangle Area", "Train their basic geometry knowledge.",
                Lessons.RECTANGLE_AREA);

        for (Button button : lessonButtons) {
            flowPane.getChildren().add(button);
        }
    }

    /**
     * A button for basic operations with a title and content text.
     * If the user clicks on the button load the settings for this lesson.
     *
     * @return The lesson button.
     * @since 0.0.1
     */
    private Button newLessonButton(String headline, String contentText, Lessons lessons) {
        Label label1 = new Label(headline);
        label1.setFont(new Font(label1.getFont().getFamily(), 18));
        label1.setWrapText(true);
        Label label2 = new Label(contentText);
        label2.setWrapText(true);

        VBox vBox = new VBox(label1, label2);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(15);

        Button button = new Button();
        int BUTTON_WIDTH = 150;
        button.setPrefWidth(BUTTON_WIDTH);
        int BUTTON_HEIGHT = 150;
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setGraphic(vBox);
        button.setOnAction(event -> {
            this.lessons = lessons;
            loadLessonSettings();
        });

        return button;
    }

    /**
     * Loads the lesson settings in the current Scene and transfer the selected lesson.
     *
     * @since 0.0.1
     */
    private void loadLessonSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("lesson-settings-scene.fxml"));
            Parent root = fxmlLoader.load();

            LessonSettingsController lessonSettingsController = fxmlLoader.getController();
            lessonSettingsController.setLesson(lessons);

            Scene scene = new Scene(root);

            Stage stage = (Stage) flowPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flowPane.setPadding(new Insets(10));
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        createLessonButtons();
    }
}