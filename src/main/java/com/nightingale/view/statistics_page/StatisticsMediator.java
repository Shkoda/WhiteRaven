package com.nightingale.view.statistics_page;

import com.google.inject.Inject;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.generate.GenerateTaskGraphCommand;
import com.nightingale.command.menu.open.OpenTaskGraphCommand;
import com.nightingale.command.statistics.StatisticsCommand;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.model.entities.statistics.StatisticsConfig;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.view.view_components.statistics.StatisticsTable;
import com.sun.org.glassfish.external.statistics.Statistic;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Observable;

/**
 * Created by Nightingale on 09.03.14.
 */
public class StatisticsMediator implements IStatisticsMediator {
    @Inject
    ICommandProvider commandProvider;
    @Inject
    private IStatisticsView statisticsView;

    private TextField minTaskNumberField, maxTaskNumberField;
    private TextField vertexNumberGrowStep;
    private TextField minTaskConnectivityField, maxTaskConnectivityField;
    private TextField connectivityGrowStep;
    private TextField minTaskWeightField, maxTaskWeightField;
    private TextField graphNumber;

    private Button refreshStatisticsButton;

    @Override
    public void initFieldValidators() {
        refreshStatisticsButton = statisticsView.getRefreshButton();
        initTextFields();
        addTextFieldListeners();
    }

    @Override
    public void initSubmitButton() {
        refreshStatisticsButton.setOnMouseClicked(me -> {
            if (!DataManager.getMppModel().isConnectedGraph())
                return;
            statisticsView.setStatistics(null);
            StatisticsCommand command = commandProvider.get(StatisticsCommand.class);
            command.setOnSucceeded(workerStateEvent -> {
                ObservableList<ExperimentResult> statistics = (ObservableList<ExperimentResult>) workerStateEvent.getSource().getValue();
                statisticsView.setStatistics(statistics);
            });
            command.statisticsConfig = readConfig();
            command.start();
        });

    }

    private StatisticsConfig readConfig() {
        return new StatisticsConfig(
                Integer.valueOf(minTaskWeightField.getText()),
                Integer.valueOf(maxTaskWeightField.getText()),
                Integer.valueOf(minTaskNumberField.getText()),
                Integer.valueOf(maxTaskNumberField.getText()),
                Integer.valueOf(vertexNumberGrowStep.getText()),

                Double.valueOf(minTaskConnectivityField.getText()),
                Double.valueOf(maxTaskConnectivityField.getText()),
                Double.valueOf(connectivityGrowStep.getText()),

                Integer.valueOf(graphNumber.getText()));
    }

    private void initTextFields() {
        minTaskNumberField = statisticsView.getMinTaskNumberField();
        maxTaskNumberField = statisticsView.getMaxTaskNumberField();
        vertexNumberGrowStep = statisticsView.getVertexNumberGrowStep();

        minTaskConnectivityField = statisticsView.getMinTaskConnectivityField();
        maxTaskConnectivityField = statisticsView.getMaxTaskConnectivityField();
        connectivityGrowStep = statisticsView.getConnectivityGrowStep();

        minTaskWeightField = statisticsView.getMinTaskWeightField();
        maxTaskWeightField = statisticsView.getMaxTaskWeightField();
        graphNumber = statisticsView.getGraphNumber();
    }

    private void addTextFieldListeners() {
        minTaskNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer minValue = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            Integer maxValue = maxTaskNumberField.getText().length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(maxTaskNumberField.getText());
            setStyle(minValue > 0 && minValue <= maxValue, minTaskNumberField, maxTaskNumberField);
        });
        maxTaskNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer maxValue = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            Integer minValue = minTaskNumberField.getText().length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(minTaskNumberField.getText());
            setStyle(minValue > 0 && minValue <= maxValue, minTaskNumberField, maxTaskNumberField);
        });
        vertexNumberGrowStep.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer value = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            setStyle(value > 0, vertexNumberGrowStep);
        });

        minTaskConnectivityField.textProperty().addListener((observable, oldValue, newValue) -> {
            Double minValue = newValue.length() == 0 ? Double.MIN_VALUE : Double.valueOf(newValue);
            Double maxValue = maxTaskConnectivityField.getText().length() == 0 ? Double.MIN_VALUE : Double.valueOf(maxTaskConnectivityField.getText());
            setStyle(minValue > 0 && minValue <= maxValue && maxValue <= 1, minTaskConnectivityField, maxTaskConnectivityField);
        });
        maxTaskConnectivityField.textProperty().addListener((observable, oldValue, newValue) -> {
            Double maxValue = newValue.length() == 0 ? Double.MIN_VALUE : Double.valueOf(newValue);
            Double minValue = minTaskConnectivityField.getText().length() == 0 ? Double.MAX_VALUE : Double.valueOf(minTaskConnectivityField.getText());
            setStyle(minValue > 0 && minValue <= maxValue && maxValue <= 1, minTaskConnectivityField, maxTaskConnectivityField);
        });
        connectivityGrowStep.textProperty().addListener((observable, oldValue, newValue) -> {
            Double value = newValue.length() == 0 ? Double.MIN_VALUE : Integer.valueOf(newValue);
            setStyle(value > 0 && value < 1, connectivityGrowStep);
        });

        minTaskWeightField.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer minValue = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            Integer maxValue = maxTaskWeightField.getText().length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(maxTaskWeightField.getText());
            setStyle(minValue > 0 && minValue <= maxValue, minTaskWeightField, maxTaskWeightField);
        });
        maxTaskWeightField.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer maxValue = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            Integer minValue = minTaskWeightField.getText().length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(minTaskWeightField.getText());
            setStyle(minValue > 0 && minValue <= maxValue, minTaskWeightField, maxTaskWeightField);
        });
        graphNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            Integer value = newValue.length() == 0 ? Integer.MIN_VALUE : Integer.valueOf(newValue);
            setStyle(value > 0, graphNumber);
        });

    }


    private void setStyle(boolean allCorrect, Node... nodes) {
        String style = allCorrect ? "-fx-control-inner-background:  white; -fx-font-size: 9"
                : "-fx-control-inner-background:  rgb(247, 157, 173);  -fx-font-size: 9";
        for (Node node : nodes)
            node.setStyle(style);
        refreshStatisticsButton.setDisable(!allCorrect);
    }
}
