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
    public final List<Task> loadedTasks;

    private int connectivity;


    public ProcessorTime(ProcessorModel processorModel) {
        id = processorModel.getId();
        name = processorModel.getName();
        physicalLinkNumber = processorModel.getPhysicalLinkNumber();
        isFullDuplex = processorModel.isFullDuplexEnabled();
        hasIO = processorModel.isHasIO();
        performance = processorModel.getWeight();

        loadedTasks = new ArrayList<>();
        processorTime = new ArrayList<>();
        for (int i = 0; i < SystemModel.defaultTickNumber; i++)
            processorTime.add(new ProcessorTick(id, physicalLinkNumber, isFullDuplex));
    }

    public void loadTask(Task task, int startTime) {
        if (startTime >= processorTime.size())
            increaseProcessorTime();

        int executionTime = (int) Math.ceil(task.weight / performance);
        int finishTime = startTime + executionTime - 1;

        task.setStartTime(startTime).setFinishTime(finishTime);
        for (int i = startTime; i<=finishTime; i++)
            processorTime.get(i).setTask(task);

        loadedTasks.add(task);
    }

    public void increaseConnectivity(){
        connectivity++;
    }

    public Integer getConnectivity() {
        return connectivity;
    }

    private void increaseProcessorTime() {
        int currentSize = processorTime.size();
        for (int i = 0; i < currentSize; i++)
            processorTime.add(new ProcessorTick(id, physicalLinkNumber, isFullDuplex));
    }

    public ProcessorTick getTick(int number){
        if (number>= processorTime.size())
            increaseProcessorTime();
        return processorTime.get(number);
    }

//    public boolean isFree(int timeMoment) {
//        return processorTime.get(timeMoment).isFree(); //todo check this place
//    }

    public int willBeFree(int minimalTimeMoment) {
        for (int i = processorTime.size()-1; i >= minimalTimeMoment; i--) {
            if (!processorTime.get(i).isFree())
                return i+1;
        }
        return minimalTimeMoment;
    }

    @Override
    public String toString() {
        return "ProcTime{" +
                 name +
                " \t" + processorTime +
                '}';
    }
}
