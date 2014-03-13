package com.nightingale.view.proscessor_editor_page.mpp;

import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface IMppView {
    Pane getView();

    void resetView(Iterable<ProcessorVO> processors, Iterable<ProcessorLinkVO> links);

    Node addProcessorView(ProcessorVO processorVO);

    void addLinkView(ProcessorLinkVO processorLinkVO);

}
