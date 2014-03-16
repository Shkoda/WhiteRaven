package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.view.proscessor_editor_page.IProcessorEditorView;
import com.nightingale.vo.ProcessorVO;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.annotation.processing.Processor;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowProcessorInfoHandler implements EventHandler<MouseEvent> {

    private IProcessorEditorView processorEditorView;
    private ProcessorVO processorVO;

    public ShowProcessorInfoHandler(IProcessorEditorView processorEditorView, ProcessorVO processorVO) {
        this.processorEditorView = processorEditorView;
        this.processorVO = processorVO;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        processorEditorView.showProcessorInfoPane(processorVO);
    }
}
