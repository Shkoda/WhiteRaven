package com.nightingale.command.editor;

import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.GraphType;
import com.nightingale.view.utils.NodeType;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by Nightingale on 13.03.14.
 */
public class DeleteCommand extends Service<Void> {
    public int deleteId;
    public NodeType nodeType;
    public GraphType graphType;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (nodeType == null)
                    return null;
                switch (graphType) {
                    case MPP:
                        switch (nodeType) {
                            case VERTEX:
                                DataManager.getMppModel().removeVertex(deleteId);
                                break;
                            case LINK:
                                DataManager.getMppModel().removeConnection(deleteId);
                                break;
                        }
                        break;
                    case TASK:
                        switch (nodeType) {
                            case VERTEX:
                                DataManager.getTaskGraphModel().removeVertex(deleteId);
                                break;
                            case LINK:
                                DataManager.getTaskGraphModel().removeConnection(deleteId);
                                break;
                        }
                        break;
                }
                return null;
            }
        };
    }
}
