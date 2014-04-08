package com.nightingale.model.entities.statistics;

import com.nightingale.view.view_components.modeller.ModellerConstants;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class ExperimentResult {
    public final int taskNumber;
    public final double connectivity;
    public final double accelerationFactor, efficiencyFactor, algorithmEfficiencyFactor;
    public final ModellerConstants.QueueType queueType;
    public final ModellerConstants.LoadingType loadingType;

    public ExperimentResult(ExperimentConfig experimentConfig,
                            double accelerationFactor, double efficiencyFactor, double algorithmEfficiencyFactor) {
        this.taskNumber = experimentConfig.taskNumber;
        this.connectivity = experimentConfig.connectivity;
        this.accelerationFactor = accelerationFactor;
        this.efficiencyFactor = efficiencyFactor;
        this.algorithmEfficiencyFactor = algorithmEfficiencyFactor;
        this.queueType = experimentConfig.scheduleDescription.queueType;
        this.loadingType = experimentConfig.scheduleDescription.loadingType;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public double getConnectivity() {
        return connectivity;
    }

    public double getAccelerationFactor() {
        return accelerationFactor;
    }

    public double getEfficiencyFactor() {
        return efficiencyFactor;
    }

    public double getAlgorithmEfficiencyFactor() {
        return algorithmEfficiencyFactor;
    }

    public ModellerConstants.QueueType getQueueType() {
        return queueType;
    }

    public ModellerConstants.LoadingType getLoadingType() {
        return loadingType;
    }

    @Override
    public String toString() {
        return "ExperimentResult{" +
                "taskNumber=" + taskNumber +
                ", connectivity=" + connectivity +
                ", accelerationFactor=" + accelerationFactor +
                ", efficiencyFactor=" + efficiencyFactor +
                ", algorithmEfficiencyFactor=" + algorithmEfficiencyFactor +
                ", queueType=" + queueType +
                ", loadingType=" + loadingType +
                '}';
    }
}
