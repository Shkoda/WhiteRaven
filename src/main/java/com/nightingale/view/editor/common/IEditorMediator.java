package com.nightingale.view.editor.common;

import com.nightingale.model.entities.graph.Connection;
import com.nightingale.model.entities.graph.Vertex;
import com.nightingale.utils.Tuple;
import com.nightingale.view.utils.NodeType;
import javafx.scene.Node;

/**
 * Created by Nightingale on 16.03.14.
 */
public interface IEditorMediator<V extends Vertex, C extends Connection> {
    void initTools();

    public void createVertex(double x, double y);

    public void tryLinking(Node firstNode, Node secondNode);

    Node addVertexView(V vertex);

    Node addConnectionView(C connection, final Node firstNode, final Node secondNode);

    Tuple<Node, NodeType> getSelected();

    void turnOffAllSelection();

    void turnOnSelection(Node node, NodeType nodeType);

    Node getLinkStart();

    void setLinkStart(Node node);
}
