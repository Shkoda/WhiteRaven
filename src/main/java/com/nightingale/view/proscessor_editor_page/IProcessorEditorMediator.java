package com.nightingale.view.proscessor_editor_page;

import com.nightingale.view.utils.NodeType;
import com.nightingale.view.utils.Tuple;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.scene.Node;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorMediator {
    void initTools();

    public void createProcessor(double x, double y);

    public void tryLinking(Node firstProcessorNode, Node secondProcessorNode);

    Node addProcessorView(ProcessorVO processorVO);

    Node addLinkView(ProcessorLinkVO linkVO, final Node firstProcessorNode, final Node secondProcessorNode);

    Tuple<Node, NodeType> getSelected();

    void turnOffAllSelection();

    void turnOnSelection(Node node, NodeType nodeType);

    Node getLinkStart();

    void setLinkStart(Node node);

}
