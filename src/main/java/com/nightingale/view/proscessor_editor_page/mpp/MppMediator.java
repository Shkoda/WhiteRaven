package com.nightingale.view.proscessor_editor_page.mpp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.IMppModel;
import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.utils.CanvasBounds;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import com.sun.javafx.animation.transition.Position2D;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class MppMediator implements IMppMediator {
    @Inject
    public MppView mppView;
    @Inject
    public IProcessorEditorMediator editorMediator;

    private double initX;
    private double initY;
    private Point2D dragAnchor;
    private Pane mppCanvas;
    private IMppModel mppModel;

    @Override
    public void init() {
        mppModel = DataManager.getMppModel();
    }

    @Override
    public void refresh() {
        mppModel = DataManager.getMppModel();
      //  mppView.resetView(mppModel.getProcessors(), mppModel.getLinks());

        if (mppCanvas == null)
            mppCanvas = mppView.getView();
        mppCanvas.getChildren().clear();
        for (ProcessorVO processorVO : mppModel.getProcessors()) {
            editorMediator.addProcessorView(processorVO);

        }
      for (ProcessorLinkVO processorLinkVO : mppModel.getLinks())
          editorMediator.addLinkView(processorLinkVO);
    }

    @Override
    public void setDragHandler(final int processorId, final Node node) {
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                initX = node.getTranslateX();
                initY = node.getTranslateY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX() - dragAnchor.getX();
                double dragY = me.getSceneY() - dragAnchor.getY();

                Position2D inBoundsCoordinate = CanvasBounds.getInBoundsCoordinate(initX + dragX, initY + dragY, node, mppView.getView());
                node.setTranslateX(inBoundsCoordinate.x);
                node.setTranslateY(inBoundsCoordinate.y);

                ((Node) me.getSource()).setCursor(Cursor.MOVE);
            }
        });

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                mppModel.getProcessor(processorId)
                        .setTranslateX(node.getTranslateX())
                        .setTranslateY(node.getTranslateY());
                ((Node) me.getSource()).setCursor(Cursor.HAND);
            }
        });
    }

}
