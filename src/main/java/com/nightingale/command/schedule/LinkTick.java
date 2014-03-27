package com.nightingale.command.schedule;

import com.nightingale.model.mpp.ProcessorLinkModel;

/**
 * Created by Nightingale on 26.03.2014.
 */
public class LinkTick {
    public final int firstProcessorId, secondProcessorId;
    public final boolean fullDuplexEnabled;
    public final Transmission t1, t2;

    public final ProcessorTick firstProcessorTick, secondProcessorTick;

    public LinkTick(ProcessorTick firstProcessorTick, ProcessorTick secondProcessorTick) {
        this.firstProcessorTick = firstProcessorTick;
        this.secondProcessorTick = secondProcessorTick;
        firstProcessorId = firstProcessorTick.processorId;
        secondProcessorId = secondProcessorTick.processorId;
        fullDuplexEnabled = firstProcessorTick.fullDuplexEnabled && secondProcessorTick.fullDuplexEnabled;

        this.t1 = new Transmission(firstProcessorId, secondProcessorId);
        this.t2 = new Transmission(secondProcessorId, firstProcessorId);
    }

    public boolean isAvailable(int src) {
        if (!firstProcessorTick.ioAllowed() || !secondProcessorTick.ioAllowed())
            return false;
        if (!fullDuplexEnabled)
            return t1.transmitTask == null && t2.transmitTask == null;
        return (t1.srcId == src) ?
                t1.transmitTask == null : t2.transmitTask == null;
    }

    public void setTransmission(Task task, int src) {
        if (!isAvailable(src))
            throw new IllegalArgumentException("Link " + firstProcessorId + "->" + secondProcessorId + " is not available");
        if (t1.srcId == src)
            t1.transmitTask = task;
        else t2.transmitTask = task;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(t1.transmitTask == null ? "  " : "T" + t1.transmitTask.id).append("/");
        builder.append(t2.transmitTask == null ? "  " : "T" + t2.transmitTask.id).append("}");
        return builder.toString();
    }

    public class Transmission {
        public final int srcId, dstId;
        private Task transmitTask;

        public Transmission(int srcId, int dstId) {
            this.srcId = srcId;
            this.dstId = dstId;
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
