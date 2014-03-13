package com.nightingale.command.menu.open;

import com.google.inject.Inject;
import com.nightingale.model.DataManager;
import com.nightingale.service.IDataService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.nio.file.Path;

/**
 * Created by Nightingale on 10.03.14.
 */
public class OpenTaskGraphCommand extends Service<Void> {
    @Inject
    public IDataService dataService;

    public Path path;
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                DataManager.resetTaskGraphModel(dataService.readTaskGraph(path));
                return null;
            }
        };
    }
}
