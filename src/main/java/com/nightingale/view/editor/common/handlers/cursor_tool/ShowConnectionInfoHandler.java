package com.nightingale.view.editor.common.handlers.cursor_tool;

import com.nightingale.model.entities.graph.Connection;
import com.nightingale.view.editor.common.IEditorView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowConnectionInfoHandler implements EventHandler<MouseEvent> {

    private IEditorView editorView;
    private Connection connection;

    public ShowConnectionInfoHandler(IEditorView editorView, Connection connection) {
        this.editorView = editorView;
        this.connection = connection;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        editorView.showConnectionInfoPane(connection);
    }
}
