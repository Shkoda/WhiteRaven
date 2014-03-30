package com.nightingale.model.entities.schedule;

import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.model.entities.schedule.resourse.LinkResource;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.graph.Connection;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public final Map<Integer, ProcessorResource> processorTimes;
    public final Map<Integer, LinkResource> linkTimes;


    public SystemModel(Graph<ProcessorModel, ProcessorLinkModel> graph) {
        pidToColumnNumberMap = new HashMap<>();
        columnNumberToPidMap = new HashMap<>();
        processorTimes = new HashMap<>();
        taskOnProcessorsMap = new HashMap<>();
        vertexes = graph.getVertexIdMap();
        linkTimes = new HashMap<>();

        int id = 0;

        for (ProcessorModel processorModel : graph.getVertexes()) {
            ProcessorResource processorResource = new ProcessorResource(processorModel);
            pidToColumnNumberMap.put(processorModel.getId(), id);
            columnNumberToPidMap.put(id, processorModel.getId());
            processorTimes.put(processorResource.id, processorResource);
            id++;
        }

        graph.getConnections().stream()
                .forEach(link -> {
                    LinkResource linkResource = new LinkResource(link, processorTimes.get(link.getFirstVertexId()),
                            processorTimes.get(link.getSecondVertexId()));
                    linkTimes.put(link.getId(), linkResource);
                    processorTimes.get(link.getFirstVertexId()).increaseConnectivity();
                    processorTimes.get(link.getSecondVertexId()).increaseConnectivity();
                });

        paths = new Paths(graph);
    }

    public int getDuration() {
        return processorTimes.values().size() == 0 ? 0 : processorTimes.values().stream().mapToInt(processor -> processor.earliestAvailableStartTime(0)).max().getAsInt();
    }

    public void loadTasks(List<Task> queue) {
        queue.stream().forEach(this::loadTask);
    }

    private void loadTask(Task task) {
        int minimalStartTime = task.getMinimalStartTime();
        ProcessorResource processor = selectBestProcessor(getAvailableProcessors(minimalStartTime));
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
    private int transmitParentData(List<Task> parents, ProcessorResource processor) {
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
            if (!processorTimes.get(connection.getOtherVertexId(processorSrcId)).loadedTasks.contains(finishedTask)){
                LinkResource linkResource = linkTimes.get(connection.getId());
                int transmissionTime = (int) Math.ceil(finishedTask.weight / connection.getWeight());
                minimalTransmitStart = linkResource.transmitTask(finishedTask, minimalTransmitStart, transmissionTime, processorSrcId) + 1;
            }

            processorSrcId = connection.getOtherVertexId(processorSrcId);

        }
        return minimalTransmitStart;
    }

    private int defineStartTime(int minimalStartTime, ProcessorResource processorResource) {
        return processorResource.earliestAvailableStartTime(minimalStartTime);
    }

    private ProcessorResource selectBestProcessor(List<ProcessorResource> availableProcessors) {        //todo write comparators
        availableProcessors.sort((p1, p2) -> -(p1.getConnectivity().compareTo(p2.getConnectivity())));
        return availableProcessors.get(0);
    }

    private List<ProcessorResource> getAvailableProcessors(int timeMoment) {
        ArrayList<ProcessorResource> availableNow = processorTimes.values().stream()
                .filter(processorTime -> processorTime.earliestAvailableStartTime(timeMoment) == timeMoment)
                .collect(Collectors.toCollection(ArrayList::new));
        if (availableNow.isEmpty()) {
            Map<Integer, ProcessorResource> resources = new HashMap<>();
            processorTimes.values().parallelStream().forEach(processorTime -> resources.put(processorTime.earliestAvailableStartTime(timeMoment), processorTime));
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

        List<Task> convertedTasks = Task.convert(queue);
        System.out.println(convertedTasks);
        SystemModel systemModel = new SystemModel(mpp);

        systemModel.loadTasks(convertedTasks);

    }


}





































