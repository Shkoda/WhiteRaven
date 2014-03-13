package com.nightingale.vo;


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

    public ProcessorVO update(int id) {
        this.id = id;
        name = "P" + id;
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

    public ProcessorVO setName(String name) {
        this.name = name;
        return this;
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
}
