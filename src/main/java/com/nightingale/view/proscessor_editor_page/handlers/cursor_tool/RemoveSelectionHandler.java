package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class RemoveSelectionHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;
    private IProcessorEditorMediator mediator;


    public RemoveSelectionHandler(ToggleButton cursorButton, IProcessorEditorMediator mediator) {
        this.cursorButton = cursorButton;
        this.mediator = mediator;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (cursorButton.isSelected()) {
            mediator.turnOffActiveSelection();
        }
    }
}
