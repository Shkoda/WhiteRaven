package com.nightingale.view.editor.common.handlers.cursor_tool;

import com.nightingale.view.editor.common.IEditorMediator;
import com.nightingale.view.editor.common.IEditorView;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class TurnOffSelectionHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;
    private IEditorMediator mediator;
    private IEditorView view;


    public TurnOffSelectionHandler(ToggleButton cursorButton, IEditorMediator mediator, IEditorView view) {
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
