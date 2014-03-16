package com.nightingale.view.editor.proscessor_editor_page.handlers.add_processor_tool;

import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class AddProcessorHandler implements EventHandler<MouseEvent> {

    private ToggleButton addProcessorButton;
    private IProcessorEditorMediator processorEditorMediator;

    public AddProcessorHandler(ToggleButton addProcessorButton, IProcessorEditorMediator processorEditorMediator) {
        this.addProcessorButton = addProcessorButton;
        this.processorEditorMediator = processorEditorMediator;
    }

    public void handle(MouseEvent me) {
        if (addProcessorButton.isSelected()) {
            processorEditorMediator.createProcessor(me.getX(), me.getY());
        }
    }
}
