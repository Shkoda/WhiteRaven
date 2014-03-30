package com.nightingale.model.tasks;

import com.nightingale.model.entities.graph.Connection;

/**
 * Created by Nightingale on 16.03.14.
 */
public class TaskLinkModel extends Connection{
    @Override
    public String toString() {
        return "TaskLinkModel{" +
                "id=" + id +
                ", parentTaskId=" + firstVertexId +
                ", childTaskId=" + secondVertexId +
                ", translateX1=" + translateX1 +
                ", translateY1=" + translateY1 +
                ", translateX2=" + translateX2 +
                ", translateY2=" + translateY2 +
                ", name='" + name + '\''+
                '}';
    }
}
