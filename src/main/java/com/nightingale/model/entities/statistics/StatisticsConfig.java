package com.nightingale.model.entities.statistics;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class StatisticsConfig {
    public final int minTaskWeight, maxTaskWeight;
    public final int minTaskNumber, maxTaskNumber, taskNumberGrowStep;
    public final double minConnectivity, maxConnectivity, connectivityGrowStep;
    public final int experimentNumber;

    public StatisticsConfig(int minTaskWeight, int maxTaskWeight, int minTaskNumber, int maxTaskNumber, int taskNumberGrowStep,
                            double minConnectivity, double maxConnectivity, double connectivityGrowStep, int experimentNumber) {
        this.minTaskWeight = minTaskWeight;
        this.maxTaskWeight = maxTaskWeight;
        this.minTaskNumber = minTaskNumber;
        this.maxTaskNumber = maxTaskNumber;
        this.taskNumberGrowStep = taskNumberGrowStep;
        this.minConnectivity = minConnectivity;
        this.maxConnectivity = maxConnectivity;
        this.connectivityGrowStep = connectivityGrowStep;
        this.experimentNumber = experimentNumber;
    }

    @Override
    public String toString() {
        return "StatisticsConfig{" +
                "minTaskWeight=" + minTaskWeight +
                ", maxTaskWeight=" + maxTaskWeight +
                ", minTaskNumber=" + minTaskNumber +
                ", maxTaskNumber=" + maxTaskNumber +
                ", taskNumberGrowStep=" + taskNumberGrowStep +
                ", minConnectivity=" + minConnectivity +
                ", maxConnectivity=" + maxConnectivity +
                ", connectivityGrowStep=" + connectivityGrowStep +
                ", experimentNumber=" + experimentNumber +
                '}';
    }
}
