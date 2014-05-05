package com.nightingale.model.entities.schedule.processor_rating_functions;

import com.nightingale.model.entities.schedule.Paths;
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
public class ShortestPathFunction implements TriFunction<List<ProcessorResource>, List<Task>, Task, ProcessorResource> {
    private static final Random random = new Random();
    private static final MaxConnectivityFunction MAX_CONNECTIVITY_FUNCTION = new MaxConnectivityFunction();
    private Paths paths;
    private Map<Integer, ProcessorResource> taskOnProcessorsMap;

    public ShortestPathFunction(Paths paths, Map<Integer, ProcessorResource> taskOnProcessorsMap) {
        this.paths = paths;
        this.taskOnProcessorsMap = taskOnProcessorsMap;
    }

    @Override
    public ProcessorResource apply(List<ProcessorResource> processorResources, List<Task> tasks, Task task) {
        if (tasks.isEmpty())
            return MAX_CONNECTIVITY_FUNCTION.apply(processorResources, tasks, task);

        Map<Double, List<ProcessorResource>> processorRatings
                = processorResources.stream().collect(Collectors.groupingBy(p -> transmitTime(p, tasks, task)));

        List<ProcessorResource> lowestTransmissionTimeProcessors = processorRatings
                .get(processorRatings.keySet().stream()
                        .min(Double::compare).get());
        return lowestTransmissionTimeProcessors.get(random.nextInt(lowestTransmissionTimeProcessors.size()));
    }


    private Double transmitTime(ProcessorResource processorResource, List<Task> parents, Task child) {
        return parents.stream()
                .mapToDouble(parent -> {
                    Paths.Path path = paths.getPath(taskOnProcessorsMap.get(parent.id), processorResource); //find path
                    return path.links.values().isEmpty() ? 0 :  //if path not empty return sum(transmission weight / link width)
                            path.links.values().stream()
                                    .mapToDouble(link -> Math.ceil(parent.childrenMap.get(child) / link.getWeight())).sum();
                })
                .sum();


    }
}