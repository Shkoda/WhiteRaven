package com.nightingale.view.editor.proscessor_editor_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.entities.graph.Informative;
import com.nightingale.utils.Loggers;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.editor.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.editor.tasks_editor_page.TasksEditorView;
import com.nightingale.view.view_components.editor.*;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.view.view_components.editor.ProcessorInfoPane;
import com.nightingale.view.view_components.editor.InfoPane;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.nightingale.view.view_components.editor.EditorConstants.*;

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
    private ProcessorInfoPane processorInfoPane;
    private InfoPane<ProcessorLinkModel> linkInfoPane;

    private Pane infoContainer;

    private FadeTransition hideInfoMessageTransition;

    public ProcessorEditorView() {
        Loggers.debugLogger.debug("new ProcessorEditorView");
    }

    @Override
    public Pane getView() {
        return view == null ? initView() : view;
    }


    @Override
    public void showVertexInfoPane(ProcessorModel processorModel) {
        showInfo(processorModel, processorInfoPane);
    }


    private void showInfo(Informative informative, InfoPane infoPane) {
        processorInfoPane.unbindParams();
        linkInfoPane.unbindParams();
        infoContainer.getChildren().setAll(infoPane.getToolBar());
        infoPane.setParams(informative);
        infoPane.bindParams(informative);
        infoPane.getWeightTextField().selectAll();
        infoPane.getToolBar().setVisible(true);
    }

    @Override
    public void showMppInfoPane(boolean isMppOk) {
        Text message = CheckInfoBuilder.build(isMppOk);
        message.setX(infoContainer.getTranslateX());
        message.setY(infoContainer.getTranslateY() + 22);
        message.setVisible(true);

        hideInfoMessageTransition.setNode(message);
        hideInfoMessageTransition.play();

        infoContainer.getChildren().setAll(message);
    }

    @Override
    public void showConnectionInfoPane(ProcessorLinkModel linkModel) {
        showInfo(linkModel, linkInfoPane);
    }

    @Override
    public void hideInfoPane() {
        processorInfoPane.unbindParams();
        linkInfoPane.unbindParams();
        for (Node node : infoContainer.getChildren())
            node.setVisible(false);
    }

    @Override
    public ToggleButton getCursorButton() {
        return cursorButton;
    }

    @Override
    public ToggleButton getAddVertexButton() {
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

        linkInfoPane = new InfoPane<>();
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
        checkButton = EditorToolBuilder.build("CheckButton", Config.EDITOR_BUTTON_SIZE, "Check MPP Correctness");

        cursorButton.setSelected(true);
    }
}




























