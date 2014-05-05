package com.nightingale.model.entities.statistics;

import com.nightingale.command.modelling.critical_path_functions.CriticalPath;
import com.nightingale.command.modelling.critical_path_functions.PathComparator;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.processor_rating_functions.ProcessorRatingFunctionClass;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;

import java.util.List;
import java.util.function.Function;

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

    public static SingleExperimentResult executeSingleExperiment(SystemModel oneProcessorSystemPrototype, SystemModel topologyPrototype,
                                                                 ExperimentConfig experimentConfig, Graph<TaskModel, TaskLinkModel> taskGraph) {
        AcyclicDirectedGraph acyclicDirectedGraph = taskGraph.getAcyclicDirectedGraph();
        List<AcyclicDirectedGraph.Node> queue =acyclicDirectedGraph.getTaskQueue(experimentConfig.scheduleDescription.queueType);

        int t1 = getExecutionDuration(acyclicDirectedGraph, queue, new SystemModel(oneProcessorSystemPrototype), experimentConfig.scheduleDescription.loadingType.ratingFunctionClass);
        int tp = getExecutionDuration(acyclicDirectedGraph, queue, new SystemModel(topologyPrototype), experimentConfig.scheduleDescription.loadingType.ratingFunctionClass);

        double accelerationFactor = (double) t1 / tp;
        double efficiencyFactor = accelerationFactor / topologyPrototype.getProcessorNumber();

        Function<List<AcyclicDirectedGraph.Node>, Number> vertexWeightFunction = PathComparator.VERTEX_WEIGHT_FUNCTION;

        double graphCriticalPathWeight = vertexWeightFunction
                .apply(CriticalPath.find(taskGraph.getAcyclicDirectedGraph(), vertexWeightFunction))
                .doubleValue();

        double algorithmEfficiencyFactor = graphCriticalPathWeight / tp;
        return new SingleExperimentResult(accelerationFactor, efficiencyFactor, algorithmEfficiencyFactor);
    }

    private static int getExecutionDuration(AcyclicDirectedGraph graph, List<AcyclicDirectedGraph.Node> queue, SystemModel systemModel, ProcessorRatingFunctionClass processorRatingFunctionClass) {
        List<com.nightingale.model.entities.schedule.Task> convertedQueue = com.nightingale.model.entities.schedule.Task.convert(graph, queue);
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
