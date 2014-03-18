package com.nightingale.view.editor.tasks_editor_page.task_graph;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.Graph;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorMediator;
import com.nightingale.view.view_components.VertexShapeBuilder;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nightingale on 10.03.14.
 */
@Singleton
public class TaskGraphMediator implements ITaskGraphMediator {
    @Inject
    public ITaskGraphView taskGraphView;
    @Inject
    public ITasksEditorMediator editorMediator;

    private double initX;
    private double initY;
    private Point2D dragAnchor;
    private Pane taskCanvas;
    private Graph<TaskModel, TaskLinkModel> taskGraphModel;

    public TaskGraphMediator() {
        Loggers.debugLogger.debug("new TaskGraphMediator");
    }

    @Override
    public void init() {
        taskGraphModel = DataManager.getTaskGraphModel();
    }

    @Override
    public void refresh() {
        taskGraphModel = DataManager.getTaskGraphModel();

        if (taskCanvas == null)
            taskCanvas = taskGraphView.getView();
        taskCanvas.getChildren().clear();

        Map<Integer, Node> processors = new HashMap<>();
        for (TaskModel taskModel : taskGraphModel.getVertexes()) {
            Node node = editorMediator.addVertexView(taskModel);
            processors.put(Integer.valueOf(node.getId()), node);
        }
        for (TaskLinkModel taskLinkModel : taskGraphModel.getConnections()) {
            Node firstProcessor = processors.get(taskLinkModel.getFirstVertexId());
            Node secondProcessor = processors.get(taskLinkModel.getSecondVertexId());
            editorMediator.addConnectionView(taskLinkModel, firstProcessor, secondProcessor);
        }
    }

    @Override
    public void setDragHandler(final Node node) {
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                initX = node.getTranslateX();
                initY = node.getTranslateY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX() - dragAnchor.getX();
                double dragY = me.getSceneY() - dragAnchor.getY();

                Point2D inBoundsCoordinate = VertexShapeBuilder.getInBoundsCoordinate(initX + dragX, initY + dragY, node, taskGraphView.getView());
                node.setTranslateX(inBoundsCoordinate.getX());
                node.setTranslateY(inBoundsCoordinate.getY());

                ((Node) me.getSource()).setCursor(Cursor.MOVE);
            }
        });

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ((Node) me.getSource()).setCursor(Cursor.HAND);
            }
        });
    }
}
