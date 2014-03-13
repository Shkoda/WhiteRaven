package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CursorToolOnScrollClickHandler implements EventHandler<MouseEvent> {

    private ToggleButton cursorButton;

    public CursorToolOnScrollClickHandler(ToggleButton cursorButton) {
        this.cursorButton = cursorButton;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.isMiddleButtonDown()) {
            cursorButton.setSelected(true);
        }
    }
}
