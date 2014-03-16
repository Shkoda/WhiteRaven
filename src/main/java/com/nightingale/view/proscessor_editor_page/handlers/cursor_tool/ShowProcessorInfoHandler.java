package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.proscessor_editor_page.IProcessorEditorView;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowProcessorInfoHandler implements EventHandler<MouseEvent> {

    private IProcessorEditorView processorEditorView;
    private ProcessorModel processorModel;

    public ShowProcessorInfoHandler(IProcessorEditorView processorEditorView, ProcessorModel processorModel) {
        this.processorEditorView = processorEditorView;
        this.processorModel = processorModel;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        processorEditorView.showProcessorInfoPane(processorModel);
    }
}
