package com.nightingale.view.editor.proscessor_editor_page;

import com.google.inject.Inject;
import com.nightingale.Main;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CheckMppCommand;
import com.nightingale.command.editor.CreateLinkCommand;
import com.nightingale.command.editor.CreateProcessorCommand;
import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.proscessor_editor_page.handlers.add_processor_tool.AddProcessorHandler;
import com.nightingale.view.editor.proscessor_editor_page.handlers.cursor_tool.*;
import com.nightingale.view.editor.proscessor_editor_page.handlers.link_tool.LinkToolOnNodeHandler;
import com.nightingale.view.editor.proscessor_editor_page.handlers.link_tool.StopLinkingHandler;
import com.nightingale.view.editor.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.utils.NodeType;
import com.nightingale.view.view_components.editor.EditorConstants;
import com.nightingale.utils.Tuple;
import com.nightingale.view.view_components.mpp.ProcessorShapeBuilder;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
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
        checkButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CheckMppCommand command = commandProvider.get(CheckMppCommand.class);
                command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        Boolean mppIsOk = (Boolean) workerStateEvent.getSource().getValue();
                        Loggers.debugLogger.debug("mpp ok: "+mppIsOk);
                        processorEditorView.showMppInfoPane(mppIsOk);
                    }
                }
                );
                command.start();
            }
        });
    }

    private void initCursorTool() {
        cursorButton = processorEditorView.getCursorButton();
        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new CursorToolOnScrollClickHandler(this, cursorButton));
        mppCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new RemoveSelectionHandler(cursorButton, this, processorEditorView));
        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, new DeleteSelectedProcessorHandler(commandProvider, cursorButton, mppView.getMediator(), this));
    }

    private void initAddProcessorTool() {
        addProcessorButton = processorEditorView.getAddProcessorButton();
        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new AddProcessorHandler(addProcessorButton, this));
    }

    private void initLinkTool() {
        linkButton = processorEditorView.getLinkButton();
        mppCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new StopLinkingHandler(linkButton, this));
    }

    @Override
    public Node addProcessorView(ProcessorModel processorModel) {
        final Node node = mppView.addProcessorView(processorModel);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.VERTEX));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new LinkToolOnNodeHandler(commandProvider, linkButton, this, node));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowProcessorInfoHandler(processorEditorView, processorModel));
        return node;
    }

    @Override
    public Node addLinkView(ProcessorLinkModel linkModel, final Node firstProcessorNode, final Node secondProcessorNode) {
        final Node node = mppView.addLinkView(linkModel, firstProcessorNode, secondProcessorNode);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.LINK));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowLinkInfoHandler(processorEditorView, linkModel));
        return node;
    }

    @Override
    public void createProcessor(final double x, final double y) {
        CreateProcessorCommand command = commandProvider.get(CreateProcessorCommand.class);
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                ProcessorModel processorModel = (ProcessorModel) workerStateEvent.getSource().getValue();
                final Node node = addProcessorView(processorModel);
                Point2D inBoundsCoordinate = ProcessorShapeBuilder.getInBoundsCoordinate(x, y, node, mppCanvas);
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
        CreateLinkCommand command = commandProvider.get(CreateLinkCommand.class);
        command.firstProcessor = firstProcessorNode;
        command.secondProcessor = secondProcessorNode;
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                if (workerStateEvent.getSource().getValue() == null)
                    return;
                ProcessorLinkModel linkVO = (ProcessorLinkModel) workerStateEvent.getSource().getValue();
                turnOffAllSelection();
                addLinkView(linkVO, firstProcessorNode, secondProcessorNode);
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
