package com.nightingale.view.view_components.statistics;

import com.nightingale.model.entities.statistics.Experiment;
import com.nightingale.model.entities.statistics.ExperimentResult;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.Observable;

/**
 * Created by Nightingale on 08.04.2014.
 */
public class StatisticsTable {

    public static TableView build(ObservableList<ExperimentResult> experimentResults) {
        TableView<ExperimentResult> tableView = new TableView<>();

        TableColumn<ExperimentResult, String> queueColumn = new TableColumn<>("Queue");
        queueColumn.setCellValueFactory(new PropertyValueFactory<>("queueType"));


        TableColumn<ExperimentResult, String> loadingColumn = new TableColumn<>("Loading");
        loadingColumn.setCellValueFactory(new PropertyValueFactory<>("loadingType"));

        TableColumn<ExperimentResult, String> taskNumberColumn = new TableColumn<>("  Task\nnumber");
        taskNumberColumn.setCellValueFactory(new PropertyValueFactory<>("taskNumber"));

        TableColumn<ExperimentResult, String> connectivityColumn = new TableColumn<>("Connectivity");
        connectivityColumn.setCellValueFactory(new PropertyValueFactory<>("connectivity"));

        TableColumn<ExperimentResult, String> accelerationFactorColumn = new TableColumn<>("Acceleration\n    factor");
        accelerationFactorColumn.setCellValueFactory(new PropertyValueFactory<>("accelerationFactor"));

        TableColumn<ExperimentResult, String> efficiencyFactorColumn = new TableColumn<>("Efficiency\n  factor");
        efficiencyFactorColumn.setCellValueFactory(new PropertyValueFactory<>("efficiencyFactor"));

        TableColumn<ExperimentResult, String> algorithmEfficiencyFactorColumn = new TableColumn<>("Algorithm\n efficiency\n   factor");
        algorithmEfficiencyFactorColumn.setCellValueFactory(new PropertyValueFactory<>("algorithmEfficiencyFactor"));

//tableView.getColumns().setAll(queueColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().setAll(queueColumn, loadingColumn, taskNumberColumn, connectivityColumn, accelerationFactorColumn,
                efficiencyFactorColumn, algorithmEfficiencyFactorColumn);

        tableView.setItems(experimentResults);

        return tableView;
    }
}
