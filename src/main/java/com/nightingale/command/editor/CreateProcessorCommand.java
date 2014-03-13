package com.nightingale.command.editor;

import com.google.inject.Inject;
import com.nightingale.model.DataManager;
import com.nightingale.service.IDataService;
import com.nightingale.vo.ProcessorVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.nio.file.Path;

/**
 * Created by Nightingale on 13.03.14.
 */
public class CreateProcessorCommand extends Service<ProcessorVO> {
    @Override
    protected Task<ProcessorVO> createTask() {
        return new Task<ProcessorVO>() {
            @Override
            protected ProcessorVO call() throws Exception {
                return DataManager.getMppModel().addProcessor();

            }
        };
    }

}
