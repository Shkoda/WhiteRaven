package com.nightingale.model.entities.schedule.tick;

import com.nightingale.model.entities.schedule.Task;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class ProcessorTick extends Tick {
    public final int processorId;
    public final int physicalLinkNumber;
    private Task currentTask;
    private int ioOperationsNumber;

    public ProcessorTick(int processorId, int physicalLinkNumber, boolean fullDuplexEnabled) {
        super(fullDuplexEnabled);
        this.processorId = processorId;
        this.physicalLinkNumber = physicalLinkNumber;
    }

    public void setTask(Task task) {
        if (currentTask != null)
            throw new IllegalArgumentException("current task " + currentTask + " can't be replaced with " + task);

        currentTask = task;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public boolean ioAllowed() {
        return ioOperationsNumber < physicalLinkNumber;
    }

    public void addIOHandling() {
        if (!ioAllowed())
            throw new IllegalArgumentException("P" + processorId + ": all io ports are busy");
        ioOperationsNumber++;
    }

    public boolean isFree() {
        return currentTask == null;
    }

    @Override
    public String toString() {
        String id = currentTask == null ? "  " : "T" + currentTask.id;
        return "{  " +
                id +
                " }";
    }
}
