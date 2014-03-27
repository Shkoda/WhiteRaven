package com.nightingale.command.schedule;

import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.AcyclicDirectedGraph;
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

    public final List<ProcessorTime> processorTimes;

    public SystemModel(Graph<ProcessorModel, ProcessorLinkModel> graph) {
        pidToColumnNumberMap = new HashMap<>();
        columnNumberToPidMap = new HashMap<>();
        processorTimes = new ArrayList<>();

        int id = 0;

        for (ProcessorModel processorModel : graph.getVertexes()) {
            ProcessorTime processorTime = new ProcessorTime(processorModel);
            pidToColumnNumberMap.put(processorModel.getId(), id);
            columnNumberToPidMap.put(id, processorModel.getId());
            processorTimes.add(processorTime);
            id++;
        }
    }

    public void loadTasks(List<Task> queue) {
        queue.stream().forEach(this::loadTask);
    }

    private void loadTask(Task task) {
        int minimalStartTime = task.getMinimalStartTime();
        ProcessorTime processor = selectBestProcessor(getAvailableProcessors(minimalStartTime));
        int startTime = defineStartTime(minimalStartTime, processor);
        processor.loadTask(task, startTime);

    }

    private int defineStartTime(int minimalStartTime, ProcessorTime processorTime) {
        return processorTime.willBeFree(minimalStartTime);
    }

    private ProcessorTime selectBestProcessor(List<ProcessorTime> availableProcessors) {
        //todo
        return availableProcessors.get(0);
    }

    private List<ProcessorTime> getAvailableProcessors(int timeMoment) {
        ArrayList<ProcessorTime> availableNow = processorTimes.stream()
                .filter(processorTime -> processorTime.isFree(timeMoment))
                .collect(Collectors.toCollection(ArrayList::new));
        if (availableNow.isEmpty()) {
            Map<Integer, ProcessorTime> resources = new HashMap<>();
            processorTimes.parallelStream().forEach(processorTime -> resources.put(processorTime.willBeFree(timeMoment), processorTime));
            availableNow.add(resources.get(resources.keySet().stream().min(Integer::compare).get()));
        }

        return availableNow;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        processorTimes.stream().forEach(time -> builder.append(time).append("\n"));
        return builder.toString();
    }

    public static void main(String[] args) {
        Graph<ProcessorModel, ProcessorLinkModel> mpp = new Graph<>(ProcessorModel.class, ProcessorLinkModel.class, false);
        mpp.addVertex();
        mpp.addVertex();
        mpp.addVertex();
        mpp.addVertex();
        mpp.addVertex();
        mpp.linkVertexes(1, 2);
        mpp.linkVertexes(2, 3);
        mpp.linkVertexes(3, 1);
        mpp.linkVertexes(3, 4);
        mpp.linkVertexes(4, 5);


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
        System.out.println(systemModel + "\n");

        Paths paths = new Paths(mpp);
        System.out.println(paths);


    }


}





































