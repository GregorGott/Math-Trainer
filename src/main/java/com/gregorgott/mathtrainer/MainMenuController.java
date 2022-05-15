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
 * @version 0.0.2
 * @since 2022-05-15
 */
public class MainMenuController implements Initializable {
    @FXML
    private FlowPane flowPane;
    private Lessons lessons;

    /**
     * Add all lesson buttons to the flow pane.
     */
    private void createLessonButtons() {
        Button[] lessonButtons = new Button[1];
        lessonButtons[0] = newLessonButton("Basic Operations", "Train your math basics with simple questions.",
                Lessons.BASIC_OPERATIONS);

        for (Button button : lessonButtons) {
            flowPane.getChildren().add(button);
        }
    }

    /**
     * A button for basic operations with a title and content text.
     * If the user clicks on the button load the settings for this lesson.
     *
     * @return The lesson button.
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