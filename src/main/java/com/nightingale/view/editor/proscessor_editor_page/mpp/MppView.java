package com.nightingale.view.editor.proscessor_editor_page.mpp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.entities.graph.GraphType;
import com.nightingale.utils.Loggers;
import com.nightingale.view.editor.common.GraphMediator;
import com.nightingale.view.view_components.editor.CanvasPaneBuilder;
import com.nightingale.view.view_components.LinkShapeBuilder;
import com.nightingale.view.view_components.VertexShapeBuilder;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MppView implements IMppView {
    @Inject
    public IMppMediator mediator;
    private Pane mppCanvas;

    public MppView() {
        Loggers.debugLogger.debug("new MppView");
    }


    @Override
    public Pane getView() {
        if (mppCanvas == null) {
            mediator.init();
            mppCanvas = CanvasPaneBuilder.build(GraphType.MPP);
        }
        return mppCanvas;
    }

    @Override
    public GraphMediator getGraphMediator() {
        return mediator;
    }

    @Override
    public Node addVertexView(ProcessorModel processorModel) {
        final Group view = VertexShapeBuilder.build(processorModel, GraphType.MPP);
        mediator.setDragHandler(view);
        mppCanvas.getChildren().add(view);
        return view;
    }

    @Override
    public Node addConnectionView(ProcessorLinkModel processorLinkModel, final Node firstProcessorNode, final Node secondProcessorNode) {
        final Group view = LinkShapeBuilder.build(processorLinkModel, firstProcessorNode, secondProcessorNode, GraphType.MPP);
        mppCanvas.getChildren().add(view);
        return view;
    }


    public IMppMediator getMediator() {
        return mediator;
    }
}
