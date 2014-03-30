package com.nightingale.view.editor.tasks_editor_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.Main;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CreateTaskCommand;
import com.nightingale.command.editor.CreateTaskLinkCommand;
import com.nightingale.model.entities.graph.GraphType;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;
import com.nightingale.utils.Tuple;
import com.nightingale.view.editor.common.handlers.add_tool.AddVertexHandler;
import com.nightingale.view.editor.common.handlers.connection_tool.LinkToolOnNodeHandler;
import com.nightingale.view.editor.common.handlers.connection_tool.StopLinkingHandler;
import com.nightingale.view.editor.common.handlers.cursor_tool.*;

import com.nightingale.view.editor.tasks_editor_page.task_graph.TaskGraphView;
import com.nightingale.view.utils.NodeType;
import com.nightingale.view.view_components.editor.EditorConstants;
import com.nightingale.view.view_components.VertexShapeBuilder;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class TasksEditorMediator implements ITasksEditorMediator {

    public TasksEditorMediator() {
        Loggers.debugLogger.debug("new TasksEditorMediator");
    }

    @Inject
    public ICommandProvider commandProvider;
    @Inject
    public  TasksEditorView taskEditorView;
    @Inject
    public TaskGraphView taskGraphView;

    private Pane taskCanvas;
    private ToggleButton cursorButton, addTaskButton, linkButton;

    private Node selected;
    private NodeType selectedNodeType;

    private Node linkStart;

    @Override
    public void initTools() {
        taskCanvas = taskGraphView.getView();
        initCursorTool();
        initAddProcessorTool();
        initLinkTool();
    }

    @Override
    public void createVertex(final double x, final double y) {
        CreateTaskCommand command = commandProvider.get(CreateTaskCommand.class);
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                TaskModel taskModel = (TaskModel) workerStateEvent.getSource().getValue();
                final Node node = addVertexView(taskModel);
                Point2D inBoundsCoordinate = VertexShapeBuilder.getInBoundsCoordinate(x, y, node, taskCanvas);
                taskModel
                        .setTranslateX(inBoundsCoordinate.getX())
                        .setTranslateY(inBoundsCoordinate.getY());
                node.setTranslateX(inBoundsCoordinate.getX());
                node.setTranslateY(inBoundsCoordinate.getY());
            }
        }
        );
        command.start();
    }

    @Override
    public void tryLinking(final Node firstNode, final Node secondNode) {
        CreateTaskLinkCommand command = commandProvider.get(CreateTaskLinkCommand.class);
        command.firstTask = firstNode;
        command.secondTask = secondNode;
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                if (workerStateEvent.getSource().getValue() == null)
                    return;
                TaskLinkModel taskLinkModel = (TaskLinkModel) workerStateEvent.getSource().getValue();
                turnOffAllSelection();
                addConnectionView(taskLinkModel, firstNode, secondNode);
            }
        }
        );
        command.start();
    }

    @Override
    public Node addVertexView(TaskModel taskModel) {
        final Node node = taskGraphView.addVertexView(taskModel);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.VERTEX));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new LinkToolOnNodeHandler(linkButton, this, node));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowVertexInfoHandler(taskEditorView, taskModel));
        return node;
    }

    @Override
    public Node addConnectionView(TaskLinkModel connection, Node firstNode, Node secondNode) {
        final Node node = taskGraphView.addConnectionView(connection, firstNode, secondNode);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.LINK));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowConnectionInfoHandler(taskEditorView, connection));
        return node;
    }

    @Override
    public Tuple<Node, NodeType> getSelected() {
        return new Tuple<>(selected, selectedNodeType);
    }

    @Override
    public void turnOffAllSelection() {
        if (selected != null) {
            selected.setEffect(null);
            selected = null;
        }

        if (linkStart != null) {
            linkStart.setEffect(null);
            linkStart = null;
        }
        selectedNodeType = null;
    }

    @Override
    public void turnOnSelection(Node node, NodeType nodeType) {
        selected = node;
        selectedNodeType = nodeType;
        selected.setEffect(EditorConstants.SELECTION_EFFECT);
    }

    @Override
    public Node getLinkStart() {
        return linkStart;
    }

    @Override
    public void setLinkStart(Node node) {
        this.linkStart = node;
        linkStart.setEffect(EditorConstants.START_LINK_EFFECT);
    }

    private void initCursorTool() {
        cursorButton = taskEditorView.getCursorButton();
        taskCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new CursorToolOnScrollClickHandler(this, cursorButton));
        taskCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new TurnOffSelectionHandler(cursorButton, this, taskEditorView));
        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, new DeleteSelectedHandler(commandProvider, cursorButton, taskGraphView.getGraphMediator(), this, GraphType.TASK));
    }

    private void initAddProcessorTool() {
        addTaskButton = taskEditorView.getAddVertexButton();
        taskCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new AddVertexHandler(addTaskButton, this));
    }

    private void initLinkTool() {
        linkButton = taskEditorView.getLinkButton();
        taskCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new StopLinkingHandler(linkButton, this));
    }

}
