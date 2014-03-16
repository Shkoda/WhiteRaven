package com.nightingale.view.editor.tasks_editor_page;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import com.nightingale.utils.Tuple;
import com.nightingale.view.utils.NodeType;
import javafx.scene.Node;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ITasksEditorMediator {
    void initTools();

    public void createTask(double x, double y);

    public void tryLinking(Node firstProcessorNode, Node secondProcessorNode);

    Node addTaskView(ProcessorModel processorModel);

    Node addLinkView(ProcessorLinkModel linkVO, final Node firstProcessorNode, final Node secondProcessorNode);

    Tuple<Node, NodeType> getSelected();

    void turnOffAllSelection();

    void turnOnSelection(Node node, NodeType nodeType);

    Node getLinkStart();

    void setLinkStart(Node node);

}
