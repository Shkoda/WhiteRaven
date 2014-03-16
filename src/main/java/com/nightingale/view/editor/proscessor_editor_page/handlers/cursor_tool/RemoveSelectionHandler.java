package com.nightingale.view.editor.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class RemoveSelectionHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;
    private IProcessorEditorMediator mediator;
    private IProcessorEditorView view;


    public RemoveSelectionHandler(ToggleButton cursorButton, IProcessorEditorMediator mediator, IProcessorEditorView view) {
        this.cursorButton = cursorButton;
        this.mediator = mediator;
        this.view = view;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (cursorButton.isSelected()) {
            mediator.turnOffAllSelection();
            view.hideInfoPane();
        }
    }
}
