package com.nightingale.view.editor.tasks_editor_page.task_graph;

import javafx.scene.Node;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface ITaskGraphMediator {
    void init();

    void refresh();

    void setDragHandler(Node node);
}
