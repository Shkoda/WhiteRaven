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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (id != vertex.id) return false;
        if (Double.compare(vertex.weight, weight) != 0) return false;
        if (name != null ? !name.equals(vertex.name) : vertex.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
