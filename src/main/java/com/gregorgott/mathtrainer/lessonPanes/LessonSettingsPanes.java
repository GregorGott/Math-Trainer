package com.gregorgott.mathtrainer.lessonPanes;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Every lesson has his own settings. These settings are set in this class.
 *
 * @author GregorGott
 * @version 0.0.1
 * @since 2022-05-01
 */
public class LessonSettingsPanes {

    private final TextField minNumberTextField;
    private final TextField maxNumberTextField;
    private final TextField numberOfRoundsTextField;
    private final CheckBox additionCheckBox;
    private final CheckBox subtractionCheckBox;
    private final CheckBox multiplicationCheckBox;
    private final CheckBox divisionCheckBox;
    private final CheckBox decimalsCheckBox;

    /**
     * Initialize the text fields with default values and initialize the checkboxes with text.
     */
    public LessonSettingsPanes() {
        numberOfRoundsTextField = new TextField("20");
        minNumberTextField = new TextField("0");
        maxNumberTextField = new TextField("100");

        additionCheckBox = new CheckBox("Addition");
        subtractionCheckBox = new CheckBox("Subtraction");
        multiplicationCheckBox = new CheckBox("Multiplication");
        divisionCheckBox = new CheckBox("Division");
        decimalsCheckBox = new CheckBox("Decimals");
    }

    /**
     * @return The min number from the minNumberTextField as text.
     */
    public int getMinNumber() {
        return Integer.parseInt(minNumberTextField.getText());
    }

    /**
     * @return The max number from the maxNumberTextField as text.
     */
    public int getMaxNumber() {
        return Integer.parseInt(maxNumberTextField.getText());
    }

    /**
     * @return The number of rounds from the numberOfRoundsTextField as Text.
     */
    public int getNumberOfRounds() {
        return Integer.parseInt(numberOfRoundsTextField.getText());
    }

    /**
     * @return A boolean if the user selected the additionCheckBox checkbox.
     */
    public boolean isAddition() {
        return additionCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the subtractionCheckBox checkbox.
     */
    public boolean isSubtraction() {
        return subtractionCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the multiplicationCheckBox checkbox.
     */
    public boolean isMultiplication() {
        return multiplicationCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the divisionCheckBox checkbox.
     */
    public boolean isDivision() {
        return divisionCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the decimalsCheckBox checkbox.
     */
    public boolean isDecimals() {
        return decimalsCheckBox.isSelected();
    }

    /**
     * The settings node for the basic operations' lesson. This Node contains checkboxes for all types of operators,
     * text field for number of rounds, min number, max number and a checkbox to allow decimals.
     * @return A Node with a VBox with all text fields and checkboxes.
     */
    public Node basicOperationsSettings() {
        Label operatorsLabel = new Label("Operators");
        operatorsLabel.setPrefWidth(130);
        HBox operatorsHBox = new HBox(operatorsLabel, additionCheckBox, subtractionCheckBox, multiplicationCheckBox,
                divisionCheckBox);
        operatorsHBox.setSpacing(10);

        Label numberOfRoundsLabel = new Label("Number of rounds");
        numberOfRoundsLabel.setPrefWidth(130);
        HBox numberOfRoundsHBox = new HBox(numberOfRoundsLabel, numberOfRoundsTextField);

        Label minNumberLabel = new Label("Min number");
        minNumberLabel.setPrefWidth(130);
        HBox minNumberHBox = new HBox(minNumberLabel, minNumberTextField);

        Label maxNumberLabel = new Label("Max number");
        maxNumberLabel.setPrefWidth(130);
        HBox maxNumberHBox = new HBox(maxNumberLabel, maxNumberTextField);

        Label otherSettingsLabel = new Label("Other settings");
        otherSettingsLabel.setPrefWidth(130);
        HBox otherSettingsHBox = new HBox(otherSettingsLabel, decimalsCheckBox);

        VBox vBox = new VBox(operatorsHBox, numberOfRoundsHBox, minNumberHBox, maxNumberHBox, otherSettingsHBox);
        vBox.setSpacing(20);

        return vBox;
    }
}