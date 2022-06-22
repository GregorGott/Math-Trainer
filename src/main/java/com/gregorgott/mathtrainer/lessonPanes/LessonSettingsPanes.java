package com.gregorgott.mathtrainer.lessonPanes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Every lesson has its settings. These settings are set in this class.
 *
 * @author GregorGott
 * @version 0.1.1
 * @since 2022-06-22
 */
public class LessonSettingsPanes {
    private final Spinner<Integer> minNumberSpinner;
    private final Spinner<Integer> maxNumberSpinner;
    private final Spinner<Integer> numberOfRoundsSpinner;
    private final Spinner<Integer> maxExponentSpinner;
    private final Node numberOfRoundsNode;
    private final CheckBox additionCheckBox;
    private final CheckBox subtractionCheckBox;
    private final CheckBox multiplicationCheckBox;
    private final CheckBox divisionCheckBox;
    private final CheckBox decimalsCheckBox;
    private Spinner<Integer> countdownTimeSpinner;
    private CheckBox countdownCheckBox;

    /**
     * Initialize the text fields with default values and initialize the checkboxes with text.
     *
     * @since 0.0.1
     */
    public LessonSettingsPanes() {
        numberOfRoundsSpinner = newIntegerSpinner(1, 100, 20);
        minNumberSpinner = newIntegerSpinner(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        maxNumberSpinner = newIntegerSpinner(Integer.MIN_VALUE, Integer.MAX_VALUE, 100);
        maxExponentSpinner = newIntegerSpinner(0, 10, 2);
        numberOfRoundsNode = createSettingNode("Number of rounds:", numberOfRoundsSpinner);
        additionCheckBox = new CheckBox("Addition");
        subtractionCheckBox = new CheckBox("Subtraction");
        multiplicationCheckBox = new CheckBox("Multiplication");
        divisionCheckBox = new CheckBox("Division");
        decimalsCheckBox = new CheckBox("Decimals");
    }

    /**
     * Creates a new integer spinner with min, max and initial value.
     *
     * @param min          the min value as an int.
     * @param max          the max value as an int.
     * @param initialValue the initial value as an int.
     * @return an editable integer spinner.
     * @since 0.1.0
     */
    private Spinner<Integer> newIntegerSpinner(int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue));
        spinner.setEditable(true);

        return spinner;
    }

    /**
     * @return the min number from the minNumberTextField as int.
     * @since 0.0.1
     */
    public int getMinNumber() {
        return minNumberSpinner.getValue();
    }

    /**
     * @return the max number from the maxNumberTextField as int.
     * @since 0.0.1
     */
    public int getMaxNumber() {
        return maxNumberSpinner.getValue();
    }

    /**
     * @return the number of rounds from the numberOfRoundsTextField as int.
     * @since 0.0.1
     */
    public int getNumberOfRounds() {
        return numberOfRoundsSpinner.getValue();
    }

    /**
     * @return the max exponent number from the maxExponentTextField text field as int.
     * @since 0.0.4
     */
    public int getMaxExponent() {
        return maxExponentSpinner.getValue();
    }

    /**
     * @return a boolean if the user selected the additionCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isAddition() {
        return additionCheckBox.isSelected();
    }

    /**
     * @return a boolean if the user selected the subtractionCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isSubtraction() {
        return subtractionCheckBox.isSelected();
    }

    /**
     * @return a boolean if the user selected the multiplicationCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isMultiplication() {
        return multiplicationCheckBox.isSelected();
    }

    /**
     * @return a boolean if the user selected the divisionCheckBox checkbox.
     * @since 0.0.1
     */
    public boolean isDivision() {
        return divisionCheckBox.isSelected();
    }

    /**
     * @return a boolean if the user selected the decimalsCheckBox checkbox.
     * @since 0.0.2
     */
    public boolean isDecimals() {
        return decimalsCheckBox.isSelected();
    }

    /**
     * @return a boolean if the countdown game type is chosen.
     */
    public boolean isCountdown() {
        return countdownCheckBox.isSelected();
    }

    public int getSeconds() {
        return countdownTimeSpinner.getValue();
    }

    /**
     * Creates a node with a label and the belonging node.
     *
     * @param text           the belonging setting label text.
     * @param settingElement the Node which is displayed next to the setting label.
     * @return a node with a label and a node.
     * @since 0.1.0
     */
    private Node createSettingNode(String text, Node settingElement) {
        Label label = new Label(text);
        label.setPrefWidth(130);
        HBox hBox = new HBox(label, settingElement);
        hBox.setAlignment(Pos.CENTER_LEFT);

        return hBox;
    }

    /**
     * Initializes an integer spinner with a label and a checkbox. This method is used to create an input option
     * for the user to enter how much time the user has for each question.
     *
     * @return an integer spinner with a label and a checkbox.
     * @since 0.1.0
     */
    private Node getCountdownSettingNode() {
        countdownTimeSpinner = newIntegerSpinner(5, 600, 20);
        Node spinnerNode =  createSettingNode("Seconds:", countdownTimeSpinner);
        spinnerNode.setDisable(true);

        countdownCheckBox = new CheckBox("Play against the time");
        countdownCheckBox.setOnAction(x -> spinnerNode.setDisable(!countdownCheckBox.isSelected()));

        VBox vBox = new VBox(countdownCheckBox, spinnerNode);
        vBox.setSpacing(5);
        return vBox;
    }

    /**
     * The settings node for the basic operations lesson. This Node contains checkboxes for all types of operators,
     * text field for the number of rounds, min number, max number and a checkbox to allow decimals.
     *
     * @return A Node as a VBox with all spinners and checkboxes.
     * @since 0.0.1
     */
    public Node basicOperationsSettings() {
        HBox operatorsCheckBoxesHBox = new HBox(additionCheckBox, subtractionCheckBox, multiplicationCheckBox,
                divisionCheckBox);
        operatorsCheckBoxesHBox.setSpacing(10);
        Node operatorsNode = createSettingNode("Operators:", operatorsCheckBoxesHBox);
        Node minNumberNode = createSettingNode("Min number:", minNumberSpinner);
        Node maxNumberNode = createSettingNode("Max number:", maxNumberSpinner);

        VBox otherSettingsVBox = new VBox(decimalsCheckBox, getCountdownSettingNode());
        otherSettingsVBox.setSpacing(10);
        Node otherSettingsNode = createSettingNode("Other settings:", otherSettingsVBox);

        VBox vBox = new VBox(operatorsNode, numberOfRoundsNode, minNumberNode, maxNumberNode, otherSettingsNode);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));

        return vBox;
    }

    /**
     * The settings node for the exponential lesson. This node contains two HBoxes. One HBox to set the max base number
     * and the other one to set the max exponent number.
     *
     * @return A Node as a VBox with all spinners.
     * @since 0.0.4
     */
    public Node exponentiationSettings() {
        Node maxBaseNode = createSettingNode("Max base number:", maxNumberSpinner);
        Node maxExponentNode = createSettingNode("Max exponent:", maxExponentSpinner);
        Node otherSettingsNode = createSettingNode("Other settings:", getCountdownSettingNode());

        VBox vBox = new VBox(numberOfRoundsNode, maxBaseNode, maxExponentNode, otherSettingsNode);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));

        return vBox;
    }

    /**
     * The settings node for the rectangle area lesson. This node contains two spinners for the min side length
     * and the max side length.
     *
     * @return A Node as a VBox with all spinners.
     * @since 0.0.5
     */
    public Node rectangleAreaSettings() {
        Node minSideLengthNode = createSettingNode("Min side length:", minNumberSpinner);
        Node maxSideLengthNode = createSettingNode("Max side length:", maxNumberSpinner);
        Node otherSettingsNode = createSettingNode("Other settings:", getCountdownSettingNode());

        VBox vBox = new VBox(numberOfRoundsNode, minSideLengthNode, maxSideLengthNode, otherSettingsNode);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));

        return vBox;
    }
}