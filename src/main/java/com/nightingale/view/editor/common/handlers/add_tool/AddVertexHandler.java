package com.nightingale.view.editor.common.handlers.add_tool;

import com.google.inject.Inject;
import com.nightingale.view.editor.common.IEditorMediator;
import com.nightingale.view.modeller_page.IModellerView;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class AddVertexHandler implements EventHandler<MouseEvent> {

    private ToggleButton addVertexButton;
    private IEditorMediator editorMediator;


    public AddVertexHandler(ToggleButton addVertexButton, IEditorMediator editorMediator) {
        this.addVertexButton = addVertexButton;
        this.editorMediator = editorMediator;
    }

    public void handle(MouseEvent me) {
        if (addVertexButton.isSelected()) {
            editorMediator.createVertex(me.getX(), me.getY());
        }
    }
}
