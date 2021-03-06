package com.nightingale.model.entities.schedule.resourse;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.tick.ProcessorTick;
import com.nightingale.model.mpp.ProcessorModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class ProcessorResource extends SystemResource<ProcessorTick> {

    public final int physicalLinkNumber;
    public final boolean hasIO;
    /**
     * value - when task will be available for others
     */
    public final Map<Task, Integer> loadedTasks;
    /**
     * value - when task will be available for others
     */
    public final Map<Task, Integer> executedTasks;
    private int connectivity;

    public ProcessorResource(ProcessorModel processorModel, SystemModel systemModel) {
        super(processorModel.getId(), processorModel.getName(), processorModel.getWeight(), processorModel.isFullDuplexEnabled(), systemModel);

        physicalLinkNumber = processorModel.getPhysicalLinkNumber();
        hasIO = processorModel.isHasIO();
        loadedTasks = new HashMap<>();
        executedTasks = new HashMap<>();
        for (int i = 0; i < SystemModel.defaultTickNumber; i++)
            resourceTicks.add(new ProcessorTick(id, physicalLinkNumber, isFullDuplex));
    }

    public void loadTask(Task task, int startTime) {
        int executionTime = (int) Math.ceil(task.weight / performance);
        int finishTime = startTime + executionTime - 1;

        if (finishTime >= resourceTicks.size())
            systemModel.increaseResourceTime(finishTime * 3 / 2);

        task.setStartTime(startTime).setFinishTime(finishTime);
        for (int i = startTime; i <= finishTime; i++)
            resourceTicks.get(i).setTask(task);
        executedTasks.put(task, finishTime + 1);
        loadedTasks.put(task, finishTime + 1);
    }

    public int earliestAvailableStartTime(int minimalStartTime) {
        for (int i = resourceTicks.size() - 1; i >= minimalStartTime; i--) {
            if (!resourceTicks.get(i).isFree())
                return i + 1;
        }
        return minimalStartTime;
    }

    public void increaseConnectivity() {
        connectivity++;
    }

    public Integer getConnectivity() {
        return connectivity;
    }

    @Override
    public void increaseResourceTicsNumber(int toDuration) {
        int increase = toDuration - resourceTicks.size();
        for (int i = 0; i < increase; i++)
            resourceTicks.add(new ProcessorTick(id, physicalLinkNumber, isFullDuplex));
    }

    @Override
    public String toString() {
        return "ProcTime{" +
                name +
                " \t" + resourceTicks +
                '}';
    }
}
