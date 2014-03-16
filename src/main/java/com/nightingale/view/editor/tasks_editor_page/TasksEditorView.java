package com.nightingale.view.editor.tasks_editor_page;

import com.google.inject.Inject;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.modeller_page.ModellerView;
import com.nightingale.view.editor.proscessor_editor_page.ProcessorEditorView;
import com.nightingale.view.editor.tasks_editor_page.task_graph.ITaskGraphView;
import com.nightingale.view.view_components.editor.EditorCanvasContainerBuilder;
import com.nightingale.view.view_components.editor.EditorGridBuilder;
import com.nightingale.view.view_components.editor.EditorToolBuilder;
import com.nightingale.view.view_components.editor.EditorToolbarBuilder;
import com.nightingale.view.view_components.editor.task_editor.ConnectionInfoPane;
import com.nightingale.view.view_components.editor.task_editor.TaskInfoPane;
import javafx.scene.control.Button;
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
public class TasksEditorView implements ITasksEditorView {
    @Inject
    public ProcessorEditorView processorEditorView;
    @Inject
    public ModellerView modellerView;
    @Inject
    public ITasksEditorMediator mediator;
    @Inject
    public ITaskGraphView graphView;

    private GridPane view;
    private ToggleButton cursorButton, addTaskButton, linkButton;

    private TaskInfoPane taskInfoPane;
    private ConnectionInfoPane connectionInfoPane;

    private Pane infoContainer;


    @Override
    public Pane getView() {
        return view == null ? initView() : view;
    }


    @Override
    public void showTaskInfoPane(ProcessorModel processorModel) {
        infoContainer.getChildren().setAll(taskInfoPane.getToolBar());
        taskInfoPane.setParams(processorModel);
        taskInfoPane.bindParams(processorModel);
        taskInfoPane.getToolBar().setVisible(true);
    }

    @Override
    public void showLinkInfoPane(ProcessorLinkModel linkVO) {

    }

    @Override
    public void showGraphInfoPane(boolean isMppOk) {

    }

    @Override
    public void hideInfoPane() {

    }



    @Override
    public ToggleButton getCursorButton() {
        return null;
    }

    @Override
    public ToggleButton getAddTaskButton() {
        return null;
    }

    @Override
    public ToggleButton getLinkButton() {
        return null;
    }

    @Override
    public Button getCheckButton() {
        return null;
    }



    @Override
    public String getName() {
        return Config.TASK_EDITOR_PAGE_NAME;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return processorEditorView;
    }

    @Override
    public ViewablePage getNextPage() {
        return modellerView;
    }

    @Override
    public boolean isNavigationVisible() {
        return true;
    }

    //----------------------------------------------------------
    //---------------------private methods----------------------
    //----------------------------------------------------------

    private GridPane initView() {
        view = EditorGridBuilder.build();
        initToolBar();
        initCanvas();
        initStatusBar();

        return view;
    }

    private void initStatusBar() {
        taskInfoPane = new TaskInfoPane();
        taskInfoPane.getToolBar().setVisible(false);

        connectionInfoPane = new ConnectionInfoPane();
        connectionInfoPane.getToolBar().setVisible(false);

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
        canvasContainer.setContent(graphView.getView());
        view.add(canvasContainer, CANVAS_POSITION.columnNumber, CANVAS_POSITION.rowNumber);
    }

    private void initEditorTools() {
        ToggleGroup toggleGroup = new ToggleGroup();
        cursorButton = EditorToolBuilder.build("CursorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Cursor");
        addTaskButton = EditorToolBuilder.build("AddTaskButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Add task");
        linkButton = EditorToolBuilder.build("ConnectTaskButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Connection Tool");

        cursorButton.setSelected(true);
    }
}
