package com.nightingale.model.entities.graph;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Nightingale on 16.03.14.
 */
public abstract class Connection extends Observable implements Serializable, Informative {
    protected int id;
    protected int firstVertexId, secondVertexId;
    protected double translateX1, translateY1;
    protected double translateX2, translateY2;
    protected String name;
    private double weight;

    public void update(int id, int firstVertexId, int secondVertexId) {
        this.id = id;
        this.firstVertexId = firstVertexId;
        this.secondVertexId = secondVertexId;
        name = "L-" + firstVertexId + "-" + secondVertexId;
        weight = 1;
        setChanged();
        notifyObservers(weight);
    }

    public Connection increaseWeightByOne() {
        weight += 1;
        setChanged();
        notifyObservers(weight);
        return this;
    }

    public int getOtherVertexId(int thisId){
        return thisId == firstVertexId ? secondVertexId : firstVertexId;
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

    public double getWeight() {
        return weight;
    }

    public Connection setWeight(double weight) {
        this.weight = weight;
        setChanged();
        notifyObservers(weight);
        return this;
    }
}
