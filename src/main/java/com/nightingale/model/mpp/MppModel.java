package com.nightingale.model.mpp;

import com.google.inject.Singleton;
import com.nightingale.view.config.Config;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.geometry.Dimension2D;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MppModel implements IMppModel {
    private AtomicInteger processorId = new AtomicInteger(0);
    private AtomicInteger linkId = new AtomicInteger(0);

    private Map<Integer, ProcessorVO> processors;
    private Map<Integer, ProcessorLinkVO> links;

    private int canvasHeight, canvasWidth;

    public MppModel() {
        canvasHeight = Config.CANVAS_HEIGHT;
        canvasWidth = Config.CANVAS_WIDTH;

        processors = new HashMap<>();
        links = new HashMap<>();
    }

    @Override
    public void reset(IMppModel other) {
        processors.clear();
        for (ProcessorVO processorVO : other.getProcessors())
            processors.put(processorVO.getId(), processorVO);
        links.clear();
        for (ProcessorLinkVO linkVO : other.getLinks())
            links.put(linkVO.getId(), linkVO);
        processorId = ((MppModel) other).processorId;
        linkId = ((MppModel) other).linkId;
    }

    @Override
    public ProcessorVO addProcessor() {
        int id = processorId.incrementAndGet();
        ProcessorVO processor = new ProcessorVO();
        processor.update(id);
        processors.put(id, processor);
        return processor;
    }

    @Override
    public void removeProcessor(int processorId) {
        processors.remove(processorId);
    }

    @Override
    public ProcessorVO getProcessor(int processorId) {
        return processors.get(processorId);
    }

    @Override
    public ProcessorLinkVO linkProcessors(int firstProcessorId, int secondProcessorId) {
        int id = linkId.incrementAndGet();
        ProcessorLinkVO link = new ProcessorLinkVO();
        link.update(id, firstProcessorId, secondProcessorId);
        links.put(id, link);
        return link;
    }

    @Override
    public void deleteLink(int linkId) {
        links.remove(linkId);
    }

    @Override
    public ProcessorLinkVO getLink(int linkId) {
        return links.get(linkId);
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
    public void setCanvasDimension(Dimension2D dimension) {
        canvasHeight = (int) dimension.getHeight();
        canvasWidth = (int) dimension.getWidth();
    }

    @Override
    public Dimension2D getCanvasDimension() {
        return new Dimension2D(canvasWidth, canvasHeight);
    }

    @Override
    public String toString() {
        return "MppModel{" +
                "processors=" + processors +
                ", links=" + links +
                ", canvasHeight=" + canvasHeight +
                ", canvasWidth=" + canvasWidth +
                '}';
    }
}
