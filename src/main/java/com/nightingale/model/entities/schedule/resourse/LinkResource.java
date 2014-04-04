package com.nightingale.model.entities.schedule.resourse;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.tick.LinkTick;
import com.nightingale.model.mpp.ProcessorLinkModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class LinkResource extends SystemResource<LinkTick> {

    public final int firstProcessorId, secondProcessorId;
    public final ProcessorResource firstProcessor, secondProcessor;
    public final Map<Task, TransmissionDescription> t1TransmittedTasks, t2TransmittedTasks;

    public LinkResource(ProcessorLinkModel linkModel, ProcessorResource firstProcessor, ProcessorResource secondProcessor, SystemModel systemModel) {
        super(linkModel.getId(), linkModel.getName(), linkModel.getWeight(), linkModel.isFullDuplexEnabled(), systemModel);

        this.firstProcessor = firstProcessor;
        this.secondProcessor = secondProcessor;

        firstProcessorId = linkModel.getFirstVertexId();
        secondProcessorId = linkModel.getSecondVertexId();

        t1TransmittedTasks = new HashMap<>();
        t2TransmittedTasks = new HashMap<>();

        for (int i = 0; i < SystemModel.defaultTickNumber; i++)
            resourceTicks.add(new LinkTick(firstProcessor.getTick(i), secondProcessor.getTick(i)));
    }

    /**
     * @return transmission finish time
     */
    public int transmitTask(Task task, int minStartTime, int transmitTime, int src) {
        int startTime = intervalAvailable(minStartTime, minStartTime + transmitTime - 1, src) ?
                minStartTime :
                earliestAvailableStartTime(minStartTime, transmitTime, src);
        int finishTime = startTime + transmitTime - 1;
        boolean useReverseTransmission = false;

        for (int i = startTime; i <= finishTime; i++) {
            useReverseTransmission = resourceTicks.get(i).setTransmission(task, src).isRevers;
            firstProcessor.getTick(i).addIOHandling();
            secondProcessor.getTick(i).addIOHandling();
        }

        Map<Task, TransmissionDescription> descriptionMap = useReverseTransmission ? t2TransmittedTasks : t1TransmittedTasks;
        descriptionMap.put(task, new TransmissionDescription(startTime, finishTime));
        ProcessorResource receiver = useReverseTransmission? firstProcessor: secondProcessor;

        if (receiver.loadedTasks.containsKey(task))
            throw new IllegalArgumentException("T"+task.id+" is available on "+receiver.name+" since "+receiver.loadedTasks.get(task));
        receiver.loadedTasks.put(task, finishTime + 1);
//        secondProcessor.loadedTasks.put(task, finishTime + 1);
        return finishTime;
    }

    @Override
    public void increaseResourceTicsNumber(int toDuration) {
        int currentSize = resourceTicks.size();
        int increase = toDuration - currentSize;
        for (int i = 0; i < increase; i++)
            resourceTicks.add(new LinkTick(firstProcessor.getTick(i + currentSize), secondProcessor.getTick(i + currentSize)));
    }


    //--------------private api------------------

    private int earliestAvailableStartTime(int minimalStartTime, int transmitLength, int src) {
        while (!intervalAvailable(minimalStartTime, transmitLength, src))
            minimalStartTime++;
        return minimalStartTime;
    }

    private boolean intervalAvailable(int start, int transmitLength, int src) {
        if (start + transmitLength >= resourceTicks.size())
            systemModel.increaseResourceTime((start + transmitLength) * 3 / 2);
        for (int i = start; i < start + transmitLength; i++)
            if (!(!resourceTicks.get(i).isBusy(src) && firstProcessor.getTick(i).ioAllowed() && secondProcessor.getTick(i).ioAllowed()))
                return false;
        return true;
    }

    public class TransmissionDescription {
        public final int startTime, finishTime, duration;

        public TransmissionDescription(int startTime, int finishTime) {
            this.startTime = startTime;
            this.finishTime = finishTime;
            duration = finishTime - startTime + 1;
        }

        @Override
        public String toString() {
            return "TransmissionDescription{" +
                    "startTime=" + startTime +
                    ", finishTime=" + finishTime +
                    ", duration=" + duration +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "LinkTime{" + firstProcessorId + ":" + secondProcessorId + "}\t" +
                resourceTicks +
                '}';
    }


}
