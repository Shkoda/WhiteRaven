package com.nightingale.view.editor.tasks_editor_page.handlers.cursor_tool;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowLinkInfoHandler  implements EventHandler<MouseEvent> {

    private IProcessorEditorView processorEditorView;
    private ProcessorLinkModel linkModel;

    public ShowLinkInfoHandler(IProcessorEditorView processorEditorView, ProcessorLinkModel linkModel) {
        this.processorEditorView = processorEditorView;
        this.linkModel = linkModel;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        processorEditorView.showLinkInfoPane(linkModel);
    }
}
