package com.nightingale.model.entities.schedule;

import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.utils.Loggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class Task {
    private static final int UNDEFINED = -1;
    public final int id;
    public final double weight;
    public final List<Task> parents;
    private int startTime, finishTime;

    public Task(int id, double weight, List<Task> parents) {
        this.id = id;
        this.weight = weight;
        this.parents = parents;
        startTime = UNDEFINED;
        finishTime = UNDEFINED;
    }

    public Task(int id, double weight) {
        this.weight = weight;
        this.id = id;
        parents = new ArrayList<>();
        startTime = UNDEFINED;
        finishTime = UNDEFINED;
    }

    public int getMinimalStartTime() {
        return parents.isEmpty() ? 0 : parents.parallelStream().mapToInt(parent -> parent.finishTime).max().getAsInt() + 1;
    }

    public int getStartTime() {
        return startTime;
    }

    public Task setStartTime(int startTime) {
        if (this.startTime != UNDEFINED)
            throw new IllegalArgumentException("startTime=" + this.startTime + " drop " + startTime);
        this.startTime = startTime;
        return this;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public Task setFinishTime(int finishTime) {
        if (this.finishTime != UNDEFINED)
            throw new IllegalArgumentException("finishTime=" + this.finishTime + " drop " + finishTime);
        this.finishTime = finishTime;
        return this;
    }

    public static List<Task> convert(List<AcyclicDirectedGraph.Node> input) {
        Map<Integer, Task> convertedTasks = new HashMap<>();

        input.stream().forEach(i -> convertedTasks.put(i.getId(), new Task(i.getId(), i.getWeight())));

        input.stream().forEach(i -> {
            Task current = convertedTasks.get(i.getId());
            i.getParentsIds().forEach(p -> current.parents.add(convertedTasks.get(p)));
        });
        return new ArrayList<>(convertedTasks.values());
    }

    public static List<Task> copy(List<Task> input) {
        Map<Integer, Task> copy = new HashMap<>();

        input.stream().forEach(i -> copy.put(i.id, new Task(i.id, i.weight)));

        input.stream().forEach(i -> {
            Task current = copy.get(i.id);
            i.parents.forEach(p -> current.parents.add(copy.get(p.id)));
        });
        return new ArrayList<>(copy.values());
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
