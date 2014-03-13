package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.vo.ProcessorVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by Nightingale on 13.03.14.
 */
public class DeleteProcessorCommand extends Service<Void> {
    public int deleteId;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                DataManager.getMppModel().removeProcessor(deleteId);
                return null;
            }
        };
    }
}
