package com.nightingale.vo;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.adapter.JavaBeanDoubleProperty;

import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 * Processor value object
 */
public class ProcessorVO implements Serializable {
    private int id;

    private double translateX, translateY;
    private String name;
    private boolean hasIO;
    private boolean fullDuplexEnabled;
    private double performance;


    public ProcessorVO update(int id) {
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

    public ProcessorVO setTranslateX(double translateX) {
        this.translateX = translateX;
        return this;
    }

    public int getId() {
        return id;
    }

    public double getTranslateY() {
        return translateY;
    }

    public ProcessorVO setTranslateY(double translateY) {
        this.translateY = translateY;
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isHasIO() {
        return hasIO;
    }

    public ProcessorVO setHasIO(boolean hasIO) {
        this.hasIO = hasIO;
        return this;
    }

    public boolean isFullDuplexEnabled() {
        return fullDuplexEnabled;
    }

    public ProcessorVO setFullDuplexEnabled(boolean fullDuplexEnabled) {
        this.fullDuplexEnabled = fullDuplexEnabled;
        return this;
    }

    public double getPerformance() {
        return performance;
    }

    public ProcessorVO setPerformance(double performance) {
        this.performance = performance;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessorVO{" +
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
