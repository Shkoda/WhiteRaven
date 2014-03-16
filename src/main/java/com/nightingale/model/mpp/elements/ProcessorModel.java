package com.nightingale.model.mpp.elements;


import com.nightingale.model.common.Vertex;

/**
 * Created by Nightingale on 09.03.14.
 * Processor value object
 */
public class ProcessorModel extends Vertex {

    private boolean hasIO;
    private boolean fullDuplexEnabled;

    @Override
    public Vertex update(int id) {
        super.update(id);
        name = "P" + id;
        hasIO = true;
        fullDuplexEnabled = true;
        return this;
    }

    public boolean isHasIO() {
        return hasIO;
    }

    public void setHasIO(boolean hasIO) {
        this.hasIO = hasIO;
    }

    public boolean isFullDuplexEnabled() {
        return fullDuplexEnabled;
    }

    public void setFullDuplexEnabled(boolean fullDuplexEnabled) {
        this.fullDuplexEnabled = fullDuplexEnabled;
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
                ", performance=" + weight +
                '}';
    }
}
