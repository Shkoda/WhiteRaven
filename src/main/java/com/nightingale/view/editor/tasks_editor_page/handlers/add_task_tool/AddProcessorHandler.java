package com.nightingale.view.editor.tasks_editor_page.handlers.add_task_tool;

import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorMediator;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Nightingale on 13.03.14.
 */
public class AddProcessorHandler implements EventHandler<MouseEvent> {

    private ToggleButton addTaskButton;
    private ITasksEditorMediator tasksEditorMediator;

    public AddProcessorHandler(ToggleButton addTaskButton, ITasksEditorMediator tasksEditorMediator) {
        this.addTaskButton = addTaskButton;
        this.tasksEditorMediator = tasksEditorMediator;
    }

    public void handle(MouseEvent me) {
        if (addTaskButton.isSelected()) {
            tasksEditorMediator.createTask(me.getX(), me.getY());
        }
    }
}
