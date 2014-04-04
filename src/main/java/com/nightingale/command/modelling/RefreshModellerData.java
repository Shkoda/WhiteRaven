package com.nightingale.command.modelling;

import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.DeadlineDifferenceConsumer;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.TimeBeforeCurrentConsumer;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.modeller_page.IModellerMediator;
import com.nightingale.view.modeller_page.IModellerView;
import com.nightingale.view.view_components.modeller.ModellerConstants;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.logging.Logger;
import static com.nightingale.view.view_components.modeller.ModellerConstants.ScheduleType.*;
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
                    queueMap.put(ModellerConstants.FIRST_QUEUE_ALGORITHM_TEXT, RefreshModellerData.toString(queue2));
                    queueMap.put(ModellerConstants.SECOND_QUEUE_ALGORITHM_TEXT, RefreshModellerData.toString(queue6));
                    queueMap.put(ModellerConstants.THIRD_QUEUE_ALGORITHM_TEXT, RefreshModellerData.toString(queue16));

                    mppModel = DataManager.getMppModel();
                    if (!mppModel.isConnectedGraph())
                        return null;


                    List<com.nightingale.model.entities.schedule.Task> convertedTasks2 = com.nightingale.model.entities.schedule.Task.convert(queue2);
                    List<com.nightingale.model.entities.schedule.Task> convertedTasks6 = com.nightingale.model.entities.schedule.Task.convert(queue6);
                    List<com.nightingale.model.entities.schedule.Task> convertedTasks16 = com.nightingale.model.entities.schedule.Task.convert(queue16);


                    Map<ModellerConstants.ScheduleType, SystemModel> scheduleMap = modellerMediator.getScheduleMap();

                    Loggers.debugLogger.debug(QUEUE_2_SCHEDULE_3 +" "+convertedTasks2);
                    SystemModel systemModel_2_3 = new SystemModel();
                    systemModel_2_3.initResources().loadTasks(convertedTasks2, systemModel_2_3.MAX_CONNECTIVITY_FUNCTION);
                    scheduleMap.put(QUEUE_2_SCHEDULE_3, systemModel_2_3);

                    Loggers.debugLogger.debug(QUEUE_6_SCHEDULE_3 +" "+convertedTasks6);
                    SystemModel systemModel_6_3 = new SystemModel();
                    systemModel_6_3.initResources().loadTasks(convertedTasks6, systemModel_6_3.MAX_CONNECTIVITY_FUNCTION);
                    scheduleMap.put(QUEUE_6_SCHEDULE_3, systemModel_6_3);

                    Loggers.debugLogger.debug(QUEUE_16_SCHEDULE_3 +" "+convertedTasks16);
                    SystemModel systemModel_16_3 = new SystemModel();
                    systemModel_16_3.initResources().loadTasks(convertedTasks16, systemModel_16_3.MAX_CONNECTIVITY_FUNCTION);
                    scheduleMap.put(QUEUE_16_SCHEDULE_3, systemModel_16_3);

                    Loggers.debugLogger.debug(QUEUE_2_SCHEDULE_5 +" "+convertedTasks2);
                    SystemModel systemModel_2_5 = new SystemModel();
                    systemModel_2_5.initResources().loadTasks(convertedTasks2, systemModel_2_5.SHORTEST_PATH_FUNCTION);
                    scheduleMap.put(QUEUE_2_SCHEDULE_5, systemModel_2_5);

                    Loggers.debugLogger.debug(QUEUE_6_SCHEDULE_5 +" "+convertedTasks6);
                    SystemModel systemModel_6_5 = new SystemModel();
                    systemModel_6_5.initResources().loadTasks(convertedTasks6, systemModel_6_5.SHORTEST_PATH_FUNCTION);
                    scheduleMap.put(QUEUE_6_SCHEDULE_5, systemModel_6_5);

                    Loggers.debugLogger.debug(QUEUE_16_SCHEDULE_5 +" "+convertedTasks16);
                    SystemModel systemModel_16_5 = new SystemModel();
                    systemModel_16_5.initResources().loadTasks(convertedTasks16, systemModel_16_5.SHORTEST_PATH_FUNCTION);
                    scheduleMap.put(QUEUE_16_SCHEDULE_5, systemModel_16_5);

                 //   return null;
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
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