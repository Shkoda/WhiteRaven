package com.nightingale.model.common;

import java.io.Serializable;

/**
 * Created by Nightingale on 16.03.14.
 */
public abstract class Connection implements Serializable{
    protected int id;
    protected int firstVertexId, secondVertexId;
    protected double translateX1, translateY1;
    protected double translateX2, translateY2;
    protected String name;
    private int weight;

    public void update(int id, int firstVertexId, int secondVertexId) {
        this.id = id;
        this.firstVertexId = firstVertexId;
        this.secondVertexId = secondVertexId;
        name = "L-" + firstVertexId + "-" + secondVertexId;
        weight = 1;
    }

    public int getId() {
        return id;
    }

    public int getFirstVertexId() {
        return firstVertexId;
    }

    public int getSecondVertexId() {
        return secondVertexId;
    }

    public String getName() {
        return name;
    }

    public double getTranslateX1() {
        return translateX1;
    }

    public Connection setTranslateX1(double translateX1) {
        this.translateX1 = translateX1;
        return this;
    }

    public double getTranslateY1() {
        return translateY1;
    }

    public Connection setTranslateY1(double translateY1) {
        this.translateY1 = translateY1;
        return this;
    }

    public double getTranslateX2() {
        return translateX2;
    }

    public Connection setTranslateX2(double translateX2) {
        this.translateX2 = translateX2;
        return this;
    }

    public double getTranslateY2() {
        return translateY2;
    }

    public Connection setTranslateY2(double translateY2) {
        this.translateY2 = translateY2;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Connection setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
