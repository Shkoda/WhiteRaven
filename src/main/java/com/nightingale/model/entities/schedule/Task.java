package com.nightingale.model.entities.schedule;

import com.nightingale.model.entities.graph.AcyclicDirectedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class Task {
    private static final int UNDEFINED = -1;
    public final int id;
    public final double weight;
    public final List<Task> parents;
    private int startTime, finishTime;
    /**
     * key - child task, value - transmission time
     */
    public final Map<Task, Double> childrenMap;


    public Task(int id, double weight) {
        this.weight = weight;
        this.id = id;
        parents = new ArrayList<>();
        startTime = UNDEFINED;
        finishTime = UNDEFINED;
        childrenMap = new HashMap<>();
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

    public static List<Task> convert(AcyclicDirectedGraph graph, List<AcyclicDirectedGraph.Node> input) {
        Map<Integer, Task> convertedTasks = new HashMap<>();

        input.stream().forEach(i -> convertedTasks.put(i.getId(), new Task(i.getId(), i.getWeight())));

        List<Task> result = new ArrayList<>();
        input.stream().forEach(i -> {
            Task current = convertedTasks.get(i.getId());
            i.getParentsIds().forEach(p -> current.parents.add(convertedTasks.get(p)));
            i.getChildren().forEach(c -> {
                int childId = c.getId();
                int currentId = i.getId();
                current.childrenMap.put(convertedTasks.get(childId), graph.getConnection(currentId, childId).getWeight());
            });
            result.add(current);
        });
        return new ArrayList<>(result);
    }

    public static List<Task> copy(List<Task> original) {
        //key - task id, value - task
        Map<Integer, Task> tempCopyMap = new HashMap<>();

        original.stream().forEach(originalTask -> tempCopyMap.put(originalTask.id, new Task(originalTask.id, originalTask.weight)));
        List<Task> result = new ArrayList<>();
        original.stream().forEach(originalTask -> {
            Task current = tempCopyMap.get(originalTask.id);
            originalTask.parents
                    .forEach(originalParent -> current.parents.add(tempCopyMap.get(originalParent.id)));
            originalTask.childrenMap.keySet()
                    .forEach(originalChild -> {
                        Task copiedChild = tempCopyMap.get(originalChild.id);
                        Double transmissionTime = originalTask.childrenMap.get(originalChild);
                        current.childrenMap.put(copiedChild, transmissionTime);
                    });
            result.add(current);
        });
        return result;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (Double.compare(task.weight, weight) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
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
