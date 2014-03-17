package com.nightingale.view.editor.tasks_editor_page;

import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Tuple;
import com.nightingale.view.editor.common.handlers.connection_tool.LinkToolOnNodeHandler;
import com.nightingale.view.editor.common.handlers.cursor_tool.SelectNodeHandler;
import com.nightingale.view.editor.common.handlers.cursor_tool.ShowVertexInfoHandler;

import com.nightingale.view.utils.NodeType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 09.03.14.
 */
public class TasksEditorMediator implements ITasksEditorMediator{


    @Override
    public void initTools() {

    }

    @Override
    public void createVertex(double x, double y) {

    }

    @Override
    public void tryLinking(Node firstNode, Node secondNode) {

    }

    @Override
    public Node addVertexView(TaskModel vertex) {
        return null;
    }

    @Override
    public Node addConnectionView(TaskLinkModel connection, Node firstNode, Node secondNode) {
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
