package com.nightingale.command.menu.save;

import com.google.inject.Inject;
import com.nightingale.model.DataManager;
import com.nightingale.service.IDataService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


import java.nio.file.Path;

/**
 * Created by Nightingale on 10.03.14.
 */
public class SaveCommand extends Service<Void> {
    @Inject
    public IDataService dataService;

    public Path path;
    public Type type;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println(DataManager.toStringValue());
                //     System.out.println(mppModel);
                switch (type) {
                    case SAVE_MPP:
                        dataService.save(DataManager.getMppModel(), path);
                        break;
                    case SAVE_TASK_GRAPH:
                        dataService.save(DataManager.getTaskGraphModel(), path);
                        break;
                    case SAVE_PROJECT:
                        dataService.save(new DataManager.DataObject(DataManager.getMppModel(), DataManager.getTaskGraphModel()), path);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                return null;
            }
        };
    }

    public enum Type {
        SAVE_MPP,
        SAVE_TASK_GRAPH,
        SAVE_PROJECT
    }
}
