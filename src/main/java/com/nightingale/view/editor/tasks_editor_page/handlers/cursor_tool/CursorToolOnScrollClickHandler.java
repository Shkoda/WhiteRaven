package com.nightingale.view.editor.tasks_editor_page.handlers.cursor_tool;


import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CursorToolOnScrollClickHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;
    private IProcessorEditorMediator mediator;

    public CursorToolOnScrollClickHandler(IProcessorEditorMediator mediator, ToggleButton cursorButton) {
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
