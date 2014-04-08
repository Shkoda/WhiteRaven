package com.nightingale.command.statistics;

import com.nightingale.command.generate.TaskGraphGenerator;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.statistics.ExperimentConfig;
import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;

import java.util.function.Consumer;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class Experiment {
    public static ExperimentResult execute(ExperimentConfig experimentConfig){
        Graph<ProcessorModel, ProcessorLinkModel> oneProcessorSystem = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
        Graph<ProcessorModel, ProcessorLinkModel> topology = DataManager.getMppModel();

        for (int i = 0; i< experimentConfig.experimentNumber; i++){
            Graph<TaskModel, TaskLinkModel> taskGraph = TaskGraphGenerator.generate(experimentConfig);
            SystemModel oneProcessorModel = new SystemModel(oneProcessorSystem, taskGraph);
            SystemModel topologyModel = new SystemModel(topology, taskGraph);

        }
        return null;
    }

    private static ExperimentResult execute(Consumer<AcyclicDirectedGraph> consumer, boolean useIncreaseOrder){
return null;
    }
}
