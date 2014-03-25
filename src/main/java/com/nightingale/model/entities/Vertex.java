package com.nightingale.model.entities;

import java.io.Serializable;

/**
 * Created by Nightingale on 16.03.14.
 */
public abstract class Vertex implements Serializable, Informative {
    protected int id;

    protected double translateX, translateY;
    protected String name;
    protected double weight;

    protected Vertex() {
    }

    protected Vertex(int id, double weight) {
        this.id = id;
        this.weight = weight;
        name = "T"+id;
    }

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

    public double getWeight() {
        return weight;
    }

    public Vertex setWeight(double weight) {
        this.weight = weight;
        return this;
    }
}
