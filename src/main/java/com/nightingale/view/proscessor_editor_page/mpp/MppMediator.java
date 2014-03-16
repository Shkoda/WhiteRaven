package com.nightingale.view.proscessor_editor_page.mpp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.IMppModel;
import com.nightingale.view.proscessor_editor_page.IProcessorEditorMediator;
import com.nightingale.view.view_components.mpp.ProcessorShapeBuilder;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;


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

        if (mppCanvas == null)
            mppCanvas = mppView.getView();
        mppCanvas.getChildren().clear();

        Map<Integer, Node> processors = new HashMap<>();
        for (ProcessorModel processorModel : mppModel.getProcessors()) {
            Node node = editorMediator.addProcessorView(processorModel);
            processors.put(Integer.valueOf(node.getId()), node);
        }
        for (ProcessorLinkModel processorLinkModel : mppModel.getLinks()) {
            Node firstProcessor = processors.get(processorLinkModel.getFirstProcessorId());
            Node secondProcessor = processors.get(processorLinkModel.getSecondProcessorId());
            editorMediator.addLinkView(processorLinkModel, firstProcessor, secondProcessor);
        }
    }

    @Override
    public void setDragHandler(final Node node) {
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

                Point2D inBoundsCoordinate = ProcessorShapeBuilder.getInBoundsCoordinate(initX + dragX, initY + dragY, node, mppView.getView());
                node.setTranslateX(inBoundsCoordinate.getX());
                node.setTranslateY(inBoundsCoordinate.getY());

                ((Node) me.getSource()).setCursor(Cursor.MOVE);
            }
        });

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ((Node) me.getSource()).setCursor(Cursor.HAND);
            }
        });
    }

}
