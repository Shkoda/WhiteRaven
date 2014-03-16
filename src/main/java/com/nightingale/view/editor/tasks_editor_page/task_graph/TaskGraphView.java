package com.nightingale.view.editor.tasks_editor_page.task_graph;

import com.nightingale.model.tasks.elements.TaskLinkModel;
import com.nightingale.model.tasks.elements.TaskModel;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 10.03.14.
 */
public class TaskGraphView implements ITaskGraphView {
    @Override
    public Pane getView() {
        return null;
    }

    @Override
    public Node addTaskView(TaskModel taskModel) {
        return null;
    }

    @Override
    public Node addConnectionView(TaskLinkModel taskLinkModel, Node parentProcessorNode, Node childProcessorNode) {
        return null;
    }
}
