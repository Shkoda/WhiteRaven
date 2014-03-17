package com.nightingale.model.entities;

import java.io.Serializable;

/**
 * Created by Nightingale on 16.03.14.
 */
public abstract class Vertex implements Serializable {
    protected int id;

    protected double translateX, translateY;
    protected String name;
    protected int weight;

    public Vertex update(int id) {
        this.id = id;
        weight = 1;
        return this;
    }

    public int getId() {
        return id;
    }

    public double getTranslateX() {
        return translateX;
    }

    public Vertex setTranslateX(double translateX) {
        this.translateX = translateX;
        return this;
    }

    public double getTranslateY() {
        return translateY;
    }

    public Vertex setTranslateY(double translateY) {
        this.translateY = translateY;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
