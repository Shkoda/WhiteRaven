package com.nightingale.view.proscessor_editor_page;

import com.google.inject.Inject;
import com.nightingale.Main;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.editor.CreateLinkCommand;
import com.nightingale.command.editor.CreateProcessorCommand;
import com.nightingale.view.proscessor_editor_page.handlers.add_processor_tool.AddProcessorHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.CursorToolOnScrollClickHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.DeleteSelectedProcessorHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.RemoveSelectionHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.SelectNodeHandler;
import com.nightingale.view.proscessor_editor_page.handlers.link_tool.LinkToolOnNodeHandler;
import com.nightingale.view.proscessor_editor_page.handlers.link_tool.StopLinkingHandler;
import com.nightingale.view.proscessor_editor_page.mpp.IMppMediator;
import com.nightingale.view.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.utils.CanvasBounds;
import com.nightingale.view.utils.EditorComponents;
import com.nightingale.view.utils.Tuple;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import com.sun.javafx.animation.transition.Position2D;
import com.sun.swing.internal.plaf.metal.resources.metal;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
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
    // @Inject
    public IMppMediator mppMediator;
    @Inject
    public MppView mppView;

    private Pane mppCanvas;

    private ToggleButton cursorButton, addProcessorButton, linkButton;

    private Node selected;

    private Node linkStart;


    @Override
    public void initTools() {
        mppCanvas = mppView.getView();
        mppMediator = mppView.getMediator();

        initCursorTool();
        initAddProcessorTool();
        initLinkTool();
    }

    private void initCursorTool() {
        cursorButton = processorEditorView.getCursorButton();

        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new CursorToolOnScrollClickHandler(cursorButton));
        mppCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new RemoveSelectionHandler(cursorButton, this));
        Main.scene.addEventHandler(KeyEvent.KEY_PRESSED, new DeleteSelectedProcessorHandler(commandProvider, cursorButton, mppMediator, this));
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
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node));
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new LinkToolOnNodeHandler(commandProvider, linkButton, this, node));

        return node;
    }


    @Override
    public Node addLinkView(ProcessorLinkVO linkVO) {
        final Node node = mppView.addLinkView(linkVO);
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
                Position2D inBoundsCoordinate = CanvasBounds.getInBoundsCoordinate(x, y, node, mppCanvas);
                node.setTranslateX(inBoundsCoordinate.x);
                node.setTranslateY(inBoundsCoordinate.y);
                processorVO.setTranslateX(inBoundsCoordinate.x)
                        .setTranslateY(inBoundsCoordinate.y);
            }
        }
        );
        command.start();
    }

    @Override
    public void createLink(final Node firstProcessorNode, final Node secondProcessorNode) {
        CreateLinkCommand command = commandProvider.get(CreateLinkCommand.class);
        command.firstProcessor = firstProcessorNode;
        command.secondProcessor = secondProcessorNode;
        command.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                ProcessorLinkVO linkVO = (ProcessorLinkVO) workerStateEvent.getSource().getValue();
                turnOffAllSelection();
                Tuple<Position2D, Position2D> lineEnds = EditorComponents.getBestLineEnds(firstProcessorNode, secondProcessorNode);

                linkVO.setTranslateX1(lineEnds._1.x)
                        .setTranslateX2(lineEnds._2.x)
                        .setTranslateY1(lineEnds._1.y)
                        .setTranslateY2(lineEnds._2.y);
                System.out.println(linkVO);
                final Node node = addLinkView(linkVO);

                //  Position2D inBoundsCoordinate = CanvasBounds.getInBoundsCoordinate(x, y, node, mppCanvas);
                //     node.setTranslateX(inBoundsCoordinate.x);
                //      node.setTranslateY(inBoundsCoordinate.y);
                //       linkVO.setTranslateX(inBoundsCoordinate.x)
                //             .setTranslateY(inBoundsCoordinate.y);
            }
        }
        );
        command.start();
    }

    @Override
    public void turnOnSelection(Node node) {
        selected = node;
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
    }

    @Override
    public Node getSelected() {
        return selected;
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
