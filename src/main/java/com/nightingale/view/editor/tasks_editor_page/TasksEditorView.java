package com.nightingale.view.editor.tasks_editor_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.entities.Informative;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import com.nightingale.view.editor.tasks_editor_page.task_graph.ITaskGraphView;
import com.nightingale.view.modeller_page.IModellerView;
import com.nightingale.view.view_components.editor.EditorCanvasContainerBuilder;
import com.nightingale.view.view_components.editor.EditorGridBuilder;
import com.nightingale.view.view_components.editor.EditorToolBuilder;
import com.nightingale.view.view_components.editor.EditorToolbarBuilder;
import com.nightingale.view.view_components.editor.InfoPane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.nightingale.view.view_components.editor.EditorConstants.CANVAS_POSITION;
import static com.nightingale.view.view_components.editor.EditorConstants.INFO_BAR_POSITION;
import static com.nightingale.view.view_components.editor.EditorConstants.TOOLBAR_POSITION;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class TasksEditorView implements ITasksEditorView {

    @Inject
    public TasksEditorMediator mediator;
    @Inject
    public IModellerView nextPage;
    @Inject
    public IProcessorEditorView previousPage;

    @Inject
    public ITaskGraphView taskGraphView;

    private GridPane view;
    private ToggleButton cursorButton, addTaskButton, linkButton;

    private InfoPane infoPane;

    private Pane infoContainer;

    public TasksEditorView() {
        Loggers.debugLogger.debug("new TasksEditorView");
    }

    @Override
    public Pane getView() {
        return view == null ? initView() : view;
    }

    @Override
    public Pane getGraphView() {
        return taskGraphView.getView();
    }

    private GridPane initView() {
        view = EditorGridBuilder.build();
        initToolBar();
        initCanvas();
        initStatusBar();
        return view;
    }

    private void initStatusBar() {
        infoPane = new InfoPane();
        infoPane.getToolBar().setVisible(false);

        infoContainer = new Pane();
        infoContainer.setStyle("-fx-background-color:  #f7f7f7; -fx-border-color: #bababa");

        view.add(infoContainer, INFO_BAR_POSITION.columnNumber, INFO_BAR_POSITION.rowNumber);
    }

    private void initToolBar() {
        initEditorTools();
        Pane toolBar = EditorToolbarBuilder.build(cursorButton, addTaskButton, linkButton);
        view.add(toolBar, TOOLBAR_POSITION.columnNumber, TOOLBAR_POSITION.rowNumber);
        mediator.initTools();
    }

    private void initCanvas() {
        ScrollPane canvasContainer = EditorCanvasContainerBuilder.build();
        canvasContainer.setContent(taskGraphView.getView());
        view.add(canvasContainer, CANVAS_POSITION.columnNumber, CANVAS_POSITION.rowNumber);
    }

    private void initEditorTools() {
        ToggleGroup toggleGroup = new ToggleGroup();
        cursorButton = EditorToolBuilder.build("CursorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Cursor");
        addTaskButton = EditorToolBuilder.build("AddProcessorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Add Task");
        linkButton = EditorToolBuilder.build("LinkButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Link Tool");
        cursorButton.setSelected(true);
    }

    @Override
    public ToggleButton getCursorButton() {
        return cursorButton;
    }

    @Override
    public ToggleButton getAddVertexButton() {
        return addTaskButton;
    }

    @Override
    public ToggleButton getLinkButton() {
        return linkButton;
    }

    @Override
    public void showVertexInfoPane(TaskModel vertex) {
        showInfo(vertex);
    }

    @Override
    public void showConnectionInfoPane(TaskLinkModel connection) {
        showInfo(connection);
    }

    private void showInfo(Informative informative) {
        infoPane.unbindParams();
        infoContainer.getChildren().setAll(infoPane.getToolBar());
        infoPane.setParams(informative);
        infoPane.bindParams(informative);
        infoPane.getToolBar().setVisible(true);
    }

    @Override
    public void hideInfoPane() {
        infoPane.unbindParams();
        for (Node node : infoContainer.getChildren())
            node.setVisible(false);
    }

    @Override
    public String getName() {
        return Config.TASK_EDITOR_PAGE_NAME;
    }

    @Override
    public boolean isNavigationVisible() {
        return true;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return previousPage;
    }

    @Override
    public ViewablePage getNextPage() {
        return nextPage;
    }

}
