package com.nightingale.view.proscessor_editor_page.handlers.cursor_tool;

import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.DeleteProcessorCommand;
import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.proscessor_editor_page.mpp.IMppMediator;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class DeleteSelectedProcessorHandler implements EventHandler<KeyEvent> {

    private ICommandProvider commandProvider;

    private ToggleButton cursorButton;
    private IMppMediator mppMediator;
    private IProcessorEditorMediator processorEditorMediator;

    public DeleteSelectedProcessorHandler(ICommandProvider commandProvider, ToggleButton cursorButton, IMppMediator mppMediator, IProcessorEditorMediator processorEditorMediator) {
        this.commandProvider = commandProvider;
        this.cursorButton = cursorButton;
        this.mppMediator = mppMediator;
        this.processorEditorMediator = processorEditorMediator;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (cursorButton.isSelected() && keyEvent.getCode() == KeyCode.DELETE) {
            Node selected = processorEditorMediator.getSelected();
            if (selected == null)
                return;
            processorEditorMediator.turnOffAllSelection();
            DeleteProcessorCommand command = commandProvider.get(DeleteProcessorCommand.class);
            command.deleteId = Integer.valueOf(selected.getId());
            command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    mppMediator.refresh();
                }
            }
            );
            command.start();
        }
    }
}
