package com.nightingale.model.entities.schedule.processor_rating_functions;

import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.utils.TriFunction;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 04.04.2014.
 */
public class MaxConnectivityFunction implements TriFunction<List<ProcessorResource>, List<Task>, Task, ProcessorResource> {
    private static final Random random = new Random();

    @Override
    public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks, Task task) {
        Map<Integer, List<ProcessorResource>> processorRatings
                = processorResources.stream().collect(Collectors.groupingBy(ProcessorResource::getConnectivity));

        List<ProcessorResource> bestProcessors = processorRatings.get(processorRatings.keySet().stream()
                .max(Double::compare).get());
        return bestProcessors.get(random.nextInt(bestProcessors.size()));
    }


}