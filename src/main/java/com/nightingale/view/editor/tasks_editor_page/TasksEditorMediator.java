package com.nightingale.view.editor.tasks_editor_page;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import com.nightingale.utils.Tuple;
import com.nightingale.view.utils.NodeType;
import javafx.scene.Node;

/**
 * Created by Nightingale on 09.03.14.
 */
public class TasksEditorMediator implements ITasksEditorMediator {
    @Override
    public void initTools() {

    }

    @Override
    public void createTask(double x, double y) {

    }

    @Override
    public void tryLinking(Node firstProcessorNode, Node secondProcessorNode) {

    }

    @Override
    public Node addTaskView(ProcessorModel processorModel) {
        return null;
    }

    @Override
    public Node addLinkView(ProcessorLinkModel linkVO, Node firstProcessorNode, Node secondProcessorNode) {
        return null;
    }

    @Override
    public Tuple<Node, NodeType> getSelected() {
        return null;
    }

    @Override
    public void turnOffAllSelection() {

    }

    @Override
    public void turnOnSelection(Node node, NodeType nodeType) {

    }

    @Override
    public Node getLinkStart() {
        return null;
    }

    @Override
    public void setLinkStart(Node node) {

    }
}
