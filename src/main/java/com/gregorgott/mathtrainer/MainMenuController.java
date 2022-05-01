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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * In the main menu the user can select between different lessons. Every lesson is a big button in a flow pane.
 *
 * @author GregorGott
 * @version 0.0.1
 * @since 2022-05-01
 */
public class MainMenuController implements Initializable {
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 150;
    @FXML
    private FlowPane flowPane;
    private Lessons lessons;

    /**
     * Add a lesson button to the flow pane.
     */
    private void createLessonButtons() {
        flowPane.getChildren().add(basicOperationsButton());
    }

    /**
     * A button for basic operations with a title and content text.
     * If the user clicks on the button load the settings for this lesson.
     *
     * @return The lesson button.
     */
    private Button basicOperationsButton() {
        Label label1 = new Label("Basic Operations");
        Label label2 = new Label("Train your math basics with simple questions");
        label2.setWrapText(true);

        VBox vBox = new VBox(label1, label2);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        Button button = new Button();
        button.setPrefWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setGraphic(vBox);
        button.setOnAction(event -> {
            lessons = Lessons.BASIC_OPERATIONS;
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
        createLessonButtons();
    }
}