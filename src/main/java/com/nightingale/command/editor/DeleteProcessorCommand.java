package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.view.utils.NodeType;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by Nightingale on 13.03.14.
 */
public class DeleteProcessorCommand extends Service<Void> {
    public int deleteId;
    public NodeType nodeType;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (nodeType == null)
                    return null;

                switch (nodeType){
                    case VERTEX:
                        DataManager.getMppModel().removeProcessor(deleteId);
                        break;
                    case LINK:
                        DataManager.getMppModel().removeLink(deleteId);
                        break;
                }

                return null;
            }
        };
    }
}
