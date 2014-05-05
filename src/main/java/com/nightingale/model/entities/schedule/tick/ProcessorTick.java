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
    private int receiveOperationNumber, sendOperationNumber;

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

    public boolean ioAllowed(int srcId) {
        boolean isSender = srcId == processorId;

        return isSender ? sendOperationNumber < physicalLinkNumber :
                receiveOperationNumber < physicalLinkNumber;

    }

    /**
     * @param srcId used for comparing with this.id.
     *              If this.id==srcId sendOperationNumber++
     *              else receiveOperationNumber++
     */
    public void addIOHandling(int srcId) {
        if (!ioAllowed(srcId))
            throw new IllegalArgumentException("P" + processorId + ": all io ports are busy");
        boolean isSender = srcId == processorId;

        if (isSender)
            sendOperationNumber++;
        else
            receiveOperationNumber++;

//        ioOperationsNumber++;
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
