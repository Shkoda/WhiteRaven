package com.nightingale.model.tasks.elements;

import com.nightingale.model.common.Vertex;

/**
 * Created by Nightingale on 16.03.14.
 */
public class TaskModel extends Vertex {
    @Override
    public Vertex update(int id) {
        super.update(id);
        name = "T" + id;
        return this;
    }
}
