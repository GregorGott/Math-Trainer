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
 * @version 0.0.3
 * @since 2022-05-15
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
     */
    public int getMinNumber() {
        return minNumberSpinner.getValue();
    }

    /**
     * @return The max number from the maxNumberTextField as int.
     */
    public int getMaxNumber() {
        return maxNumberSpinner.getValue();
    }

    /**
     * @return The number of rounds from the numberOfRoundsTextField as int.
     */
    public int getNumberOfRounds() {
        return numberOfRoundsSpinner.getValue();
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
     *
     * @return A Node as a VBox with all text fields and checkboxes.
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
}