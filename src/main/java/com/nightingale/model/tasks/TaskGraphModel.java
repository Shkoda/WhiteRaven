package com.nightingale.model.tasks;

import com.google.inject.Singleton;
import com.nightingale.model.tasks.elements.TaskLinkModel;
import com.nightingale.model.tasks.elements.TaskModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class TaskGraphModel implements ITaskGraphModel {

    private AtomicInteger taskIdGenerator = new AtomicInteger(0);
    private AtomicInteger connectionIdGenerator = new AtomicInteger(0);

    private Map<Integer, TaskModel> tasks;
    private Map<Integer, TaskLinkModel> connections;

    public TaskGraphModel() {
        tasks = new HashMap<>();
        connections = new HashMap<>();
    }

    @Override
    public TaskModel addTask() {
        int id = taskIdGenerator.incrementAndGet();
        TaskModel taskModel = new TaskModel();
        taskModel.update(id);
        tasks.put(id, taskModel);
        return taskModel;
    }

    @Override
    public int getMaxTaskId() {
        return taskIdGenerator.get();
    }

    @Override
    public void removeTask(int taskId) {
//        tasks.remove(taskId);
//        List<Integer> connections = new ArrayList<>();
//        for (Map.Entry<Integer, TaskLinkModel> kv : this.connections.entrySet()) {
//            TaskLinkModel connection = kv.getValue();
//            if (connection.getParentTaskId() == taskId || connection.getChildTaskId() == taskId)
//                connections.add(kv.getKey());
//        }
//        this.connections.keySet().removeAll(connections);
    }

    @Override
    public Collection<TaskModel> getTasks() {
        return tasks.values();
    }

    @Override
    public TaskLinkModel connectTasks(int parentTaskId, int childTaskId) {
        int id = connectionIdGenerator.incrementAndGet();
        TaskLinkModel connection = new TaskLinkModel();
        connection.update(id, parentTaskId, childTaskId);
        connections.put(id, connection);

        return connection;
    }

    @Override
    public void removeConnection(int connectionId) {
        connections.remove(connectionId);
    }

    @Override
    public Collection<TaskLinkModel> getConnections() {
        return connections.values();
    }

    @Override
    public boolean areConnected(int firstProcessorId, int secondProcessorId) {
//        for (TaskLinkModel taskLinkModel : connections.values())
//            if (taskLinkModel.getParentTaskId() == firstProcessorId && taskLinkModel.getChildTaskId() == secondProcessorId ||
//                    taskLinkModel.getChildTaskId() == firstProcessorId && taskLinkModel.getParentTaskId() == secondProcessorId)
//                return true;
        return false;
    }
}
