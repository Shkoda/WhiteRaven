package com.nightingale.model.mpp.elements;


import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 * Processor value object
 */
public class ProcessorModel implements Serializable {
    private int id;

    private double translateX, translateY;
    private String name;
    private boolean hasIO;
    private boolean fullDuplexEnabled;
    private double performance;


    public ProcessorModel update(int id) {
        this.id = id;
        name = "P" + id;
        performance = 1;
        hasIO = true;
        fullDuplexEnabled = true;
        return this;
    }

    public double getTranslateX() {
        return translateX;
    }

    public ProcessorModel setTranslateX(double translateX) {
        this.translateX = translateX;
        return this;
    }

    public int getId() {
        return id;
    }

    public double getTranslateY() {
        return translateY;
    }

    public ProcessorModel setTranslateY(double translateY) {
        this.translateY = translateY;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isHasIO() {
        return hasIO;
    }

    public ProcessorModel setHasIO(boolean hasIO) {
        this.hasIO = hasIO;
        return this;
    }

    public boolean isFullDuplexEnabled() {
        return fullDuplexEnabled;
    }

    public ProcessorModel setFullDuplexEnabled(boolean fullDuplexEnabled) {
        this.fullDuplexEnabled = fullDuplexEnabled;
        return this;
    }

    public double getPerformance() {
        return performance;
    }

    public ProcessorModel setPerformance(double performance) {
        this.performance = performance;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessorModel{" +
                "id=" + id +
                ", translateX=" + translateX +
                ", translateY=" + translateY +
                ", name='" + name + '\'' +
                ", hasIO=" + hasIO +
                ", fullDuplexEnabled=" + fullDuplexEnabled +
                ", performance=" + performance +
                '}';
    }
}
