package com.nightingale.command.generate;

import com.google.inject.Inject;
import com.nightingale.model.DataManager;
import com.nightingale.service.IDataService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.nio.file.Path;

/**
 * Created by Nightingale on 25.03.2014.
 */
public class GenerateTaskGraphCommand extends Service<Void> {

    public int minTaskWeight, maxTaskWeight;
    public int taskNumber;
    public double connectivity;

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                DataManager.resetTaskGraphModel(TaskGraphGenerator.generate(minTaskWeight, maxTaskWeight, taskNumber, connectivity));
                return null;
            }
        };
    }
}
