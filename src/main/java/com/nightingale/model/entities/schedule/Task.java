package com.nightingale.model.entities.schedule;

import com.nightingale.model.entities.graph.AcyclicDirectedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (finishTime != task.finishTime) return false;
        if (id != task.id) return false;
        if (startTime != task.startTime) return false;
        if (Double.compare(task.weight, weight) != 0) return false;
        if (parents != null ? !parents.equals(task.parents) : task.parents != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (parents != null ? parents.hashCode() : 0);
        result = 31 * result + startTime;
        result = 31 * result + finishTime;
        return result;
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
