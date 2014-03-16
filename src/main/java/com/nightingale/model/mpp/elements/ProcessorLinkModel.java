package com.nightingale.model.mpp.elements;

import java.io.Serializable;

/**
 * Created by Nightingale on 09.03.14.
 */
public class ProcessorLinkModel implements Serializable {
    private int id;
    private int firstProcessorId, secondProcessorId;
    private double translateX1, translateY1;
    private double translateX2, translateY2;
    private String name;
    private boolean fullDuplexEnabled;
    private int weight;


    public void update(int id, int firstProcessorId, int secondProcessorId) {
        this.id = id;
        this.firstProcessorId = firstProcessorId;
        this.secondProcessorId = secondProcessorId;
        name = "L-" + firstProcessorId + "-" + secondProcessorId;
        fullDuplexEnabled = true;
        weight = 1;
    }

    public double getTranslateX1() {
        return translateX1;
    }

    public ProcessorLinkModel setTranslateX1(double translateX1) {
        this.translateX1 = translateX1;
        return this;
    }

    public double getTranslateY1() {
        return translateY1;
    }

    public ProcessorLinkModel setTranslateY1(double translateY1) {
        this.translateY1 = translateY1;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProcessorLinkModel setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isFullDuplexEnabled() {
        return fullDuplexEnabled;
    }

    public ProcessorLinkModel setFullDuplexEnabled(boolean fullDuplexEnabled) {
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

    public ProcessorLinkModel setTranslateX2(double translateX2) {
        this.translateX2 = translateX2;
        return this;
    }

    public double getTranslateY2() {
        return translateY2;
    }

    public ProcessorLinkModel setTranslateY2(double translateY2) {
        this.translateY2 = translateY2;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public ProcessorLinkModel setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessorLinkModel linkVO = (ProcessorLinkModel) o;

        if (firstProcessorId != linkVO.firstProcessorId) return false;
        if (id != linkVO.id) return false;
        if (secondProcessorId != linkVO.secondProcessorId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstProcessorId;
        result = 31 * result + secondProcessorId;
        return result;
    }

    @Override
    public String toString() {
        return "ProcessorLinkModel{" +
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
