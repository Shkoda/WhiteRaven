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

    public ExperimentConfig(StatisticsConfig statisticsConfig, int taskNumber, double connectivity, ModellerConstants.ScheduleDescription scheduleDescription) {
        this.minTaskWeight = statisticsConfig.minTaskWeight;
        this.maxTaskWeight = statisticsConfig.maxTaskWeight;
        this.taskNumber = taskNumber;
        this.connectivity = connectivity;
        this.experimentNumber = statisticsConfig.experimentNumber;
        this.scheduleDescription = scheduleDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentConfig that = (ExperimentConfig) o;
        return Double.compare(that.connectivity, connectivity) == 0
                && experimentNumber == that.experimentNumber
                && maxTaskWeight == that.maxTaskWeight
                && minTaskWeight == that.minTaskWeight
                && taskNumber == that.taskNumber
                && !(scheduleDescription != null ? !scheduleDescription.equals(that.scheduleDescription) : that.scheduleDescription != null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = minTaskWeight;
        result = 31 * result + maxTaskWeight;
        result = 31 * result + taskNumber;
        temp = Double.doubleToLongBits(connectivity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + experimentNumber;
        result = 31 * result + (scheduleDescription != null ? scheduleDescription.hashCode() : 0);
        return result;
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
