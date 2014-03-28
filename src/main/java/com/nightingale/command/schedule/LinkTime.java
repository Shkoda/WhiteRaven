package com.nightingale.command.schedule;

import com.nightingale.model.mpp.ProcessorLinkModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class LinkTime {

    public final String name;
    public final int id;
    public final boolean isFullDuplex;
    public final double channelWidth;

    public final int firstProcessorId, secondProcessorId;

    public final List<LinkTick> linkTime;

    public final ProcessorTime firstProcessor, secondProcessor;


    public LinkTime(ProcessorLinkModel linkModel, ProcessorTime firstProcessor, ProcessorTime secondProcessor) {
        this.firstProcessor = firstProcessor;
        this.secondProcessor = secondProcessor;
        id = linkModel.getId();
        name = linkModel.getName();
        firstProcessorId = linkModel.getFirstVertexId();
        secondProcessorId = linkModel.getSecondVertexId();
        isFullDuplex = linkModel.isFullDuplexEnabled();
        channelWidth = linkModel.getWeight();

        linkTime = new ArrayList<>();
        for (int i = 0; i < SystemModel.defaultTickNumber; i++)
            linkTime.add(new LinkTick(firstProcessor.getTick(i), secondProcessor.getTick(i)));
    }

    /**
     * @return transmission finish time
     */
    public int transmitTask(Task task, int minStartTime, int transmitTime, int src) {
        int startTime = startTime(minStartTime, transmitTime, src);
        int finishTime = startTime + transmitTime - 1;

        for (int i = startTime; i <= finishTime; i++) {
            linkTime.get(i).setTransmission(task, src);
            firstProcessor.getTick(i).addIOHandling();
            secondProcessor.getTick(i).addIOHandling();
        }
        secondProcessor.loadedTasks.add(task);
        return finishTime;
    }

    private int startTime(int minimalStartTime, int transmitLength, int src) {
        while (!intervalAvailable(minimalStartTime, transmitLength, src))
            minimalStartTime++;
        return minimalStartTime;
    }

    private boolean intervalAvailable(int start, int transmitLength, int src) {
        if (start + transmitLength >= linkTime.size())
            increaseLinkTime();
        for (int i = start; i < start + transmitLength ; i++)
            if (!(linkTime.get(i).isAvailable(src)&& firstProcessor.getTick(i).ioAllowed()&& secondProcessor.getTick(i).ioAllowed()))
                return false;
        return true;
    }

    private void increaseLinkTime() {
        int currentSize = linkTime.size();
        for (int i = 0; i < currentSize; i++)
            linkTime.add(new LinkTick(firstProcessor.getTick(i + currentSize), secondProcessor.getTick(i + currentSize)));
    }

    public boolean isFree(int timeMoment, int src) {
        return linkTime.get(timeMoment).isAvailable(src); //todo check this place
    }

    public int willBeFree(int minimalTimeMoment, int src) {
        for (int i = minimalTimeMoment; i < linkTime.size(); i++) {
            if (linkTime.get(i).isAvailable(src))
                return i;
        }
        return linkTime.size();
    }

    @Override
    public String toString() {
        return "LinkTime{" +firstProcessorId+":"+secondProcessorId+"}\t"+
                 linkTime +
                '}';
    }


}
