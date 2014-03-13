package com.nightingale.view.proscessor_editor_page;

import com.google.inject.Inject;
import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.IMppModel;
import com.nightingale.view.proscessor_editor_page.handlers.add_processor_tool.AddProcessorHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.CursorToolOnRightClickHandler;
import com.nightingale.view.proscessor_editor_page.handlers.cursor_tool.RemoveSelectionHandler;
import com.nightingale.view.proscessor_editor_page.handlers.node.SelectNodeHandler;
import com.nightingale.view.proscessor_editor_page.mpp.IMppMediator;
import com.nightingale.view.proscessor_editor_page.mpp.MppView;
import com.nightingale.view.utils.CanvasBounds;
import com.nightingale.view.utils.EditorComponents;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import com.sun.javafx.animation.transition.Position2D;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


/**
 * Created by Nightingale on 09.03.14.
 */
public class ProcessorEditorMediator implements IProcessorEditorMediator {
    @Inject
    public IProcessorEditorView processorEditorView;
    @Inject
    public IMppMediator IMppMediator;
    @Inject
    public MppView mppView;

    private Pane mppCanvas;
    private IMppModel mppModel;

    private ToggleButton cursorButton, addProcessorButton, linkButton;

    private Node selected;


    @Override
    public void initTools() {
        mppCanvas = mppView.getView();
        mppModel = DataManager.getMppModel();
        initCursorTool();
        initAddProcessorTool();

    }

    private void initCursorTool() {
        cursorButton = processorEditorView.getCursorButton();
        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new CursorToolOnRightClickHandler(cursorButton));
        mppCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new RemoveSelectionHandler(cursorButton, this));

    }


    private void initAddProcessorTool() {
        addProcessorButton = processorEditorView.getAddProcessorButton();
        mppCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new AddProcessorHandler(addProcessorButton, this));
    }

    @Override
    public Node addProcessor(ProcessorVO processorVO) {
        final Node node = mppView.addProcessorView(processorVO);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new SelectNodeHandler(cursorButton, this, node));
        return node;
    }


    @Override
    public Node addLink(ProcessorLinkVO linkVO) {
        return null;
    }

    @Override
    public void createProcessor(double x, double y) {
        ProcessorVO processorVO = mppModel.addProcessor();
        final Node node = addProcessor(processorVO);

        Position2D inBoundsCoordinate = CanvasBounds.getInBoundsCoordinate(x, y, node, mppCanvas);

        node.setTranslateX(inBoundsCoordinate.x);
        node.setTranslateY(inBoundsCoordinate.y);

        processorVO.setTranslateX(inBoundsCoordinate.x)
                .setTranslateY(inBoundsCoordinate.y);
    }


    @Override
    public void turnOnActiveSelection(Node node) {
        selected = node;
        selected.setEffect(EditorComponents.SELECTION_EFFECT);
    }

    @Override
    public void turnOffActiveSelection() {
        if (selected != null) {
            selected.setEffect(null);
            selected = null;
        }
    }

    @Override
    public boolean isProcessorToolSelected() {
        return processorEditorView.getAddProcessorButton().isSelected();
    }

    @Override
    public Node getSelected() {
        return selected;
    }


    @Override
    public Pane getMppView() {
        return mppView.getView();
    }


    @Override
    public Dimension2D getCanvasDimension() {
        return IMppMediator.getCanvasDimension();
    }

    @Override
    public void updateCanvasDimension(Dimension2D dimension2D) {

    }

}
