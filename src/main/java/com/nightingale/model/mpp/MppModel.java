package com.nightingale.model.mpp;

import com.google.inject.Singleton;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MppModel implements IMppModel {
    private AtomicInteger processorIdGenerator = new AtomicInteger(0);
    private AtomicInteger linkIdGenerator = new AtomicInteger(0);

    private Map<Integer, ProcessorVO> processors;
    private Map<Integer, ProcessorLinkVO> links;


    public MppModel() {
        processors = new HashMap<>();
        links = new HashMap<>();
    }

//    @Override
//    public void reset(IMppModel other) {
//
//        processors.clear();
//        for (ProcessorVO processorVO : other.getProcessors())
//            processors.put(processorVO.getId(), processorVO);
//        links.clear();
//        for (ProcessorLinkVO linkVO : other.getLinks())
//            links.put(linkVO.getId(), linkVO);
//
//        MppModel otherMpp = ((MppModel) other);
//        processorIdGenerator = otherMpp.processorIdGenerator;
//        linkIdGenerator = otherMpp.linkIdGenerator;
//    }

    @Override
    public ProcessorVO addProcessor() {
        int id = processorIdGenerator.incrementAndGet();
        ProcessorVO processor = new ProcessorVO();
        processor.update(id);
        processors.put(id, processor);
        return processor;
    }

    @Override
    public void removeProcessor(int processorId) {
        processors.remove(processorId);
        List<Integer> connectedLinks = new ArrayList<>();
        for (Map.Entry<Integer, ProcessorLinkVO> kv : links.entrySet()) {
            ProcessorLinkVO linkVO = kv.getValue();
            if (linkVO.getFirstProcessorId() == processorId || linkVO.getSecondProcessorId() == processorId)
                connectedLinks.add(kv.getKey());
        }
        links.keySet().removeAll(connectedLinks);
    }


    @Override
    public ProcessorLinkVO linkProcessors(int firstProcessorId, int secondProcessorId) {
        int id = linkIdGenerator.incrementAndGet();
        ProcessorLinkVO link = new ProcessorLinkVO();
        link.update(id, firstProcessorId, secondProcessorId);
        links.put(id, link);

        return link;
    }

    @Override
    public boolean areConnected(int firstProcessorId, int secondProcessorId) {
        for (ProcessorLinkVO linkVO : links.values())
            if (linkVO.getFirstProcessorId() == firstProcessorId && linkVO.getSecondProcessorId() == secondProcessorId ||
                    linkVO.getSecondProcessorId() == firstProcessorId && linkVO.getFirstProcessorId() == secondProcessorId)
                return true;
        return false;
    }

    @Override
    public void removeLink(int linkId) {
        links.remove(linkId);
    }


    @Override
    public Collection<ProcessorVO> getProcessors() {
        return processors.values();
    }

    @Override
    public Collection<ProcessorLinkVO> getLinks() {
        return links.values();
    }


    @Override
    public String toString() {
        return "MppModel{" +
                "processors=" + processors +
                ", links=" + links +
                '}';
    }
}
