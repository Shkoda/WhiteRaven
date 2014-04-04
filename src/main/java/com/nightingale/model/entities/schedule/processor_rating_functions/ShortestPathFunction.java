package com.nightingale.model.entities.schedule.processor_rating_functions;

import com.nightingale.model.entities.schedule.Paths;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

/**
 * Created by Nightingale on 04.04.2014.
 */
public  class ShortestPathFunction implements BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> {
    private Paths paths;
    private Map<Integer, ProcessorResource> taskOnProcessorsMap;

    public ShortestPathFunction(Paths paths, Map<Integer, ProcessorResource> taskOnProcessorsMap) {
        this.paths = paths;
        this.taskOnProcessorsMap = taskOnProcessorsMap;
    }

    @Override
    public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks) {
        Collections.sort(processorResources, (p1, p2) -> transmitRating(p1, tasks).compareTo(transmitRating(p2, tasks)));
        return processorResources.get(0);
    }

    private Double transmitRating(ProcessorResource processorResource, List<Task> tasks) {
        if (tasks.isEmpty())
            return 0.0;
        DoubleStream doubleStream = tasks.stream()
                .mapToDouble(task -> paths.getPath(taskOnProcessorsMap.get(task.id), processorResource).length);
        return processorResource.physicalLinkNumber == 1 ?
                doubleStream.sum() :
                doubleStream.max().getAsDouble();
    }
}