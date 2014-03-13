package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class DeleteSelectedHandler implements EventHandler<KeyEvent> {

    private ToggleButton cursorButton;
    private IProcessorEditorMediator mediator;


    public DeleteSelectedHandler(ToggleButton cursorButton, IProcessorEditorMediator mediator) {
        this.cursorButton = cursorButton;
        this.mediator = mediator;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (cursorButton.isSelected() && keyEvent.getCode() == KeyCode.DELETE) {

        }
    }
}
