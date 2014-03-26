package com.nightingale.command.schedule;

import com.nightingale.model.mpp.ProcessorModel;

import javax.annotation.processing.Processor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class ProcessorTime {
    public final String name;
    public final int id;
    public final int physicalLinkNumber;
    public final boolean isFullDuplex, hasIO;
    public final double performance;

    public final List<ProcessorTick> processorTime;


    public ProcessorTime(ProcessorModel processorModel) {
        id = processorModel.getId();
        name = processorModel.getName();
        physicalLinkNumber = processorModel.getPhysicalLinkNumber();
        isFullDuplex = processorModel.isFullDuplexEnabled();
        hasIO = processorModel.isHasIO();
        performance = processorModel.getWeight();

        processorTime = new ArrayList<>();
        for (int i = 0; i < SystemModel.defaultTickNumber; i++)
            processorTime.add(new ProcessorTick(physicalLinkNumber));
    }

    public void loadTask(Task task, int startTime) {
        if (startTime >= processorTime.size())
            increaseProcessorTime();

        int executionTime = (int) Math.ceil(task.weight / performance);
        int finishTime = startTime + executionTime - 1;

        task.setStartTime(startTime).setFinishTime(finishTime);
        for (int i = startTime; i<=finishTime; i++)
            processorTime.get(i).setTask(task);
    }

    private void increaseProcessorTime() {
        int currentSize = processorTime.size();
        for (int i = 0; i < currentSize; i++)
            processorTime.add(new ProcessorTick(physicalLinkNumber));
    }

    public boolean isFree(int timeMoment) {
        return processorTime.get(timeMoment).isFree(); //todo check this place
    }

    public int willBeFree(int minimalTimeMoment) {
        for (int i = minimalTimeMoment; i < processorTime.size(); i++) {
            if (processorTime.get(i).isFree())
                return i;
        }
        return processorTime.size();
    }

    @Override
    public String toString() {
        return "ProcessorTime{" +
                "name='" + name + '\'' +
                ", processorTime=\t" + processorTime +
                '}';
    }
}
