package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CursorToolOnRightClickHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;

    public CursorToolOnRightClickHandler(ToggleButton cursorButton) {
        this.cursorButton = cursorButton;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.isSecondaryButtonDown()) {
            cursorButton.setSelected(true);
        }
    }
}
