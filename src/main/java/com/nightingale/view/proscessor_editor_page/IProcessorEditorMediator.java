package com.nightingale.view.proscessor_editor_page;

import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IProcessorEditorMediator {
    void initTools();

    Dimension2D getCanvasDimension();

    void updateCanvasDimension(Dimension2D dimension2D);

    Pane getMppView();

    public void createProcessor(double x, double y);

    Node addProcessor(ProcessorVO processorVO);

    Node addLink(ProcessorLinkVO linkVO);

    boolean isProcessorToolSelected();

    Node getSelected();

    void turnOffActiveSelection();

    void turnOnActiveSelection(Node node);
}
