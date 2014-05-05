package com.nightingale.view.editor.proscessor_editor_page.handlers;

import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CheckMppCommand;
import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ShowMppCorrectnessInfoHandler implements EventHandler<MouseEvent> {

    private IProcessorEditorView processorEditorView;
    private ICommandProvider commandProvider;

    public ShowMppCorrectnessInfoHandler(ICommandProvider commandProvider, IProcessorEditorView processorEditorView) {
        this.processorEditorView = processorEditorView;
        this.commandProvider = commandProvider;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        CheckMppCommand command = commandProvider.get(CheckMppCommand.class);
        command.setOnSucceeded(workerStateEvent -> {
            Boolean mppIsOk = (Boolean) workerStateEvent.getSource().getValue();
            Loggers.debugLogger.debug("mpp ok: "+mppIsOk);
            processorEditorView.showMppInfoPane(mppIsOk);
        }
        );
        command.start();
    }
}