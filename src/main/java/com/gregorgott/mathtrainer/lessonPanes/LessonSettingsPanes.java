package com.gregorgott.mathtrainer.lessonPanes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Every lesson has his own settings. These settings are set in this class.
 *
 * @author GregorGott
 * @version 0.0.4
 * @since 2022-05-17
 */
public class LessonSettingsPanes {
    private final Spinner<Integer> minNumberSpinner;
    private final Spinner<Integer> maxNumberSpinner;
    private final Spinner<Integer> numberOfRoundsSpinner;
    private final Spinner<Integer> maxExponentSpinner;
    private final CheckBox additionCheckBox;
    private final CheckBox subtractionCheckBox;
    private final CheckBox multiplicationCheckBox;
    private final CheckBox divisionCheckBox;
    private final CheckBox decimalsCheckBox;

    /**
     * Initialize the text fields with default values and initialize the checkboxes with text.
     *
     * @since 0.0.1
     */
    public LessonSettingsPanes() {
        numberOfRoundsSpinner = new Spinner<>();
        numberOfRoundsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 20));
        numberOfRoundsSpinner.setEditable(true);

        minNumberSpinner = new Spinner<>();
        minNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-Integer.MAX_VALUE, Integer.MAX_VALUE, 0));
        minNumberSpinner.setEditable(true);

        maxNumberSpinner = new Spinner<>();
        maxNumberSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-Integer.MAX_VALUE, Integer.MAX_VALUE, 100));
        maxNumberSpinner.setEditable(true);

        maxExponentSpinner = new Spinner<>();
        maxExponentSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 2));
        maxExponentSpinner.setEditable(true);

        additionCheckBox = new CheckBox("Addition");
        subtractionCheckBox = new CheckBox("Subtraction");
        multiplicationCheckBox = new CheckBox("Multiplication");
        divisionCheckBox = new CheckBox("Division");
        decimalsCheckBox = new CheckBox("Decimals");
    }

    /**
     * @return The min number from the minNumberTextField as int.
     * @since 0.0.1
     */
    public int getMinNumber() {
        return minNumberSpinner.getValue();
    }

    /**
     * @return The max number from the maxNumberTextField as int.
     * @since 0.0.1
     */
    public int getMaxNumber() {
        return maxNumberSpinner.getValue();
    }

    /**
     * @return The number of rounds from the numberOfRoundsTextField as int.
     * @since 0.0.1
     */
    public int getNumberOfRounds() {
        return numberOfRoundsSpinner.getValue();
    }

    /**
     * @return The max exponent number from the maxExponentTextField text field as int.
     * @since 0.0.4
     */
    public int getMaxExponent() {
        return maxExponentSpinner.getValue();
    }

    /**
     * @return A boolean if the user selected the additionCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isAddition() {
        return additionCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the subtractionCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isSubtraction() {
        return subtractionCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the multiplicationCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isMultiplication() {
        return multiplicationCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the divisionCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isDivision() {
        return divisionCheckBox.isSelected();
    }

    /**
     * @return A boolean if the user selected the decimalsCheckBox checkbox.
     * @since 0.0.2
     */
    public boolean isDecimals() {
        return decimalsCheckBox.isSelected();
    }

    /**
     * The settings node for the basic operations' lesson. This Node contains checkboxes for all types of operators,
     * text field for number of rounds, min number, max number and a checkbox to allow decimals.
     *
     * @return A Node as a VBox with all text fields and checkboxes.
     * @since 0.0.1
     */
    public Node basicOperationsSettings() {
        Label operatorsLabel = new Label("Operators");
        operatorsLabel.setPrefWidth(130);
        HBox operatorsCheckBoxesHBox = new HBox(additionCheckBox, subtractionCheckBox, multiplicationCheckBox,
                divisionCheckBox);
        operatorsCheckBoxesHBox.setSpacing(10);
        HBox operatorsHBox = new HBox(operatorsLabel, operatorsCheckBoxesHBox);

        Label numberOfRoundsLabel = new Label("Number of rounds");
        numberOfRoundsLabel.setPrefWidth(130);
        HBox numberOfRoundsHBox = new HBox(numberOfRoundsLabel, numberOfRoundsSpinner);

        Label minNumberLabel = new Label("Min number");
        minNumberLabel.setPrefWidth(130);
        HBox minNumberHBox = new HBox(minNumberLabel, minNumberSpinner);

        Label maxNumberLabel = new Label("Max number");
        maxNumberLabel.setPrefWidth(130);
        HBox maxNumberHBox = new HBox(maxNumberLabel, maxNumberSpinner);

        Label otherSettingsLabel = new Label("Other settings");
        otherSettingsLabel.setPrefWidth(130);
        HBox otherSettingsHBox = new HBox(otherSettingsLabel, decimalsCheckBox);

        VBox vBox = new VBox(operatorsHBox, numberOfRoundsHBox, minNumberHBox, maxNumberHBox, otherSettingsHBox);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));

        return vBox;
    }

    /**
     * The settings node for the exponential lesson. This node contains two HBoxes. One HBox to set the max base number
     * and the other one to set the max exponent number.
     *
     * @return A Node as a VBox with all text fields.
     * @since 0.0.4
     */
    public Node exponentiationSettings() {
        Label maxBaseLabel = new Label("Max base number:");
        maxBaseLabel.setPrefWidth(130);
        HBox maxBaseHBox = new HBox(maxBaseLabel, maxNumberSpinner);

        Label maxExponentLabel = new Label("Max Exponent:");
        maxExponentLabel.setPrefWidth(130);
        HBox maxExponentHBox = new HBox(maxExponentLabel, maxExponentSpinner);

        VBox vBox = new VBox(maxBaseHBox, maxExponentHBox);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));

        return vBox;
    }
}