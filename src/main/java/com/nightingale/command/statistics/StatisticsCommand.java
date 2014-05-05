package com.nightingale.command.statistics;

import au.com.bytecode.opencsv.CSVWriter;
import com.nightingale.command.generate.TaskGraphGenerator;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.statistics.Experiment;
import com.nightingale.model.entities.statistics.ExperimentConfig;
import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.model.entities.statistics.StatisticsConfig;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.view_components.modeller.ModellerConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class StatisticsCommand extends Service<ObservableList<ExperimentResult>> {

    public StatisticsConfig statisticsConfig;

    @Override
    protected Task<ObservableList<ExperimentResult>> createTask() {
        return new Task<ObservableList<ExperimentResult>>() {
            @Override
            protected ObservableList<ExperimentResult> call() throws Exception {
                try {
                    if (!DataManager.getMppModel().isConnectedGraph()) {
                        Loggers.debugLogger.debug("MPP is not connected");
                        return null;
                    }
                    ObservableList<ExperimentResult> results = FXCollections.observableArrayList();

                    Graph<ProcessorModel, ProcessorLinkModel> oneProcessorSystem = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
                    oneProcessorSystem.addVertex();
                    Graph<ProcessorModel, ProcessorLinkModel> topology = DataManager.getMppModel();
                    Map<ExperimentConfig, List<Experiment.SingleExperimentResult>> singleResults = new HashMap<>();

                    for (int experimentNumber = 0; experimentNumber < statisticsConfig.experimentNumber; experimentNumber++) {
                        System.out.println("experiment #" + experimentNumber);
                        for (int taskNumber = statisticsConfig.minTaskNumber; taskNumber <= statisticsConfig.maxTaskNumber; taskNumber += statisticsConfig.taskNumberGrowStep) {
                            System.out.println("task number = " + taskNumber);
                            for (double connectivity = statisticsConfig.minConnectivity; connectivity <= statisticsConfig.maxConnectivity; connectivity += statisticsConfig.connectivityGrowStep) {
                                System.out.println("connectivity = " + connectivity);
                                Graph<TaskModel, TaskLinkModel> taskGraph =
                                        TaskGraphGenerator.generate(statisticsConfig.minTaskWeight, statisticsConfig.maxTaskWeight,
                                                taskNumber, connectivity);

                                SystemModel oneProcessorPrototype = new SystemModel(oneProcessorSystem, taskGraph).initResources();
                                SystemModel topologyPrototype = new SystemModel(topology, taskGraph).initResources();

                                for (ModellerConstants.ScheduleDescription scheduleDescription : ModellerConstants.ScheduleDescription.DESCRIPTIONS) {
                                    ExperimentConfig experimentConfig = new ExperimentConfig(statisticsConfig, taskNumber, connectivity, scheduleDescription);
                                    System.out.println(experimentConfig);
                                    saveSingleResult(singleResults, experimentConfig, Experiment.executeSingleExperiment(oneProcessorPrototype, topologyPrototype,
                                            experimentConfig,
                                            taskGraph));
                                }
                            }
                        }
                    }

                    singleResults.keySet().stream().forEach(config -> results.add(Experiment.handleSingleResults(config, singleResults.get(config))));

                    logResults(results);

                    return results;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            private void logResults(ObservableList<ExperimentResult> results) throws IOException {
                String csvPath = "D:\\white_raven_statistics_" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm")) + ".txt";
                CSVWriter writer = new CSVWriter(new FileWriter(csvPath));

                List<String[]> data = results.stream().map(ExperimentResult::toStringArray).collect(Collectors.toList());

                writer.writeAll(data);
                writer.close();
            }

            private void saveSingleResult(Map<ExperimentConfig, List<Experiment.SingleExperimentResult>> results,
                                          ExperimentConfig experimentConfig, Experiment.SingleExperimentResult singleExperimentResult) {
                List<Experiment.SingleExperimentResult> singleExperimentResults = results.get(experimentConfig);
                if (singleExperimentResults == null)
                    singleExperimentResults = new ArrayList<>();
                singleExperimentResults.add(singleExperimentResult);
                results.put(experimentConfig, singleExperimentResults);
            }
        };
    }

}
