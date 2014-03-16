package com.nightingale.view.editor.tasks_editor_page.task_graph;

import com.nightingale.model.tasks.elements.TaskLinkModel;
import com.nightingale.model.tasks.elements.TaskModel;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface ITaskGraphView {
    Pane getView();

    Node addTaskView(TaskModel taskModel);

    Node addConnectionView(TaskLinkModel taskLinkModel, final Node parentProcessorNode, final Node childProcessorNode);
}
