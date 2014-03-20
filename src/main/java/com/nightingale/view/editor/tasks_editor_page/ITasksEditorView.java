package com.nightingale.view.editor.tasks_editor_page;

import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.view.editor.common.IEditorView;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ITasksEditorView  extends IEditorView<TaskModel, TaskLinkModel> {
    Pane getGraphView();

}
