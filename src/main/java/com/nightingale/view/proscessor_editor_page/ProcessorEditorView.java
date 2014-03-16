package com.nightingale.view.proscessor_editor_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.tasks_editor_page.TasksEditorView;
import com.nightingale.view.utils.ButtonBuilder;
import com.nightingale.view.utils.EditorComponents;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.nightingale.view.utils.EditorComponents.*;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class ProcessorEditorView implements IProcessorEditorView {
    @Inject
    public ProcessorEditorMediator mediator;
    @Inject
    public TasksEditorView tasksEditorView;
    @Inject
    public MppView mppView;

    private GridPane view;
    private ToggleButton cursorButton, addProcessorButton, linkButton;
    private Button checkButton;
    private ProcessorInfoPane infoPane;

    @Override
    public Pane getView() {
        return view == null ? initView() : view;
    }


    @Override
    public void showProcessorInfoPane(ProcessorVO processorVO) {
        infoPane.setParams(processorVO);
        infoPane.bindParams(processorVO);
        infoPane.getToolBar().setVisible(true);
    }

    @Override
    public void showLinkInfoPane(ProcessorLinkVO linkVO) {

    }

    @Override
    public void hideInfoPane() {
        infoPane.unbindParams();
        infoPane.getToolBar().setVisible(false);
    }

    @Override
    public ToggleButton getCursorButton() {
        return cursorButton;
    }

    @Override
    public ToggleButton getAddProcessorButton() {
        return addProcessorButton;
    }

    @Override
    public ToggleButton getLinkButton() {
        return linkButton;
    }

    @Override
    public Button getCheckButton() {
        return checkButton;
    }

    @Override
    public String getName() {
        return Config.PROCESSOR_EDITOR_PAGE_NAME;
    }

    @Override
    public boolean isNavigationVisible() {
        return true;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return null;
    }

    @Override
    public ViewablePage getNextPage() {
        return tasksEditorView;
    }

    //----------------------------------------------------------
    //---------------------private methods----------------------
    //----------------------------------------------------------

    private GridPane initView() {
        view = EditorComponents.buildTemplateGrid();
        initToolBar();
        initCanvas();
        initStatusBar();
        return view;
    }

    private void initStatusBar() {
        infoPane = new ProcessorInfoPane();
        infoPane.getToolBar().setVisible(false);
        //   infoPane.getToolBar().setStyle("-fx-opacity: 0.5");
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color:  #f7f7f7; -fx-border-color: #bababa");
        pane.getChildren().addAll(infoPane.getToolBar());
        view.add(pane, INFO_BAR_POSITION.columnNumber, INFO_BAR_POSITION.rowNumber);
    }

    private void initToolBar() {
        initEditorTools();
        Pane toolBar = EditorComponents.buildToolBar(cursorButton, addProcessorButton, linkButton, checkButton);
        view.add(toolBar, TOOLBAR_POSITION.columnNumber, TOOLBAR_POSITION.rowNumber);
        mediator.initTools();
    }

    private void initCanvas() {
        ScrollPane canvasContainer = EditorComponents.buildCanvasContainer();
        canvasContainer.setContent(mppView.getView());
        view.add(canvasContainer, CANVAS_POSITION.columnNumber, CANVAS_POSITION.rowNumber);
    }

    private void initEditorTools() {
        ToggleGroup toggleGroup = new ToggleGroup();
        cursorButton = EditorComponents.createToolButton("CursorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Cursor");
        addProcessorButton = EditorComponents.createToolButton("AddProcessorButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Add Processor");
        linkButton = EditorComponents.createToolButton("LinkButton", toggleGroup, Config.EDITOR_BUTTON_SIZE, "Link Tool");
        checkButton = ButtonBuilder.createButton("CheckButton", Config.EDITOR_BUTTON_SIZE, "Check MPP Correctness");

        cursorButton.setSelected(true);
    }
}




























