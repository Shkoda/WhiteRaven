package com.nightingale.view.editor.tasks_editor_page;

import com.google.inject.Inject;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.model.tasks.TaskModel;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import com.nightingale.view.editor.proscessor_editor_page.ProcessorEditorMediator;
import com.nightingale.view.editor.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.modeller_page.ModellerView;
import com.nightingale.view.view_components.editor.EditorCanvasContainerBuilder;
import com.nightingale.view.view_components.editor.EditorGridBuilder;
import com.nightingale.view.view_components.editor.EditorToolBuilder;
import com.nightingale.view.view_components.editor.EditorToolbarBuilder;
import com.nightingale.view.view_components.editor.mpp_editor.ProcessorInfoPane;
import com.nightingale.view.view_components.editor.mpp_editor.ProcessorLinkInfoPane;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static com.nightingale.view.view_components.editor.EditorConstants.CANVAS_POSITION;
import static com.nightingale.view.view_components.editor.EditorConstants.INFO_BAR_POSITION;
import static com.nightingale.view.view_components.editor.EditorConstants.TOOLBAR_POSITION;

/**
 * Created by Nightingale on 09.03.14.
 */
public class TasksEditorView implements ITasksEditorView {

    @Inject
    public ITasksEditorMediator mediator;
    @Inject
    public ModellerView nextPage;
    @Inject
    public IProcessorEditorView previousPage;

    @Inject
    public MppView mppView;

    private GridPane view;
    private ToggleButton cursorButton, addProcessorButton, linkButton;
    private Button checkButton;
    private ProcessorInfoPane processorInfoPane;
    private ProcessorLinkInfoPane linkInfoPane;

    private Pane infoContainer;

    private FadeTransition hideInfoMessageTransition;

    @Override
    public ToggleButton getCursorButton() {
        return null;
    }

    @Override
    public ToggleButton getAddVertexButton() {
        return null;
    }

    @Override
    public ToggleButton getLinkButton() {
        return null;
    }

    @Override
    public void showVertexInfoPane(TaskModel vertex) {

    }

    @Override
    public void showConnectionInfoPane(TaskLinkModel connection) {

    }

    @Override
    public void hideInfoPane() {
        processorInfoPane.unbindParams();
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

    @Override
    public Pane getView() {
        return view == null ? initView() : view;
    }

    private GridPane initView() {
        view = EditorGridBuilder.build();
        initToolBar();
        initCanvas();
        initStatusBar();

        hideInfoMessageTransition = FadeTransitionBuilder.create()
                .fromValue(1)
                .toValue(0)
                .duration(new Duration(3000))
                .build();

        return view;
    }

    private void initStatusBar() {
        processorInfoPane = new ProcessorInfoPane();
        processorInfoPane.getToolBar().setVisible(false);

        linkInfoPane = new ProcessorLinkInfoPane();
        linkInfoPane.getToolBar().setVisible(false);

        infoContainer = new Pane();
        infoContainer.setStyle("-fx-background-color:  #f7f7f7; -fx-border-color: #bababa");

        view.add(infoContainer, INFO_BAR_POSITION.columnNumber, INFO_BAR_POSITION.rowNumber);
    }

    private void initToolBar() {
        initEditorTools();
        Pane toolBar = EditorToolbarBuilder.build(cursorButton, addProcessorButton, linkButton, checkButton);
        view.add(toolBar, TOOLBAR_POSITION.columnNumber, TOOLBAR_POSITION.rowNumber);
        mediator.initTools();
    }

    private void initCanvas() {
        ScrollPane canvasContainer = EditorCanvasContainerBuilder.build();
        canvasContainer.setContent(mppView.getView());
        view.add(canvasContainer, CANVAS_POSITION.columnNumber, CANVAS_POSITION.rowNumber);
    }

    private void initEditorTools() {
        ToggleGroup toggleGroup = new ToggleGroup();
        cursorButton = EditorToolBuilder.build("CursorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Cursor");
        addProcessorButton = EditorToolBuilder.build("AddProcessorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Add Processor");
        linkButton = EditorToolBuilder.build("LinkButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Link Tool");
      //  checkButton = EditorToolBuilder.build("CheckButton", Config.EDITOR_BUTTON_SIZE, "Check MPP Correctness");

        cursorButton.setSelected(true);
    }
}
