package com.nightingale.view.editor.common.handlers.cursor_tool;

import com.nightingale.model.entities.Vertex;
import com.nightingale.view.editor.common.IEditorView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowVertexInfoHandler implements EventHandler<MouseEvent> {

    private IEditorView editorView;
    private Vertex vertex;

    public ShowVertexInfoHandler(IEditorView editorView, Vertex vertex) {
        this.editorView = editorView;
        this.vertex = vertex;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        editorView.showVertexInfoPane(vertex);
    }
}
