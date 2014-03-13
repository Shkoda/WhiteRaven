package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class SelectNodeHandler implements EventHandler<MouseEvent> {
    private ToggleButton cursorButton;
    private IProcessorEditorMediator mediator;
    private Node node;

    public SelectNodeHandler(ToggleButton cursorButton, IProcessorEditorMediator mediator, Node node) {
        this.cursorButton = cursorButton;
        this.mediator = mediator;
        this.node = node;
    }

    public void handle(MouseEvent me) {
        if (cursorButton.isSelected()) {
            mediator.turnOffAllSelection();
            mediator.turnOnSelection(node);
        }

    }

}
