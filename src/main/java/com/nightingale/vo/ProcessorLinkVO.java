package com.nightingale.vo;

import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 */
public class ProcessorLinkVO implements Serializable {
    private int id;
    private int firstProcessorId, secondProcessorId;
    private double translateX1, translateY1;
    private double translateX2, translateY2;
    private String name;
    private boolean fullDuplexEnabled;


    public void update(int id, int firstProcessorId, int secondProcessorId) {
        this.id = id;
        this.firstProcessorId = firstProcessorId;
        this.secondProcessorId = secondProcessorId;
        name = "L-" + firstProcessorId + "-" + secondProcessorId;
    }

    public double getTranslateX1() {
        return translateX1;
    }

    public ProcessorLinkVO setTranslateX1(double translateX1) {
        this.translateX1 = translateX1;
        return this;
    }

    public double getTranslateY1() {
        return translateY1;
    }

    public ProcessorLinkVO setTranslateY1(double translateY1) {
        this.translateY1 = translateY1;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProcessorLinkVO setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isFullDuplexEnabled() {
        return fullDuplexEnabled;
    }

    public ProcessorLinkVO setFullDuplexEnabled(boolean fullDuplexEnabled) {
        this.fullDuplexEnabled = fullDuplexEnabled;
        return this;
    }

    public int getSecondProcessorId() {
        return secondProcessorId;
    }

    public int getFirstProcessorId() {
        return firstProcessorId;
    }

    public int getId() {
        return id;
    }

    public double getTranslateX2() {
        return translateX2;
    }

    public ProcessorLinkVO setTranslateX2(double translateX2) {
        this.translateX2 = translateX2;
        return this;
    }

    public double getTranslateY2() {
        return translateY2;
    }

    public ProcessorLinkVO setTranslateY2(double translateY2) {
        this.translateY2 = translateY2;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessorLinkVO{" +
                "id=" + id +
                ", firstProcessorId=" + firstProcessorId +
                ", secondProcessorId=" + secondProcessorId +
                ", translateX1=" + translateX1 +
                ", translateY1=" + translateY1 +
                ", translateX2=" + translateX2 +
                ", translateY2=" + translateY2 +
                ", name='" + name + '\'' +
                ", fullDuplexEnabled=" + fullDuplexEnabled +
                '}';
    }
}
