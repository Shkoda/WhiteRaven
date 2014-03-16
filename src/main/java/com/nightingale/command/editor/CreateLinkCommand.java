package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.view.view_components.mpp.ProcessorShapeBuilder;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CreateLinkCommand extends Service<ProcessorLinkModel> {
    public Node firstProcessor, secondProcessor;

    @Override
    protected Task<ProcessorLinkModel> createTask() {
        return new Task<ProcessorLinkModel>() {
            @Override
            protected ProcessorLinkModel call() throws Exception {
                int firstId = Integer.valueOf(firstProcessor.getId());
                int secondId = Integer.valueOf(secondProcessor.getId());

                if (firstId == secondId || DataManager.getMppModel().areConnected(firstId, secondId))
                    return null;
                ProcessorLinkModel linkVO = DataManager.getMppModel().linkProcessors(firstId, secondId);

                Point2D firstCenter = ProcessorShapeBuilder.getCentralPoint(firstProcessor);
                Point2D secondCenter = ProcessorShapeBuilder.getCentralPoint(secondProcessor);

                linkVO
                        .setTranslateX1(firstCenter.getX())
                        .setTranslateY1(firstCenter.getY())
                        .setTranslateX2(secondCenter.getX())
                        .setTranslateY2(secondCenter.getY());

                return linkVO;
            }
        };
    }

}
