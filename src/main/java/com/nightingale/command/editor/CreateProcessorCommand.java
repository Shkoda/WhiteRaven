package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CreateProcessorCommand extends Service<ProcessorModel> {
    @Override
    protected Task<ProcessorModel> createTask() {
        return new Task<ProcessorModel>() {
            @Override
            protected ProcessorModel call() throws Exception {
                return DataManager.getMppModel().addVertex();

            }
        };
    }

}
