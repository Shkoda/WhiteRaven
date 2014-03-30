package com.nightingale.model.mpp;


import com.nightingale.model.entities.graph.Vertex;

/**
 * Created by Nightingale on 09.03.14.
 * Processor value object
 */
public class ProcessorModel extends Vertex {

    private boolean hasIO;
    private boolean fullDuplexEnabled;
    private int physicalLinkNumber;

    @Override
    public Vertex update(int id) {
        super.update(id);
        name = "P" + id;
        hasIO = true;
        fullDuplexEnabled = true;
        physicalLinkNumber = 1;
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

    public int getPhysicalLinkNumber() {
        return physicalLinkNumber;
    }

    public ProcessorModel setPhysicalLinkNumber(int physicalLinkNumber) {
        this.physicalLinkNumber = physicalLinkNumber;
        return this;
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
                ", physicalLinkNumber=" + physicalLinkNumber +
                '}';
    }
}
