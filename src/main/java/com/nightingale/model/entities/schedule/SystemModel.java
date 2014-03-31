package com.nightingale.model.entities.schedule;

import com.nightingale.model.entities.schedule.resourse.LinkResource;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.model.entities.graph.Connection;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.utils.Loggers;

import javax.annotation.processing.Processor;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class SystemModel {
    public static final int defaultTickNumber = 10;

    public final BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> MAX_CONNECTIVITY_FUNCTION = new MaxConnectivityFunction();
    public final BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> SHORTEST_PATH_FUNCTION = new ShortestPathFunction();

    public final Map<Integer, ProcessorResource> processorResources;
    public final Map<Integer, LinkResource> linkResources;
    /**
     * key - task id, value - processor
     */
    private Map<Integer, ProcessorResource> taskOnProcessorsMap;
    private Map<Integer, ProcessorModel> vertexes;
    private Paths paths;


    public SystemModel(Graph<ProcessorModel, ProcessorLinkModel> graph) {
        processorResources = new HashMap<>();
        taskOnProcessorsMap = new HashMap<>();
        vertexes = graph.getVertexIdMap();
        linkResources = new HashMap<>();

        graph.getVertexes().stream().forEach(processor -> {
            ProcessorResource processorResource = new ProcessorResource(processor);
            processorResources.put(processorResource.id, processorResource);
        });

        graph.getConnections().stream()
                .forEach(link -> {
                    LinkResource linkResource = new LinkResource(link, processorResources.get(link.getFirstVertexId()),
                            processorResources.get(link.getSecondVertexId()));
                    linkResources.put(link.getId(), linkResource);
                    processorResources.get(link.getFirstVertexId()).increaseConnectivity();
                    processorResources.get(link.getSecondVertexId()).increaseConnectivity();
                });

        paths = new Paths(graph);
    }

    public int getLastOperationFinishTime() {
        return processorResources.values().size() == 0 ? 0 :
                processorResources.values().stream()
                        .mapToInt(processor -> processor.earliestAvailableStartTime(0))
                        .max().getAsInt();
    }

    public SystemModel loadTasks(List<Task> queue, BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> selectProcessorFunction) {
        queue.stream().forEach(task -> loadTask(task, selectProcessorFunction));
        return this;
    }

    private void loadTask(Task task, BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> selectProcessorFunction) {
        int minimalStartTime = task.getMinimalStartTime();
        List<ProcessorResource> availableProcessors = getAvailableProcessors(minimalStartTime);
        ProcessorResource processor = selectProcessorFunction.apply(availableProcessors, task.parents);
        if (!task.parents.isEmpty())
            minimalStartTime = transmitParentData(task.parents, processor);
        int startTime = defineStartTime(minimalStartTime, processor);
        processor.loadTask(task, startTime);
        taskOnProcessorsMap.put(task.id, processor);

        Loggers.debugLogger.debug(this + "\n");
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
        ProcessorResource srcProcessor = taskOnProcessorsMap.get(finishedTask.id);
        int processorSrcId = srcProcessor.id;
        Paths.Path path = paths.getPath(vertexes.get(processorSrcId), vertexes.get(processorDstId));
        int minimalTransmitStart = finishedTask.getFinishTime() + 1;

        for (int i = 0; i < path.length; i++) {
            Connection connection = path.links.get(processorSrcId);
            if (!processorResources.get(connection.getOtherVertexId(processorSrcId)).loadedTasks.contains(finishedTask)) {//if dst processor doesn't contain task
                LinkResource linkResource = linkResources.get(connection.getId());
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
        ArrayList<ProcessorResource> availableNow = processorResources.values().stream()
                .filter(processorTime -> processorTime.earliestAvailableStartTime(timeMoment) == timeMoment)
                .collect(Collectors.toCollection(ArrayList::new));
        if (availableNow.isEmpty()) {
            Map<Integer, ProcessorResource> resources = new HashMap<>();
            processorResources.values().parallelStream().forEach(processorTime -> resources.put(processorTime.earliestAvailableStartTime(timeMoment), processorTime));
            availableNow.add(resources.get(resources.keySet().stream().min(Integer::compare).get()));
        }

        return availableNow;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        processorResources.values().stream().forEach(time -> builder.append(time).append("\n"));
        linkResources.values().stream().forEach(time -> builder.append(time).append("\n"));
        return builder.toString();
    }

    public class MaxConnectivityFunction implements BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> {

        @Override
        public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks) {
            processorResources.sort((p1, p2) -> -(p1.getConnectivity().compareTo(p2.getConnectivity())));
            return processorResources.get(0);
        }
    }

    public class ShortestPathFunction implements BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> {

        @Override
        public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks) {
            Collections.sort(processorResources, (p1, p2) -> transmitRating(p1, tasks).compareTo(transmitRating(p2, tasks)));
            return processorResources.get(0);
        }

        private Double transmitRating(ProcessorResource processorResource, List<Task> tasks) {
            if (tasks.isEmpty())
                return 0.0;

            DoubleStream doubleStream = tasks.stream()
                    .mapToDouble(task -> paths.getPath(taskOnProcessorsMap.get(task.id), processorResource).length);

            return processorResource.physicalLinkNumber == 1 ?
                    doubleStream.sum() :
                    doubleStream.max().getAsDouble();
        }
    }
}





































