package com.nightingale.command.modelling;

import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.DeadlineDifferenceConsumer;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.TimeBeforeCurrentConsumer;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.processor_rating_functions.MaxConnectivityFunction;
import com.nightingale.model.entities.schedule.processor_rating_functions.ShortestPathFunction;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.modeller_page.IModellerMediator;
import com.nightingale.view.view_components.modeller.ModellerConstants;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by Nightingale on 31.03.2014.
 */
public class RefreshModellerData extends Service<Void> {

    public AcyclicDirectedGraph graph;
    public IModellerMediator modellerMediator;

    private Graph<ProcessorModel, ProcessorLinkModel> mppModel;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                try {
                    if (graph.isEmpty())
                        return null;

                    List<AcyclicDirectedGraph.Node> queue2 = graph.getTaskQueue(new DeadlineDifferenceConsumer(), true);
                    List<AcyclicDirectedGraph.Node> queue6 = graph.getTaskQueue(new NodesAfterCurrentConsumer(), false);
                    List<AcyclicDirectedGraph.Node> queue16 = graph.getTaskQueue(new TimeBeforeCurrentConsumer(), true);

                    Map<String, String> queueMap = modellerMediator.getQueueMap();
                    queueMap.put(ModellerConstants.QueueType.QUEUE_2.text, RefreshModellerData.toString(queue2));
                    queueMap.put(ModellerConstants.QueueType.QUEUE_6.text, RefreshModellerData.toString(queue6));
                    queueMap.put(ModellerConstants.QueueType.QUEUE_16.text, RefreshModellerData.toString(queue16));

                    mppModel = DataManager.getMppModel();
                    if (!mppModel.isConnectedGraph())
                        return null;

                    Map<ModellerConstants.ScheduleDescription, SystemModel> scheduleMap = modellerMediator.getScheduleMap();

                    addGantt(queue2, ModellerConstants.ScheduleDescription.QUEUE_2_SCHEDULE_3, FunctionClass.MAX_CONNECTIVITY, scheduleMap);
                    addGantt(queue6, ModellerConstants.ScheduleDescription.QUEUE_6_SCHEDULE_3, FunctionClass.MAX_CONNECTIVITY, scheduleMap);
                    addGantt(queue16, ModellerConstants.ScheduleDescription.QUEUE_16_SCHEDULE_3, FunctionClass.MAX_CONNECTIVITY, scheduleMap);

                    addGantt(queue2, ModellerConstants.ScheduleDescription.QUEUE_2_SCHEDULE_5, FunctionClass.SHORTEST_PATH, scheduleMap);
                    addGantt(queue6, ModellerConstants.ScheduleDescription.QUEUE_6_SCHEDULE_5, FunctionClass.SHORTEST_PATH, scheduleMap);
                    addGantt(queue16, ModellerConstants.ScheduleDescription.QUEUE_16_SCHEDULE_5, FunctionClass.SHORTEST_PATH, scheduleMap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private enum FunctionClass {
        MAX_CONNECTIVITY,
        SHORTEST_PATH
    }

    private static void addGantt(List<AcyclicDirectedGraph.Node> queue,
                                 ModellerConstants.ScheduleDescription scheduleDescription,
                                 FunctionClass functionClassClass,
                                 Map<ModellerConstants.ScheduleDescription, SystemModel> scheduleMap) {

        List<com.nightingale.model.entities.schedule.Task> convertedQueue = com.nightingale.model.entities.schedule.Task.convert(queue);

        Loggers.debugLogger.debug(scheduleDescription + " " + convertedQueue);
        SystemModel systemModel = new SystemModel();
        systemModel.initResources().loadTasks(convertedQueue, buildFunction(functionClassClass, systemModel));
        scheduleMap.put(scheduleDescription, systemModel);
    }

    private static BiFunction<List<ProcessorResource>, List<com.nightingale.model.entities.schedule.Task>,
            ProcessorResource> buildFunction(FunctionClass functionClass, SystemModel systemModel) {
        switch (functionClass) {
            case MAX_CONNECTIVITY:
                return new MaxConnectivityFunction();
            case SHORTEST_PATH:
                return new ShortestPathFunction(systemModel.getPaths(), systemModel.getTaskOnProcessorsMap());
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String toString(List<AcyclicDirectedGraph.Node> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i).getName());
            if (i < list.size() - 1)
                builder.append(" -> ");
        }
        return builder.toString();
    }
}