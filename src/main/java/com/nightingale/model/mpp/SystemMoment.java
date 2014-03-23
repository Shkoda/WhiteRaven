package com.nightingale.model.mpp;

import java.util.Map;

/**
 * Created by Nightingale on 21.03.2014.
 */
public class SystemMoment {
    private int stepNumber;
    private Map<String, String> processorUsage;
    private Map<String, String> linkUsage;

    public SystemMoment(int stepNumber, Map<String, String> processorUsage, Map<String, String> linkUsage) {
        this.stepNumber = stepNumber;
        this.processorUsage = processorUsage;
        this.linkUsage = linkUsage;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public Map<String, String> getProcessorUsage() {
        return processorUsage;
    }

    public void setProcessorUsage(Map<String, String> processorUsage) {
        this.processorUsage = processorUsage;
    }

    public Map<String, String> getLinkUsage() {
        return linkUsage;
    }

    public void setLinkUsage(Map<String, String> linkUsage) {
        this.linkUsage = linkUsage;
    }
}
