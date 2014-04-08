package com.nightingale.model.entities.statistics;

import com.nightingale.view.view_components.modeller.ModellerConstants;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class ExperimentConfig {
    public final int minTaskWeight, maxTaskWeight;
    public final int taskNumber;
    public final double connectivity;
    public final int experimentNumber;
    public final ModellerConstants.ScheduleDescription scheduleDescription;

    public ExperimentConfig(int minTaskWeight, int maxTaskWeight, int taskNumber, double connectivity, int experimentNumber, ModellerConstants.ScheduleDescription scheduleDescription) {
        this.minTaskWeight = minTaskWeight;
        this.maxTaskWeight = maxTaskWeight;
        this.taskNumber = taskNumber;
        this.connectivity = connectivity;
        this.experimentNumber = experimentNumber;
        this.scheduleDescription = scheduleDescription;
    }

    @Override
    public String toString() {
        return "ExperimentConfig{" +
                "minTaskWeight=" + minTaskWeight +
                ", maxTaskWeight=" + maxTaskWeight +
                ", taskNumber=" + taskNumber +
                ", connectivity=" + connectivity +
                ", experimentNumber=" + experimentNumber +
                ", loadingType=" + scheduleDescription +
                '}';
    }
}
