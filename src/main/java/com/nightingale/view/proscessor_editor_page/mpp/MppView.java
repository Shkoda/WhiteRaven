package com.nightingale.view.proscessor_editor_page.mpp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.view.config.Config;
import com.nightingale.view.proscessor_editor_page.mpp.link.LinkShape;
import com.nightingale.view.proscessor_editor_page.mpp.processor.ProcessorShape;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MppView implements IMppView {
    @Inject
    public IMppMediator mediator;
    private Pane mppCanvas;

    @Override
    public Pane getView() {
        if (mppCanvas == null) {
            mediator.init();
            initCanvas();
        }
        return mppCanvas;
    }

    @Override
    public Node addProcessorView(ProcessorVO processorVO) {
        final Group view = ProcessorShape.build(processorVO);
        mediator.setDragHandler(view);
        mppCanvas.getChildren().add(view);
        return view;
    }

    @Override
    public Node addLinkView(ProcessorLinkVO processorLinkVO, final Node firstProcessorNode, final Node secondProcessorNode) {
        final Group view = LinkShape.build(processorLinkVO, firstProcessorNode,secondProcessorNode);
        mppCanvas.getChildren().add(view);
        return view;
    }

    private void initCanvas() {
        mppCanvas = PaneBuilder.create()
                .prefHeight(Config.CANVAS_HEIGHT)
                .prefWidth(Config.CANVAS_WIDTH)
                .maxHeight(Config.CANVAS_HEIGHT)
                .maxWidth(Config.CANVAS_WIDTH)
                .build();

        GridPane.setHalignment(mppCanvas, HPos.CENTER);
        GridPane.setValignment(mppCanvas, VPos.CENTER);

        mppCanvas.setStyle("-fx-background-color: #ffffff;-fx-border-color: #000000; ");
    }

    public IMppMediator getMediator() {
        return mediator;
    }
}
