package com.nightingale.model.mpp;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IMppModel extends Serializable {

    ProcessorModel addProcessor();

    int getMaxProcessorId();

    void removeProcessor(int processorId);

    Collection<ProcessorModel> getProcessors();

    ProcessorLinkModel linkProcessors(int firstProcessorId, int secondProcessorId);

    void removeLink(int linkId);

    Collection<ProcessorLinkModel> getLinks();

    boolean areConnected(int firstProcessorId, int secondProcessorId);
}
