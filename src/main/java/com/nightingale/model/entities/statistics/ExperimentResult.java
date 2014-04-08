package com.nightingale.model.entities.statistics;

import com.nightingale.view.view_components.modeller.ModellerConstants;

import java.security.AlgorithmConstraints;

/**
 * Created by Nightingale on 06.04.2014.
 */
public class ExperimentResult {
public final int vertexNumber;
    public final double connectivity;
    public final double accelerationFactor, efficiencyFactor, algorithmEfficiencyFactor;
    public final ModellerConstants.ScheduleDescription scheduleType;

    public ExperimentResult(int vertexNumber, double connectivity,
                            double accelerationFactor, double efficiencyFactor, double algorithmEfficiencyFactor, ModellerConstants.ScheduleDescription scheduleType) {
        this.vertexNumber = vertexNumber;
        this.connectivity = connectivity;
        this.accelerationFactor = accelerationFactor;
        this.efficiencyFactor = efficiencyFactor;
        this.algorithmEfficiencyFactor = algorithmEfficiencyFactor;
        this.scheduleType = scheduleType;
    }

    @Override
    public String toString() {
        return "ExperimentResult{" +
                "vertexNumber=" + vertexNumber +
                ", connectivity=" + connectivity +
                ", accelerationFactor=" + accelerationFactor +
                ", efficiencyFactor=" + efficiencyFactor +
                ", algorithmEfficiencyFactor=" + algorithmEfficiencyFactor +
                ", scheduleType=" + scheduleType +
                '}';
    }
}
