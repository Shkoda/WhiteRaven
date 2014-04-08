package com.nightingale.command.statistics;

import com.nightingale.command.generate.TaskGraphGenerator;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.model.entities.statistics.StatisticsConfig;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class StatisticsCommand extends Service<List<ExperimentResult>> {

    public StatisticsConfig statisticsConfig;

    @Override
    protected Task<List<ExperimentResult>> createTask() {
        return new Task<List<ExperimentResult>>() {
            @Override
            protected List<ExperimentResult> call() throws Exception {
return null;

            }
        };
    }
}
