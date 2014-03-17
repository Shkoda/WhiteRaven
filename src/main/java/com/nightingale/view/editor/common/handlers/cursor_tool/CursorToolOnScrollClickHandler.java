package com.nightingale.view.editor.common.handlers.cursor_tool;

import com.nightingale.view.editor.common.IEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CursorToolOnScrollClickHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;
    private IEditorMediator mediator;

    public CursorToolOnScrollClickHandler(IEditorMediator mediator, ToggleButton cursorButton) {
        this.cursorButton = cursorButton;
        this.mediator = mediator;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.isMiddleButtonDown()) {
            cursorButton.setSelected(true);
            mediator.turnOffAllSelection();
        }
    }
}
