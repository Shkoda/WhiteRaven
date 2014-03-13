package com.nightingale.command.menu.new_file;

import com.google.inject.Inject;
import com.nightingale.model.DataManager;
import com.nightingale.service.IDataService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by Nightingale on 09.03.14.
 */
public class NewMppCommand  extends Service<Void> {
    @Inject
    public IDataService dataService;


    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                DataManager.resetMppModel(dataService.createNewMppModel());
                return null;
            }
        };
    }
}
