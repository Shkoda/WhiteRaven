package com.nightingale.view.editor.common.handlers.add_tool;

import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.common.IEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class AddVertexHandler implements EventHandler<MouseEvent> {

    private ToggleButton addProcessorButton;
    private IEditorMediator editorMediator;

    public AddVertexHandler(ToggleButton addProcessorButton, IEditorMediator editorMediator) {
        this.addProcessorButton = addProcessorButton;
        this.editorMediator = editorMediator;
    }

    public void handle(MouseEvent me) {
        if (addProcessorButton.isSelected()) {
            editorMediator.createVertex(me.getX(), me.getY());
        }
    }
}
