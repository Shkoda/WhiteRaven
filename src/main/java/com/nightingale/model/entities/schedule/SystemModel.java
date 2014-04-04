package com.nightingale.model.entities.schedule;

import com.nightingale.model.DataManager;
import com.nightingale.model.entities.schedule.resourse.LinkResource;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.model.entities.graph.Connection;
import com.nightingale.model.entities.graph.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.function.BiFunction;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class SystemModel {
    public static final int defaultTickNumber = 10;
    private static final Random random = new Random();

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

    private Graph<ProcessorModel, ProcessorLinkModel> mpp;
    private Graph<TaskModel, TaskLinkModel> taskGraph;

    public SystemModel() {
        this.mpp = DataManager.getMppModel();
        this.taskGraph = DataManager.getTaskGraphModel();
        processorResources = new HashMap<>();
        taskOnProcessorsMap = new HashMap<>();
        vertexes = mpp.getVertexIdMap();
        linkResources = new HashMap<>();

        paths = new Paths(mpp);
    }

    public SystemModel initResources() {
        mpp.getVertexes().stream().forEach(processor -> {
            ProcessorResource processorResource = new ProcessorResource(processor, this);
            processorResources.put(processorResource.id, processorResource);
        });

        mpp.getConnections().stream()
                .forEach(link -> {
                    LinkResource linkResource = new LinkResource(link, processorResources.get(link.getFirstVertexId()),
                            processorResources.get(link.getSecondVertexId()), this);
                    linkResources.put(link.getId(), linkResource);
                    processorResources.get(link.getFirstVertexId()).increaseConnectivity();
                    processorResources.get(link.getSecondVertexId()).increaseConnectivity();
                });
        return this;
    }

    public void increaseResourceTime(int toDuration) {
        processorResources.values().forEach(p -> p.increaseResourceTicsNumber(toDuration));
        linkResources.values().forEach(l -> l.increaseResourceTicsNumber(toDuration));
    }

    public int getLastOperationFinishTime() {
        return processorResources.values().size() == 0 ? 0 :
                processorResources.values().stream()
                        .mapToInt(processor -> processor.earliestAvailableStartTime(0))
                        .max().getAsInt();
    }

    public SystemModel loadTasks(List<Task> queue, BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> selectProcessorFunction) {
        Task.copy(queue).stream().forEach(task -> loadTask(task, selectProcessorFunction));
        return this;
    }


    private void loadTask(Task task, BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> selectProcessorFunction) {
        Loggers.debugLogger.debug("loading T" + task.id);

        int minimalStartTime = task.getMinimalStartTime();
        Loggers.debugLogger.debug("minimalStartTime=" + minimalStartTime);

        List<ProcessorResource> availableProcessors = getAvailableProcessors(minimalStartTime);
        Loggers.debugLogger.debug("availableProcessors=" + availableProcessors);

        ProcessorResource processor = selectProcessorFunction.apply(availableProcessors, task.parents);
        Loggers.debugLogger.debug("processor " + processor.name);
        if (!task.parents.isEmpty())
            minimalStartTime = transmitParentData(task.parents, task, processor);
        Loggers.debugLogger.debug("transmitParentData time=" + minimalStartTime);

        int startTime = defineStartTime(minimalStartTime, processor);
        Loggers.debugLogger.debug("startTime=" + startTime);

        processor.loadTask(task, startTime);
        taskOnProcessorsMap.put(task.id, processor);

        Loggers.debugLogger.debug(this + "\n");
    }

    /**
     * @return minimum processor load time
     */
    private int transmitParentData(List<Task> parents, Task childTask, ProcessorResource processor) {
        int minimalProcessorLoadTime = 0;
        parents.sort((p1, p2) -> Integer.compare(p1.getFinishTime(), p2.getFinishTime()));
        for (Task parent : parents)
            minimalProcessorLoadTime = (processor.loadedTasks.keySet().contains(parent)) ?
                    Math.max(minimalProcessorLoadTime, processor.loadedTasks.get(parent)) :
                    Math.max(minimalProcessorLoadTime, transmitData(parent, childTask, processor.id));
        // if (!processor.loadedTasks.keySet().contains(parent))

        return minimalProcessorLoadTime;
    }

    /**
     * @return minimum processor load time
     */
    private int transmitData(Task parentTask, Task childTask, int dstProcessorId) {
        ProcessorResource srcProcessor = taskOnProcessorsMap.get(parentTask.id);
        int srcProcessorId = srcProcessor.id;

        Paths.Path path = paths.getPath(vertexes.get(srcProcessorId), vertexes.get(dstProcessorId));
        int minimalTransmitStart = parentTask.getFinishTime() + 1;
        double transmissionWeight = taskGraph.getConnection(parentTask.id, childTask.id).getWeight();

        Loggers.debugLogger.debug("T"+parentTask.id+" : "+path);
        for (int i = 0; i < path.length; i++) {

            Connection connection = path.links.get(srcProcessorId);
            ProcessorResource currentDst = processorResources.get(connection.getOtherVertexId(srcProcessorId));
            Integer taskAvailableTime = currentDst.loadedTasks.get(parentTask);

            Loggers.debugLogger.debug("transmitting T" + parentTask.id + " " + srcProcessorId + " --> " + currentDst.id);

            if (taskAvailableTime == null) {//if dst processor doesn't contain task
                LinkResource linkResource = linkResources.get(connection.getId());
                int transmissionTime = (int) Math.ceil(transmissionWeight / connection.getWeight());
                minimalTransmitStart = linkResource.transmitTask(parentTask, minimalTransmitStart, transmissionTime, srcProcessorId) + 1;
            } else {
                minimalTransmitStart = Math.max(minimalTransmitStart, taskAvailableTime);
            }

            Loggers.debugLogger.debug("min start time="+minimalTransmitStart);
            srcProcessorId = currentDst.id;
        }
        return minimalTransmitStart;
    }

    private int defineStartTime(int minimalStartTime, ProcessorResource processorResource) {
        return processorResource.earliestAvailableStartTime(minimalStartTime);
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

    //------------------- support function classes-----------------------

    public class MaxConnectivityFunction implements BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> {
        @Override
        public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks) {
            int maxConnectivity = processorResources.stream()
                    .mapToInt(ProcessorResource::getConnectivity)
                    .max().getAsInt();
            List<ProcessorResource> maxConnectivityProcessors = processorResources.stream()
                    .filter(p -> p.getConnectivity() == maxConnectivity)
                    .collect(Collectors.toList());              //todo check this function
            return maxConnectivityProcessors.get(random.nextInt(maxConnectivityProcessors.size()));
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





































