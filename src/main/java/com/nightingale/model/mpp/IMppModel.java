package com.nightingale.model.mpp;

import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.geometry.Dimension2D;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IMppModel extends Serializable {
    void reset(IMppModel other);

    ProcessorVO addProcessor();

    void removeProcessor(int processorId);

    ProcessorVO getProcessor(int processorId);

    Collection<ProcessorVO> getProcessors();

    ProcessorLinkVO linkProcessors(int firstProcessorId, int secondProcessorId);

    void deleteLink(int linkId);

    ProcessorLinkVO getLink(int linkId);

    Collection<ProcessorLinkVO> getLinks();

    void setCanvasDimension(Dimension2D dimension);

    Dimension2D getCanvasDimension();
}
