package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.tasks.TaskLinkModel;
import com.nightingale.view.view_components.mpp.VertexShapeBuilder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Created by Nightingale on 17.03.14.
 */
public class CreateTaskLinkCommand extends Service<TaskLinkModel> {
    public Node firstTask, secondTask;

    @Override
    protected Task<TaskLinkModel> createTask() {
        return new Task<TaskLinkModel>() {
            @Override
            protected TaskLinkModel call() throws Exception {
                int firstId = Integer.valueOf(firstTask.getId());
                int secondId = Integer.valueOf(secondTask.getId());

                if (firstId == secondId || DataManager.getTaskGraphModel().areConnected(firstId, secondId))
                    return null;
                TaskLinkModel taskLinkModel = DataManager.getTaskGraphModel().linkVertexes(firstId, secondId);

                Point2D firstCenter = VertexShapeBuilder.getCentralPoint(firstTask);
                Point2D secondCenter = VertexShapeBuilder.getCentralPoint(secondTask);

                taskLinkModel
                        .setTranslateX1(firstCenter.getX())
                        .setTranslateY1(firstCenter.getY())
                        .setTranslateX2(secondCenter.getX())
                        .setTranslateY2(secondCenter.getY());

                return taskLinkModel;
            }
        };
    }

}
