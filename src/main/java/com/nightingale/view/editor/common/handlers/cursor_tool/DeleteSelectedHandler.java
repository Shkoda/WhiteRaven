package com.nightingale.view.editor.common.handlers.cursor_tool;

import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.DeleteCommand;
import com.nightingale.model.entities.GraphType;
import com.nightingale.view.editor.common.GraphMediator;
import com.nightingale.view.editor.common.IEditorMediator;
import com.nightingale.view.utils.NodeType;
import com.nightingale.utils.Tuple;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class DeleteSelectedHandler implements EventHandler<KeyEvent> {

    private ICommandProvider commandProvider;

    private ToggleButton cursorButton;
    private GraphMediator graphMediator;
    private IEditorMediator editorMediator;
    private GraphType graphType;

    public DeleteSelectedHandler(ICommandProvider commandProvider, ToggleButton cursorButton,
                                 GraphMediator graphMediator, IEditorMediator editorMediator, GraphType graphType) {
        this.commandProvider = commandProvider;
        this.cursorButton = cursorButton;
        this.graphMediator = graphMediator;
        this.editorMediator = editorMediator;
        this.graphType = graphType;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (cursorButton.isSelected() && keyEvent.getCode() == KeyCode.DELETE) {
            Tuple<Node, NodeType> selected = editorMediator.getSelected();
            if (selected._1 == null)
                return;
            editorMediator.turnOffAllSelection();
            DeleteCommand command = commandProvider.get(DeleteCommand.class);
            command.deleteId = Integer.valueOf(selected._1.getId());
            command.nodeType = selected._2;
            command.graphType = graphType;
            command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    graphMediator.refresh();
                }
            }
            );
            command.start();
        }
    }
}
