package com.nightingale.model.tasks;

import com.nightingale.model.tasks.elements.TaskLinkModel;
import com.nightingale.model.tasks.elements.TaskModel;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ITaskGraphModel extends Serializable {

    TaskModel addTask();

    int getMaxTaskId();

    void removeTask(int taskId);

    Collection<TaskModel> getTasks();

    TaskLinkModel connectTasks(int parentTaskId, int childTaskId);

    void removeConnection(int connectionId);

    Collection<TaskLinkModel> getConnections();

    boolean areConnected(int firstProcessorId, int secondProcessorId);
}
