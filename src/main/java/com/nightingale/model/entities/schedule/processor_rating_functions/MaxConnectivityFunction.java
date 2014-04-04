package com.nightingale.model.entities.schedule.processor_rating_functions;

import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 04.04.2014.
 */
public  class MaxConnectivityFunction implements BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> {
    private static final Random random = new Random();
    @Override
    public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks) {
        int maxConnectivity = processorResources.stream()
                .mapToInt(ProcessorResource::getConnectivity)
                .max().getAsInt();
        List<ProcessorResource> maxConnectivityProcessors = processorResources.stream()
                .filter(p -> p.getConnectivity() == maxConnectivity)
                .collect(Collectors.toList());
        return maxConnectivityProcessors.get(random.nextInt(maxConnectivityProcessors.size()));
    }
}