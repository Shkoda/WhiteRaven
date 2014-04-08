package com.nightingale.model.entities.schedule.processor_rating_functions;

import com.nightingale.model.entities.schedule.Paths;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Created by Nightingale on 04.04.2014.
 */
public class ShortestPathFunction implements BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> {
    private static final Random random = new Random();
    private Paths paths;
    private Map<Integer, ProcessorResource> taskOnProcessorsMap;

    public ShortestPathFunction(Paths paths, Map<Integer, ProcessorResource> taskOnProcessorsMap) {
        this.paths = paths;
        this.taskOnProcessorsMap = taskOnProcessorsMap;
    }

    @Override
    public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks) {
        Map<Double, List<ProcessorResource>> processorRatings
                = processorResources.stream().collect(Collectors.groupingBy(p -> transmitRating(p, tasks)));

        List<ProcessorResource> bestProcessors = processorRatings.get(processorRatings.keySet().stream().max(Double::compare).get());
        return bestProcessors.get(random.nextInt(bestProcessors.size()));
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