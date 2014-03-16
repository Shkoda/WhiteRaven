package com.nightingale.view.proscessor_editor_page;

import com.nightingale.view.utils.NodeType;
import com.nightingale.utils.Tuple;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.scene.Node;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorMediator {
    void initTools();

    public void createProcessor(double x, double y);

    public void tryLinking(Node firstProcessorNode, Node secondProcessorNode);

    Node addProcessorView(ProcessorModel processorModel);

    Node addLinkView(ProcessorLinkModel linkVO, final Node firstProcessorNode, final Node secondProcessorNode);

    Tuple<Node, NodeType> getSelected();

    void turnOffAllSelection();

    void turnOnSelection(Node node, NodeType nodeType);

    Node getLinkStart();

    void setLinkStart(Node node);

}
