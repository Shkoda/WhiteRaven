package com.nightingale.view.proscessor_editor_page.mpp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.view.proscessor_editor_page.mpp.link.ILinkView;
import com.nightingale.view.proscessor_editor_page.mpp.link.LinkView;
import com.nightingale.view.proscessor_editor_page.mpp.processor.ProcessorShape;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.geometry.Dimension2D;
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
    public void resetView(Iterable<ProcessorVO> processors, Iterable<ProcessorLinkVO> links) {
        mppCanvas.getChildren().clear();
        for (ProcessorVO processor : processors) {
            addProcessorView(processor);
        }
        for (ProcessorLinkVO link : links) {
            ILinkView linkView = new LinkView();
            linkView.update(link);
            mppCanvas.getChildren().addAll(linkView.getView());
        }
    }

    @Override
    public Node addProcessorView(ProcessorVO processorVO) {
        final Group view = ProcessorShape.build(processorVO);
        mediator.setDragHandler(processorVO.getId(), view);
        mppCanvas.getChildren().add(view);
        return view;
    }

    @Override
    public void addLinkView(ProcessorLinkVO processorLinkVO) {
        //todo
    }

    private void initCanvas() {
        Dimension2D d = mediator.getCanvasDimension();
        mppCanvas = PaneBuilder.create()
                .prefHeight(d.getHeight())
                .prefWidth(d.getWidth())
                .maxHeight(d.getHeight())
                .maxWidth(d.getWidth())
                .build();

        GridPane.setHalignment(mppCanvas, HPos.CENTER);
        GridPane.setValignment(mppCanvas, VPos.CENTER);

        mppCanvas.setStyle("-fx-background-color: #ffffff;-fx-border-color: #000000; ");
    }

 }
