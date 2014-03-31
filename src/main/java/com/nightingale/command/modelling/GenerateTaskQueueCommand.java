package com.nightingale.command.modelling;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Nightingale on 23.03.2014.
 */
public class GenerateTaskQueueCommand extends Service<List<AcyclicDirectedGraph.Node>> {
    public Consumer<AcyclicDirectedGraph> consumer;
    public AcyclicDirectedGraph graph;
    public boolean  useIncreaseOrder;

    @Override
    protected Task<List<AcyclicDirectedGraph.Node>> createTask() {
        return new Task<List<AcyclicDirectedGraph.Node>>() {
            @Override
            protected List<AcyclicDirectedGraph.Node> call() throws Exception {

//                List<com.nightingale.model.entities.schedule.Task> convertedTasks = com.nightingale.model.entities.schedule.Task.convert(queue);
//                SystemModel systemModel = new SystemModel(DataManager.getMppModel());
//                systemModel.loadTasks(convertedTasks, systemModel.SHORTEST_PATH_FUNCTION);
//                System.out.println(systemModel);

                return graph.getTaskQueue(consumer, useIncreaseOrder);
            }
        };
    }
}