package com.nightingale.view.editor.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.utils.NodeType;
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
    private NodeType nodeType;

    public SelectNodeHandler(ToggleButton cursorButton, IProcessorEditorMediator mediator, Node node, NodeType nodeType) {
        this.cursorButton = cursorButton;
        this.mediator = mediator;
        this.node = node;
        this.nodeType = nodeType;
    }

    public void handle(MouseEvent me) {
        if (cursorButton.isSelected()) {
            mediator.turnOffAllSelection();
            mediator.turnOnSelection(node, nodeType);
        }

    }

}
