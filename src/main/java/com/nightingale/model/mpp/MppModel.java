package com.nightingale.model.mpp;

import com.google.inject.Singleton;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MppModel implements IMppModel {
    private AtomicInteger processorIdGenerator = new AtomicInteger(0);
    private AtomicInteger linkIdGenerator = new AtomicInteger(0);

    private Map<Integer, ProcessorModel> processors;
    private Map<Integer, ProcessorLinkModel> links;


    public MppModel() {
        processors = new HashMap<>();
        links = new HashMap<>();
    }

    @Override
    public ProcessorModel addProcessor() {
        int id = processorIdGenerator.incrementAndGet();
        ProcessorModel processor = new ProcessorModel();
        processor.update(id);
        processors.put(id, processor);
        return processor;
    }

    @Override
    public void removeProcessor(int processorId) {
        processors.remove(processorId);
        List<Integer> connectedLinks = new ArrayList<>();
        for (Map.Entry<Integer, ProcessorLinkModel> kv : links.entrySet()) {
            ProcessorLinkModel linkVO = kv.getValue();
            if (linkVO.getFirstVertexId() == processorId || linkVO.getSecondVertexId() == processorId)
                connectedLinks.add(kv.getKey());
        }
        links.keySet().removeAll(connectedLinks);
    }


    @Override
    public ProcessorLinkModel linkProcessors(int firstProcessorId, int secondProcessorId) {
        int id = linkIdGenerator.incrementAndGet();
        ProcessorLinkModel link = new ProcessorLinkModel();
        link.update(id, firstProcessorId, secondProcessorId);
        links.put(id, link);

        return link;
    }

    @Override
    public boolean areConnected(int firstProcessorId, int secondProcessorId) {
        for (ProcessorLinkModel linkVO : links.values())
            if (linkVO.getFirstVertexId() == firstProcessorId && linkVO.getSecondVertexId() == secondProcessorId ||
                    linkVO.getSecondVertexId() == firstProcessorId && linkVO.getFirstVertexId() == secondProcessorId)
                return true;
        return false;
    }

    @Override
    public void removeLink(int linkId) {
        links.remove(linkId);
    }


    @Override
    public Collection<ProcessorModel> getProcessors() {
        return processors.values();
    }

    @Override
    public Collection<ProcessorLinkModel> getLinks() {
        return links.values();
    }

    @Override
    public int getMaxProcessorId() {
        return processorIdGenerator.get();
    }

    @Override
    public String toString() {
        return "MppModel{" +
                "processors=" + processors +
                ", links=" + links +
                '}';
    }
}
