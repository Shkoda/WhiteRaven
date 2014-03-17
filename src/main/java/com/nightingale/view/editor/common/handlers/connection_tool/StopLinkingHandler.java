package com.nightingale.view.editor.common.handlers.connection_tool;

import com.nightingale.view.editor.common.IEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class StopLinkingHandler implements EventHandler<MouseEvent> {

    private ToggleButton linkButton;
    private IEditorMediator editorMediator;


    public StopLinkingHandler( ToggleButton linkButton,IEditorMediator editorMediator) {
        this.linkButton = linkButton;
        this.editorMediator = editorMediator;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (linkButton.isSelected() && mouseEvent.isSecondaryButtonDown()) {
            editorMediator.turnOffAllSelection();
        }
    }
}
