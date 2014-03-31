package com.nightingale.command.generate;

import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.config.Config;

import java.util.Random;

/**
 * Created by Nightingale on 25.03.2014.
 */
public class TaskGraphGenerator {
    private static final Random RANDOM = new Random();
    private static final double xCenter = Config.TASK_CANVAS_WIDTH / 2;
    private static final double yCenter = Config.TASK_CANVAS_HEIGHT / 2;
    private static final double radius = Math.min(Config.TASK_CANVAS_HEIGHT, Config.TASK_CANVAS_WIDTH) / 3;

    public static Graph<TaskModel, TaskLinkModel> generate(int minTaskWeight, int maxTaskWeight, int taskNumber, double connectivity) {
        Graph<TaskModel, TaskLinkModel> graph = new Graph<>(TaskModel.class, TaskLinkModel.class, true);
        addNVertexes(graph, minTaskWeight, maxTaskWeight, taskNumber);
        addLinks(graph, taskNumber, connectivity);
        return graph;
    }

    private static void addLinks(Graph<TaskModel, TaskLinkModel> graph, int taskNumber, double connectivity) {
        while (graph.getConnectivity() > connectivity) {
            int parentId = RANDOM.nextInt(taskNumber) + 1;
            int childId = RANDOM.nextInt(taskNumber) + 1;
            TaskLinkModel result = graph.linkVertexes(parentId, childId);
            if (result == null && !graph.getConnections().isEmpty()) {
                graph.getRandomConnection().increaseWeightByOne();
            }
        }
        Loggers.debugLogger.debug("connectivity = " + graph.getConnectivity());
    }

    private static void addNVertexes(Graph<TaskModel, TaskLinkModel> graph, int minTaskWeight, int maxTaskWeight, int taskNumber) {
        int randomBase = maxTaskWeight - minTaskWeight + 1;
        double angle = 2 * Math.PI / taskNumber;

        for (int i = 0; i < taskNumber; i++) {
            int weight = RANDOM.nextInt(randomBase) + minTaskWeight;
            double x = radius * Math.sin(angle * i) + xCenter;
            double y = radius * Math.cos(angle * i) + yCenter;
            graph.addVertex()
                    .setWeight(weight)
                    .setTranslateX(x)
                    .setTranslateY(y);
        }
    }

}
