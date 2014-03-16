package com.nightingale.view.proscessor_editor_page.mpp;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface IMppView {
    Pane getView();

   // void resetView(Iterable<ProcessorModel> processors, Iterable<ProcessorLinkModel> links);

    Node addProcessorView(ProcessorModel processorModel);

    Node addLinkView(ProcessorLinkModel processorLinkModel, final Node firstProcessorNode, final Node secondProcessorNode);

}
