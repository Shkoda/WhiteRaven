package com.nightingale.view.editor.common;

import com.nightingale.model.entities.graph.Connection;
import com.nightingale.model.entities.graph.Vertex;
import com.nightingale.view.ViewablePage;
import javafx.scene.control.ToggleButton;

/**
 * Created by Nightingale on 16.03.14.
 */
public interface IEditorView<V extends Vertex, C extends Connection> extends ViewablePage {
    ToggleButton getCursorButton();

    ToggleButton getAddVertexButton();

    ToggleButton getLinkButton();

    void showVertexInfoPane(V vertex);

    void showConnectionInfoPane(C connection);

    void hideInfoPane();
}
