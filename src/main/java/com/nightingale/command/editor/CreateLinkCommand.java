package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.vo.ProcessorLinkVO;
import com.nightingale.vo.ProcessorVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
                return DataManager.getMppModel().linkProcessors(Integer.valueOf(firstProcessor.getId()), Integer.valueOf(secondProcessor.getId()));

            }
        };
    }

}
