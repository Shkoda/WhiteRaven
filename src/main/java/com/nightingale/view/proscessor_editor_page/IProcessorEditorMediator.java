package com.nightingale.view.proscessor_editor_page;

import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorMediator {
    void initTools();

    public void createProcessor(double x, double y);

    public void createLink(Node firstProcessorNode, Node secondProcessorNode);

    Node addProcessorView(ProcessorVO processorVO);

    Node addLinkView(ProcessorLinkVO linkVO);

    Node getSelected();

    void turnOffAllSelection();

    void turnOnSelection(Node node);

    Node getLinkStart();

    void setLinkStart(Node node);

}
