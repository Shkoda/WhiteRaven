package com.nightingale.view.main_page.generate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.Main;
import com.nightingale.view.view_components.common.NumberField;
import com.nightingale.view.view_components.generator.GeneratorControlGridBuilder;
import com.nightingale.view.view_components.generator.GeneratorMainGridPaneBuilder;
import com.nightingale.view.view_components.generator.GeneratorStageBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.nightingale.view.view_components.generator.GeneratorControlGridBuilder.*;
import static com.nightingale.view.view_components.generator.GeneratorMainGridPaneBuilder.*;

/**
 * Created by Nightingale on 24.03.2014.
 */
@Singleton
public class GeneratorView implements IGeneratorView {
    @Inject
    IGeneratorMediator generatorMediator;
    private GridPane mainGrid, controlsGrid;
    private TextField minTaskWeightField, maxTaskWeightField, taskNumberField, connectivityField;
    private Button submitButton;
    private Stage modalStage;

    @Override
    public Stage getView() {
        if (modalStage == null){
            mainGrid = GeneratorMainGridPaneBuilder.build();
            controlsGrid = GeneratorControlGridBuilder.build();

            addTextLabels();
            addTextFields();
            addSubmitButton();

            generatorMediator.initFieldValidators();
            generatorMediator.initSubmitButton();

            mainGrid.add(controlsGrid, CONTENT_POSITION.columnNumber, CONTENT_POSITION.rowNumber);

            modalStage = GeneratorStageBuilder.build();
            Scene modalScene = new Scene(mainGrid);
            modalStage.setScene(modalScene);
            modalScene.getStylesheets().add(Main.class.getResource("/JMetroLightTheme.css").toExternalForm());
        }

        return modalStage;
    }

    private void addSubmitButton() {
        submitButton = new Button("Generate random task graph");
        submitButton.setId("GenerateGraphButton");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(submitButton);
        mainGrid.add(borderPane, BUTTON_POSITION.columnNumber, BUTTON_POSITION.rowNumber);
    }

    private void addTextFields() {
        controlsGrid.add(minTaskWeightField = NumberField.buildIntField(1), MIN_TASK_WEIGHT_FIELD.columnNumber, MIN_TASK_WEIGHT_FIELD.rowNumber);
        controlsGrid.add(maxTaskWeightField = NumberField.buildIntField(10), MAX_TASK_WEIGHT_FIELD.columnNumber, MAX_TASK_WEIGHT_FIELD.rowNumber);
        controlsGrid.add(taskNumberField = NumberField.buildIntField(12), TASK_NUMBER_FIELD.columnNumber, TASK_NUMBER_FIELD.rowNumber);
        controlsGrid.add(connectivityField = NumberField.buildDoubleField(0.33), CONNECTIVITY_FIELD.columnNumber, CONNECTIVITY_FIELD.rowNumber);
    }

    private void addTextLabels() {
        controlsGrid.add(new Text("Min task weight in (0, max weight]:"), MIN_TASK_WEIGHT_LABEL.columnNumber, MIN_TASK_WEIGHT_LABEL.rowNumber);
        controlsGrid.add(new Text("Max task weight in [min weight, +inf):"), MAX_TASK_WEIGHT_LABEL.columnNumber, MAX_TASK_WEIGHT_LABEL.rowNumber);
        controlsGrid.add(new Text("Task number in [1, +inf):    "), TASK_NUMBER_LABEL.columnNumber, TASK_NUMBER_LABEL.rowNumber);
        controlsGrid.add(new Text("Connectivity in (0, 1]:   "), CONNECTIVITY_LABEL.columnNumber, CONNECTIVITY_LABEL.rowNumber);
    }

    @Override
    public TextField getMinVertexWeightField() {
        return minTaskWeightField;
    }

    @Override
    public TextField getMaxVertexWeightField() {
        return maxTaskWeightField;
    }

    @Override
    public TextField getVertexNumberField() {
        return taskNumberField;
    }

    @Override
    public TextField getConnectivityField() {
        return connectivityField;
    }

    @Override
    public Button getSubmitButton() {
        return submitButton;
    }
}
