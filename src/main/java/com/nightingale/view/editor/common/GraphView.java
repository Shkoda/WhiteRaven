package com.nightingale.view.editor.common;

import com.nightingale.model.entities.Connection;
import com.nightingale.model.entities.Graph;
import com.nightingale.model.entities.Vertex;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 16.03.14.
 */
public interface GraphView<V extends Vertex, C extends Connection> {
    Pane getView();

    GraphMediator getGraphMediator();

    Node addVertexView(V vertex);

    Node addConnectionView(C connection, final Node firstNode, final Node secondNode);
}
