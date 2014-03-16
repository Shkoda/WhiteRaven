package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.view.utils.EditorComponents;
import com.nightingale.view.utils.Tuple;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CreateLinkCommand extends Service<ProcessorLinkVO> {
    public Node firstProcessor, secondProcessor;

    @Override
    protected Task<ProcessorLinkVO> createTask() {
        return new Task<ProcessorLinkVO>() {
            @Override
            protected ProcessorLinkVO call() throws Exception {
                int firstId = Integer.valueOf(firstProcessor.getId());
                int secondId = Integer.valueOf(secondProcessor.getId());

                if (firstId == secondId || DataManager.getMppModel().areConnected(firstId, secondId))
                    return null;
                ProcessorLinkVO linkVO = DataManager.getMppModel().linkProcessors(firstId, secondId);
                Tuple<Point2D, Point2D> lineEnds = EditorComponents.getBestLineEnds(firstProcessor, secondProcessor);
                linkVO.setTranslateX1(lineEnds._1.getX())
                        .setTranslateX2(lineEnds._2.getX())
                        .setTranslateY1(lineEnds._1.getY())
                        .setTranslateY2(lineEnds._2.getY());

                return linkVO;
            }
        };
    }

}
