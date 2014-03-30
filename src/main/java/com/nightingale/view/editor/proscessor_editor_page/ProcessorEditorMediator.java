package com.nightingale.view.editor.proscessor_editor_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.Main;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CreateProcessorLinkCommand;
import com.nightingale.command.editor.CreateProcessorCommand;
import com.nightingale.model.entities.graph.GraphType;
import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.common.handlers.add_tool.AddVertexHandler;
import com.nightingale.view.editor.common.handlers.cursor_tool.*;
import com.nightingale.view.editor.proscessor_editor_page.handlers.ShowMppCorrectnessInfoHandler;
import com.nightingale.view.editor.common.handlers.connection_tool.LinkToolOnNodeHandler;
import com.nightingale.view.editor.common.handlers.connection_tool.StopLinkingHandler;
import com.nightingale.view.editor.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.utils.NodeType;
import com.nightingale.view.view_components.editor.EditorConstants;
import com.nightingale.utils.Tuple;
import com.nightingale.view.view_components.VertexShapeBuilder;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class ProcessorEditorMediator implements IProcessorEditorMediator {
    @Inject
    public ICommandProvider commandProvider;
    @Inject
    public IProcessorEditorView processorEditorView;
    @Inject
    public MppView mppView;

    private Pane mppCanvas;
    private ToggleButton cursorButton, addProcessorButton, linkButton;
    private Button checkButton;

    private Node selected;
    private NodeType selectedNodeType;

    private Node linkStart;

    public ProcessorEditorMediator() {
        Loggers.debugLogger.debug("new ProcessorEditorMediator");
    }

    @Override
    public void initTools() {
        mppCanvas = mppView.getView();
        initCursorTool();
        initAddProcessorTool();
        initLinkTool();
        initCheckTool();
    }

    private void initCheckTool(){
        checkButton = processorEditorView.getCheckButton();
        checkButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowMppCorrectnessInfoHandler(commandProvider, processorEditorView));
    }

    private void initCursorTool() {
        cursorButton = processorEditorView.getCursorButton();
        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new CursorToolOnScrollClickHandler(this, cursorButton));
        mppCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new TurnOffSelectionHandler(cursorButton, this, processorEditorView));
        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, new DeleteSelectedHandler(commandProvider, cursorButton, mppView.getMediator(), this, GraphType.MPP));
    }

    private void initAddProcessorTool() {
        addProcessorButton = processorEditorView.getAddVertexButton();
        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new AddVertexHandler(addProcessorButton, this));
    }

    private void initLinkTool() {
        linkButton = processorEditorView.getLinkButton();
        mppCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new StopLinkingHandler(linkButton, this));
    }

    @Override
    public Node addVertexView(ProcessorModel processorModel) {
        final Node node = mppView.addVertexView(processorModel);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.VERTEX));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new LinkToolOnNodeHandler(linkButton, this, node));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowVertexInfoHandler(processorEditorView, processorModel));
        return node;
    }

    @Override
    public Node addConnectionView(ProcessorLinkModel linkModel, final Node firstProcessorNode, final Node secondProcessorNode) {
        final Node node = mppView.addConnectionView(linkModel, firstProcessorNode, secondProcessorNode);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.LINK));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowConnectionInfoHandler(processorEditorView, linkModel));
        return node;
    }

    @Override
    public void createVertex(final double x, final double y) {
        CreateProcessorCommand command = commandProvider.get(CreateProcessorCommand.class);
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                ProcessorModel processorModel = (ProcessorModel) workerStateEvent.getSource().getValue();
                final Node node = addVertexView(processorModel);
                Point2D inBoundsCoordinate = VertexShapeBuilder.getInBoundsCoordinate(x, y, node, mppCanvas);
                processorModel
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
    public void tryLinking(final Node firstProcessorNode, final Node secondProcessorNode) {
        CreateProcessorLinkCommand command = commandProvider.get(CreateProcessorLinkCommand.class);
        command.firstProcessor = firstProcessorNode;
        command.secondProcessor = secondProcessorNode;
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                if (workerStateEvent.getSource().getValue() == null)
                    return;
                ProcessorLinkModel linkVO = (ProcessorLinkModel) workerStateEvent.getSource().getValue();
                turnOffAllSelection();
                addConnectionView(linkVO, firstProcessorNode, secondProcessorNode);
            }
        }
        );
        command.start();
    }

    @Override
    public void turnOnSelection(Node node, NodeType nodeType) {
        selected = node;
        selectedNodeType = nodeType;
        selected.setEffect(EditorConstants.SELECTION_EFFECT);
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
    public Tuple<Node, NodeType> getSelected() {
        return new Tuple<>(selected, selectedNodeType);
    }

    @Override
    public Node getLinkStart() {
        return linkStart;
    }


    @Override
    public void setLinkStart(Node linkStart) {
        this.linkStart = linkStart;
        linkStart.setEffect(EditorConstants.START_LINK_EFFECT);
    }
}
