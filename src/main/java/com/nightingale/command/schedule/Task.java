package com.nightingale.command.schedule;

import com.nightingale.model.entities.AcyclicDirectedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class Task {
    public final int id;
    public final double weight;
    public final List<Task> parents;
    private int startTime, finishTime;

    public Task(int id, double weight, List<Task> parents) {
        this.id = id;
        this.weight = weight;
        this.parents = parents;
    }

    public int getMinimalStartTime() {
        return parents.isEmpty()? 0: parents.parallelStream().mapToInt(parent -> parent.finishTime).max().getAsInt() + 1;
    }

    public int getStartTime() {
        return startTime;
    }

    public Task setStartTime(int startTime) {
        this.startTime = startTime;
        return this;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public Task setFinishTime(int finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public static List<Task> convert(List<AcyclicDirectedGraph.Node> input) {
        List<Task> queue = new ArrayList<>();

        Map<Integer, Task> convertedTasks = new HashMap<>();
        input.stream().forEach(node -> {
            List<Integer> parentsIds = node.getParentsIds();

            List<Task> parentTasks = new ArrayList<>();
            convertedTasks.keySet().stream()
                    .filter(parentsIds::contains)
                    .forEach(parentsId -> parentTasks.add(convertedTasks.get(parentsId)));
            Task task = new Task(node.getId(), node.getWeight(), parentTasks);
            queue.add(task);
            convertedTasks.put(task.id, task);
        });
        return queue;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("T").append(id).append("{");
        parents.stream().forEach(parent -> builder.append("T").append(parent.id).append(","));
        builder.append("}");
        return builder.toString();
    }
}
