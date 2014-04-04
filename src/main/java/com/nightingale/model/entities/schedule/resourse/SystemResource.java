package com.nightingale.model.entities.schedule.resourse;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.tick.Tick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nightingale on 29.03.2014.
 */
public abstract class SystemResource<T extends Tick> {

    public final String name;
    public final int id;
    public final boolean isFullDuplex;
    public final double performance;
    public final List<T> resourceTicks;
    public final SystemModel systemModel;


    protected SystemResource(int id, String name, double performance, boolean isFullDuplex, SystemModel systemModel) {
        this.name = name;
        this.id = id;
        this.isFullDuplex = isFullDuplex;
        this.performance = performance;
        resourceTicks = new ArrayList<>();
        this.systemModel = systemModel;
    }

    public T getTick(int time) {
        if (time >= resourceTicks.size())
            systemModel.increaseResourceTime(time * 3 / 2);
        return resourceTicks.get(time);
    }

    public int getDuration(){
        return resourceTicks.size();
    }
    abstract protected void increaseResourceTicsNumber(int toDuration);
}
