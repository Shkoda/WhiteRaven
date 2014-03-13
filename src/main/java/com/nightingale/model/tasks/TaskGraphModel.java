package com.nightingale.model.tasks;

import com.google.inject.Singleton;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class TaskGraphModel implements ITaskGraphModel {

    @Override
    public String toString() {
        return "TaskGraphModel{" +
                '}';
    }

    @Override
    public void reset(ITaskGraphModel other) {

    }
}
