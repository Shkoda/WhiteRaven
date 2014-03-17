package com.nightingale.view.editor.common.handlers.connection_tool;

import com.nightingale.view.editor.common.IEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class LinkToolOnNodeHandler implements EventHandler<MouseEvent> {

    private ToggleButton linkButton;
    private IEditorMediator editorMediator;
    private Node owner;

    public LinkToolOnNodeHandler(ToggleButton linkButton, IEditorMediator editorMediator, Node owner) {
        this.linkButton = linkButton;
        this.editorMediator = editorMediator;
        this.owner = owner;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (linkButton.isSelected() && mouseEvent.isPrimaryButtonDown()) {
            Node linkStart = editorMediator.getLinkStart();
            if (linkStart == null) {
                editorMediator.setLinkStart(owner);
            } else  {
                editorMediator.tryLinking(linkStart, owner);

            }
        }
    }
}
