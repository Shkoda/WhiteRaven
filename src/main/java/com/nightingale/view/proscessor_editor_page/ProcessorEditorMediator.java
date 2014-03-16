package com.nightingale.view.proscessor_editor_page;

import com.google.inject.Inject;
import com.nightingale.Main;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CreateLinkCommand;
import com.nightingale.command.editor.CreateProcessorCommand;
import com.nightingale.view.proscessor_editor_page.handlers.add_processor_tool.AddProcessorHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.*;
import com.nightingale.view.proscessor_editor_page.handlers.link_tool.LinkToolOnNodeHandler;
import com.nightingale.view.proscessor_editor_page.handlers.link_tool.StopLinkingHandler;
import com.nightingale.view.proscessor_editor_page.mpp.IMppMediator;
import com.nightingale.view.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.utils.NodeType;
import com.nightingale.view.utils.CanvasBounds;
import com.nightingale.view.utils.EditorComponents;
import com.nightingale.view.utils.Tuple;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
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
public class ProcessorEditorMediator implements IProcessorEditorMediator {
    @Inject
    public ICommandProvider commandProvider;
    @Inject
    public IProcessorEditorView processorEditorView;
    @Inject
    public MppView mppView;

    private Pane mppCanvas;
    private ToggleButton cursorButton, addProcessorButton, linkButton;

    private Node selected;
    private NodeType selectedNodeType;

    private Node linkStart;

    @Override
    public void initTools() {
        mppCanvas = mppView.getView();
        initCursorTool();
        initAddProcessorTool();
        initLinkTool();
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
    public Node addProcessorView(ProcessorVO processorVO) {
        final Node node = mppView.addProcessorView(processorVO);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.VERTEX));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new LinkToolOnNodeHandler(commandProvider, linkButton, this, node));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new ShowProcessorInfoHandler(processorEditorView, processorVO));
        return node;
    }

    @Override
    public Node addLinkView(ProcessorLinkVO linkVO, final Node firstProcessorNode, final Node secondProcessorNode) {
        final Node node = mppView.addLinkView(linkVO, firstProcessorNode, secondProcessorNode);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node, NodeType.LINK));
        return node;
    }

    @Override
    public void createProcessor(final double x, final double y) {
        CreateProcessorCommand command = commandProvider.get(CreateProcessorCommand.class);
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                ProcessorVO processorVO = (ProcessorVO) workerStateEvent.getSource().getValue();
                final Node node = addProcessorView(processorVO);
                Point2D inBoundsCoordinate = CanvasBounds.getInBoundsCoordinate(x, y, node, mppCanvas);
                processorVO
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
                ProcessorLinkVO linkVO = (ProcessorLinkVO) workerStateEvent.getSource().getValue();
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
        selected.setEffect(EditorComponents.SELECTION_EFFECT);
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
        linkStart.setEffect(EditorComponents.START_LINK_EFFECT);
    }
}
