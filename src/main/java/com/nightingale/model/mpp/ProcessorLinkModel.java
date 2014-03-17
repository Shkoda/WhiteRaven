package com.nightingale.model.mpp;

import com.nightingale.model.entities.Connection;

/**
 * Created by Nightingale on 09.03.14.
 */
public class ProcessorLinkModel extends Connection {

    private boolean fullDuplexEnabled;

    @Override
    public void update(int id, int firstVertexId, int secondVertexId) {
        super.update(id, firstVertexId, secondVertexId);
        fullDuplexEnabled = true;
    }

    public boolean isFullDuplexEnabled() {
        return fullDuplexEnabled;
    }

    public void setFullDuplexEnabled(boolean fullDuplexEnabled) {
        this.fullDuplexEnabled = fullDuplexEnabled;
    }

    @Override
    public String toString() {
        return "ProcessorLinkModel{" +
                "id=" + id +
                ", firstProcessorId=" + firstVertexId +
                ", secondProcessorId=" + secondVertexId +
                ", translateX1=" + translateX1 +
                ", translateY1=" + translateY1 +
                ", translateX2=" + translateX2 +
                ", translateY2=" + translateY2 +
                ", name='" + name + '\'' +
                ", fullDuplexEnabled=" + fullDuplexEnabled +
                '}';
    }
}
