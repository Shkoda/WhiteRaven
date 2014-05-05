package com.nightingale.command.modelling;

import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.processor_rating_functions.ProcessorRatingFunctionClass;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.modeller_page.IModellerMediator;
import com.nightingale.view.view_components.modeller.ModellerConstants;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;

import static com.nightingale.view.view_components.modeller.ModellerConstants.QueueType.*;
import static com.nightingale.view.view_components.modeller.ModellerConstants.ScheduleDescription.*;

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

                    List<AcyclicDirectedGraph.Node> queue2 = graph.getTaskQueue(QUEUE_2);
                    List<AcyclicDirectedGraph.Node> queue6 = graph.getTaskQueue(QUEUE_6);
                    List<AcyclicDirectedGraph.Node> queue16 = graph.getTaskQueue(QUEUE_16);

                    Map<String, String> queueMap = modellerMediator.getQueueMap();
                    queueMap.put(QUEUE_2.text, RefreshModellerData.toString(queue2));
                    queueMap.put(QUEUE_6.text, RefreshModellerData.toString(queue6));
                    queueMap.put(QUEUE_16.text, RefreshModellerData.toString(queue16));

                    mppModel = DataManager.getMppModel();

                    if (!mppModel.isConnectedGraph())
                        return null;

                    Map<ModellerConstants.ScheduleDescription, SystemModel> scheduleMap = modellerMediator.getScheduleMap();

                    addGantt(graph, queue2, QUEUE_2_SCHEDULE_3, ProcessorRatingFunctionClass.MAX_CONNECTIVITY, scheduleMap);
                    addGantt(graph, queue6, QUEUE_6_SCHEDULE_3, ProcessorRatingFunctionClass.MAX_CONNECTIVITY, scheduleMap);
                    addGantt(graph, queue16, QUEUE_16_SCHEDULE_3, ProcessorRatingFunctionClass.MAX_CONNECTIVITY, scheduleMap);

                    addGantt(graph, queue2, QUEUE_2_SCHEDULE_5, ProcessorRatingFunctionClass.SHORTEST_PATH, scheduleMap);
                    addGantt(graph, queue6, QUEUE_6_SCHEDULE_5, ProcessorRatingFunctionClass.SHORTEST_PATH, scheduleMap);
                    addGantt(graph, queue16, QUEUE_16_SCHEDULE_5, ProcessorRatingFunctionClass.SHORTEST_PATH, scheduleMap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }


    private static void addGantt( AcyclicDirectedGraph graph,
                                  List<AcyclicDirectedGraph.Node> queue,
                                 ModellerConstants.ScheduleDescription scheduleDescription,
                                 ProcessorRatingFunctionClass processorRatingFunctionClass,
                                 Map<ModellerConstants.ScheduleDescription, SystemModel> scheduleMap) {

        List<com.nightingale.model.entities.schedule.Task> convertedQueue = com.nightingale.model.entities.schedule.Task.convert(graph, queue);

        Loggers.debugLogger.debug(scheduleDescription + " " + convertedQueue);
        SystemModel systemModel = new SystemModel();
        systemModel.initResources().loadTasks(convertedQueue, processorRatingFunctionClass.buildFunction(systemModel));
        scheduleMap.put(scheduleDescription, systemModel);
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