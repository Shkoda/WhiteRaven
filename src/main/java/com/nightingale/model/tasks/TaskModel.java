package com.nightingale.model.tasks;

import com.nightingale.model.entities.Vertex;

/**
 * Created by Nightingale on 16.03.14.
 */
public class TaskModel extends Vertex {
    public TaskModel() {
    }

    @Override
    public Vertex update(int id) {
        super.update(id);
        name = "T" + id;
        return this;
    }


    public TaskModel(int id, double weight) {
        this.id = id;
        this.weight = weight;
        name = "T"+id;
    }
}
