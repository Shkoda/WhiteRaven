package com.nightingale.view.editor.common.handlers.cursor_tool;

import com.nightingale.model.entities.Connection;
import com.nightingale.view.editor.common.IEditorView;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowConnectionInfoHandler implements EventHandler<MouseEvent> {

    private IEditorView processorEditorView;
    private Connection connection;

    public ShowConnectionInfoHandler(IProcessorEditorView processorEditorView, Connection connection) {
        this.processorEditorView = processorEditorView;
        this.connection = connection;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        processorEditorView.showConnectionInfoPane(connection);
    }
}
