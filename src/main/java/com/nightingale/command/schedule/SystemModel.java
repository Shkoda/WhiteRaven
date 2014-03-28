package com.nightingale.command.schedule;

import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.AcyclicDirectedGraph;
import com.nightingale.model.entities.Connection;
import com.nightingale.model.entities.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;

import javax.annotation.processing.Processor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class SystemModel {
    public static final int defaultTickNumber = 10;
    private Map<Integer, Integer> pidToColumnNumberMap;
    private Map<Integer, Integer> columnNumberToPidMap;
    private Map<Integer, ProcessorModel> vertexes;
    /**
     * key - task id, value - processor id
     */
    private Map<Integer, Integer> taskOnProcessorsMap;
    private Paths paths;
    public final Map<Integer, ProcessorTime> processorTimes;
    public final Map<Integer, LinkTime> linkTimes;


    public SystemModel(Graph<ProcessorModel, ProcessorLinkModel> graph) {
        pidToColumnNumberMap = new HashMap<>();
        columnNumberToPidMap = new HashMap<>();
        processorTimes = new HashMap<>();
        taskOnProcessorsMap = new HashMap<>();
        vertexes = graph.getVertexIdMap();
        linkTimes = new HashMap<>();

        int id = 0;

        for (ProcessorModel processorModel : graph.getVertexes()) {
            ProcessorTime processorTime = new ProcessorTime(processorModel);
            pidToColumnNumberMap.put(processorModel.getId(), id);
            columnNumberToPidMap.put(id, processorModel.getId());
            processorTimes.put(processorTime.id, processorTime);
            id++;
        }

        graph.getConnections().stream()
                .forEach(link -> {
                    LinkTime linkTime = new LinkTime(link, processorTimes.get(link.getFirstVertexId()),
                            processorTimes.get(link.getSecondVertexId()));
                    linkTimes.put(link.getId(), linkTime);
                    processorTimes.get(link.getFirstVertexId()).increaseConnectivity();
                    processorTimes.get(link.getSecondVertexId()).increaseConnectivity();
                });

        paths = new Paths(graph);
    }

    public void loadTasks(List<Task> queue) {
        queue.stream().forEach(this::loadTask);
    }

    private void loadTask(Task task) {
        int minimalStartTime = task.getMinimalStartTime();
        ProcessorTime processor = selectBestProcessor(getAvailableProcessors(minimalStartTime));
        if (!task.parents.isEmpty())
            minimalStartTime = transmitParentData(task.parents, processor);
        int startTime = defineStartTime(minimalStartTime, processor);
        processor.loadTask(task, startTime);
        taskOnProcessorsMap.put(task.id, processor.id);
        System.out.println(this + "\n");
    }

    /**
     * @return minimum processor load time
     */
    private int transmitParentData(List<Task> parents, ProcessorTime processor) {
        int minimalProcessorLoadTime = 0;
        for (Task parent : parents)
            if (!processor.loadedTasks.contains(parent))
                minimalProcessorLoadTime = Math.max(minimalProcessorLoadTime, transmitData(parent, processor.id));
        return minimalProcessorLoadTime;
    }

    /**
     * @return minimum processor load time
     */
    private int transmitData(Task finishedTask, int processorDstId) {
        int processorSrcId = taskOnProcessorsMap.get(finishedTask.id);
        Paths.Path path = paths.getPath(vertexes.get(processorSrcId), vertexes.get(processorDstId));
        int minimalTransmitStart = finishedTask.getFinishTime() + 1;

        for (int i = 0; i < path.length; i++) {
            Connection connection = path.links.get(processorSrcId);
            int transmissionTime = (int) Math.ceil(finishedTask.weight / connection.getWeight());
            LinkTime linkTime = linkTimes.get(connection.getId());
            minimalTransmitStart = linkTime.transmitTask(finishedTask, minimalTransmitStart, transmissionTime, processorSrcId) + 1;
            processorSrcId = connection.getOtherVertexId(processorSrcId);

        }
        return minimalTransmitStart;
    }

    private int defineStartTime(int minimalStartTime, ProcessorTime processorTime) {
        return processorTime.willBeFree(minimalStartTime);
    }

    private ProcessorTime selectBestProcessor(List<ProcessorTime> availableProcessors) {        //todo write comparators
        availableProcessors.sort((p1, p2) -> -(p1.getConnectivity().compareTo(p2.getConnectivity())));
        return availableProcessors.get(0);
    }

    private List<ProcessorTime> getAvailableProcessors(int timeMoment) {
        ArrayList<ProcessorTime> availableNow = processorTimes.values().stream()
                .filter(processorTime -> processorTime.willBeFree(timeMoment) == timeMoment)
                .collect(Collectors.toCollection(ArrayList::new));
        if (availableNow.isEmpty()) {
            Map<Integer, ProcessorTime> resources = new HashMap<>();
            processorTimes.values().parallelStream().forEach(processorTime -> resources.put(processorTime.willBeFree(timeMoment), processorTime));
            availableNow.add(resources.get(resources.keySet().stream().min(Integer::compare).get()));
        }

        return availableNow;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        processorTimes.values().stream().forEach(time -> builder.append(time).append("\n"));
        linkTimes.values().stream().forEach(time -> builder.append(time).append("\n"));
        return builder.toString();
    }

    public static void main(String[] args) {
        Graph<ProcessorModel, ProcessorLinkModel> mpp = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
        mpp.addVertex();
        mpp.addVertex();
        mpp.addVertex();
        //  mpp.addVertex();
        //   mpp.addVertex();
        mpp.linkVertexes(1, 2);
        mpp.linkVertexes(2, 3);
   //     mpp.linkVertexes(3, 1);
        //   mpp.linkVertexes(3, 4);
        //    mpp.linkVertexes(4, 5);


        Graph<TaskModel, TaskLinkModel> taskGraph = new Graph<>(TaskModel.class, TaskLinkModel.class, true);
        taskGraph.addVertex();
        taskGraph.addVertex();
        taskGraph.addVertex();
        taskGraph.addVertex();
        taskGraph.addVertex();
        taskGraph.addVertex();

        taskGraph.linkVertexes(1, 2);
        taskGraph.linkVertexes(3, 2);
        taskGraph.linkVertexes(3, 4);
        taskGraph.linkVertexes(3, 6);
        taskGraph.linkVertexes(5, 4);


        List<AcyclicDirectedGraph.Node> queue = taskGraph.getAcyclicDirectedGraph().getTaskQueue(new NodesAfterCurrentConsumer(), false);

        List<com.nightingale.command.schedule.Task> convertedTasks = com.nightingale.command.schedule.Task.convert(queue);
        System.out.println(convertedTasks);
        SystemModel systemModel = new SystemModel(mpp);

        systemModel.loadTasks(convertedTasks);

    }


}





































