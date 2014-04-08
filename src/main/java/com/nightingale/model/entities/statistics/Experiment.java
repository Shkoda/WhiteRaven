package com.nightingale.model.entities.statistics;

import com.nightingale.command.generate.TaskGraphGenerator;
import com.nightingale.command.modelling.critical_path_functions.CriticalPath;
import com.nightingale.command.modelling.critical_path_functions.PathComparator;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.processor_rating_functions.ProcessorRatingFunctionClass;
import com.nightingale.model.entities.statistics.ExperimentConfig;
import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import static com.nightingale.view.view_components.modeller.ModellerConstants.QueueType.QUEUE_2;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class Experiment {

    public static ExperimentResult handleSingleResults(ExperimentConfig experimentConfig, List<SingleExperimentResult> singleExperimentResults) {
        double averageAccelerationFactor = singleExperimentResults.stream().mapToDouble(e -> e.accelerationFactor).average().getAsDouble();
        double averageEfficiencyFactor = singleExperimentResults.stream().mapToDouble(e -> e.efficiencyFactor).average().getAsDouble();
        double averageAlgorithmEfficiencyFactor = singleExperimentResults.stream().mapToDouble(e -> e.algorithmEfficiencyFactor).average().getAsDouble();
        return new ExperimentResult(experimentConfig, averageAccelerationFactor, averageEfficiencyFactor, averageAlgorithmEfficiencyFactor);
    }

    public static SingleExperimentResult executeSingleExperiment(Graph<ProcessorModel, ProcessorLinkModel> oneProcessorSystem, Graph<ProcessorModel, ProcessorLinkModel> topology,
                                                                 ExperimentConfig experimentConfig, Graph<TaskModel, TaskLinkModel> taskGraph) {

        List<AcyclicDirectedGraph.Node> queue = taskGraph.getAcyclicDirectedGraph().getTaskQueue(experimentConfig.scheduleDescription.queueType);

        int t1 = getExecutionDuration(queue, new SystemModel(oneProcessorSystem, taskGraph), experimentConfig.scheduleDescription.loadingType.ratingFunctionClass);
        int tp = getExecutionDuration(queue, new SystemModel(topology, taskGraph), experimentConfig.scheduleDescription.loadingType.ratingFunctionClass);

        double accelerationFactor = (double) t1 / tp;
        double efficiencyFactor = accelerationFactor / topology.getVertexNumber();

        Function<List<AcyclicDirectedGraph.Node>, Number> vertexWeightFunction = PathComparator.VERTEX_WEIGHT_FUNCTION;

        double graphCriticalPathWeight = vertexWeightFunction
                .apply(CriticalPath.find(taskGraph.getAcyclicDirectedGraph(), vertexWeightFunction))
                .doubleValue();

        double algorithmEfficiencyFactor = graphCriticalPathWeight / tp;
        return new SingleExperimentResult(accelerationFactor, efficiencyFactor, algorithmEfficiencyFactor);
    }

    private static int getExecutionDuration(List<AcyclicDirectedGraph.Node> queue, SystemModel systemModel, ProcessorRatingFunctionClass processorRatingFunctionClass) {
        List<com.nightingale.model.entities.schedule.Task> convertedQueue = com.nightingale.model.entities.schedule.Task.convert(queue);
        Loggers.debugLogger.debug(queue);
        Loggers.debugLogger.debug(convertedQueue);
        systemModel.initResources().loadTasks(convertedQueue, processorRatingFunctionClass.buildFunction(systemModel));
        return systemModel.getLastOperationFinishTime();
    }


    public static class SingleExperimentResult {
        public final double accelerationFactor, efficiencyFactor, algorithmEfficiencyFactor;

        private SingleExperimentResult(double accelerationFactor, double efficiencyFactor, double algorithmEfficiencyFactor) {
            this.accelerationFactor = accelerationFactor;
            this.efficiencyFactor = efficiencyFactor;
            this.algorithmEfficiencyFactor = algorithmEfficiencyFactor;
        }
    }

}
