package com.nightingale.model.entities.schedule.tick;

import com.nightingale.model.entities.schedule.Task;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class LinkTick extends Tick {

    public final Transmission t1, t2;
    public final ProcessorTick firstProcessorTick, secondProcessorTick;

    public LinkTick(ProcessorTick firstProcessorTick, ProcessorTick secondProcessorTick) {
        super(firstProcessorTick.fullDuplexEnabled && secondProcessorTick.fullDuplexEnabled);
        this.firstProcessorTick = firstProcessorTick;
        this.secondProcessorTick = secondProcessorTick;

        this.t1 = new Transmission(firstProcessorTick.processorId, secondProcessorTick.processorId, false);
        this.t2 = new Transmission(secondProcessorTick.processorId, firstProcessorTick.processorId, true);
    }

    public boolean isBusy(int src) {
        if (!firstProcessorTick.ioAllowed(src) || !secondProcessorTick.ioAllowed(src))
            return true;
        if (!fullDuplexEnabled)
            return t1.transmitTask != null || t2.transmitTask != null;
        return !((t1.srcId == src) ?
                        t1.transmitTask == null : t2.transmitTask == null);
    }

    public Transmission setTransmission(Task task, int src) {
        if (isBusy(src))
            throw new IllegalArgumentException("Link " + t1.srcId + "->" + t1.dstId + " is not available");
        Transmission transmission = t1.srcId == src ? t1 : t2;
        transmission.transmitTask = task;
        return transmission;
    }

    @Override
    public String toString() {
        return "{" + (t1.transmitTask == null ? "  " : "T" + t1.transmitTask.id)
                + "/" + (t2.transmitTask == null ? "  " : "T" + t2.transmitTask.id) + "}";
    }

    public class Transmission {
        public final int srcId, dstId;
        public final boolean isReverse;
        private Task transmitTask;

        public Transmission(int srcId, int dstId, boolean isReverse) {
            this.srcId = srcId;
            this.dstId = dstId;
            this.isReverse = isReverse;
        }

        public Task getTask() {
            return transmitTask;
        }

        @Override
        public String toString() {
            String id = transmitTask == null ? "" : "T" + transmitTask.id;
            return "{" +
                    id +
                    '}';
        }
    }
}
