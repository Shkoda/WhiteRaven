package com.nightingale.model.entities.schedule.resourse;
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


    protected SystemResource(int id, String name, double performance, boolean isFullDuplex) {
        this.name = name;
        this.id = id;
        this.isFullDuplex = isFullDuplex;
        this.performance = performance;
        resourceTicks = new ArrayList<>();
    }

    public T getTick(int time) {
        if (time>= resourceTicks.size())
            increaseResourceTicsNumber();
        return resourceTicks.get(time);
    }

    abstract protected void increaseResourceTicsNumber();
}
