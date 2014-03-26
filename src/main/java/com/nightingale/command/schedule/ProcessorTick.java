package com.nightingale.command.schedule;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class ProcessorTick {

    public final int physicalLinkNumber;
    private Task currentTask;
    private int ioOperationsNumber;

    public ProcessorTick(int physicalLinkNumber) {
        this.physicalLinkNumber = physicalLinkNumber;
    }

    public void setTask(Task task) {
        if (currentTask != null)
            throw new IllegalArgumentException("current task " + currentTask + " can't be replaced with " + task);

        currentTask = task;
    }

    public boolean isFree() {
        return currentTask == null;
    }

    @Override
    public String toString() {
        String id = currentTask==null? "" : "T"+currentTask.id;
        return "{" +
                id +
                '}';
    }
}
