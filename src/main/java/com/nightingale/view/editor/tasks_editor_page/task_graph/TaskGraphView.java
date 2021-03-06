package com.nightingale.view.editor.tasks_editor_page.task_graph;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.entities.graph.GraphType;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.common.GraphMediator;
import com.nightingale.view.view_components.editor.CanvasPaneBuilder;
import com.nightingale.view.view_components.LinkShapeBuilder;
import com.nightingale.view.view_components.VertexShapeBuilder;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 10.03.14.
 */
@Singleton
public class TaskGraphView implements ITaskGraphView {
    @Inject
    public ITaskGraphMediator mediator;
    private Pane graphCanvas;

    public TaskGraphView() {
        Loggers.debugLogger.debug("new TaskGraphView");
    }

    @Override
    public Pane getView() {
        if (graphCanvas == null) {
            mediator.init();
            graphCanvas = CanvasPaneBuilder.build(GraphType.TASK);

        }
        return graphCanvas;
    }

    @Override
    public GraphMediator getGraphMediator() {
        return mediator;
    }

    @Override
    public Node addVertexView(TaskModel vertex) {
        final Group view = VertexShapeBuilder.build(vertex, GraphType.TASK);
        mediator.setDragHandler(view);
        graphCanvas.getChildren().add(view);
        return view;
    }

    @Override
    public Node addConnectionView(TaskLinkModel connection, Node firstNode, Node secondNode) {
        final Group view = LinkShapeBuilder.build(connection, firstNode, secondNode, GraphType.TASK);
        graphCanvas.getChildren().add(view);
        return view;
    }
}
