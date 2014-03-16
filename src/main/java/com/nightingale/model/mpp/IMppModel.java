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

    ProcessorVO addProcessor();

    void removeProcessor(int processorId);

    Collection<ProcessorVO> getProcessors();

    ProcessorLinkVO linkProcessors(int firstProcessorId, int secondProcessorId);

    void removeLink(int linkId);

    Collection<ProcessorLinkVO> getLinks();

    boolean areConnected(int firstProcessorId, int secondProcessorId);
}
