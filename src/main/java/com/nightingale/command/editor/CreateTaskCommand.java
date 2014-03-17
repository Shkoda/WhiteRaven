package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.tasks.TaskModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by Nightingale on 17.03.14.
 */
public class CreateTaskCommand extends Service<TaskModel> {
    @Override
    protected Task<TaskModel> createTask() {
        return new Task<TaskModel>() {
            @Override
            protected TaskModel call() throws Exception {
                return DataManager.getTaskGraphModel().addVertex();

            }
        };
    }

}
