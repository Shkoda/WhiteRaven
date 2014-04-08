package com.nightingale.model.entities.schedule.processor_rating_functions;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by Nightingale on 08.04.2014.
 */
public enum ProcessorRatingFunctionClass {
    MAX_CONNECTIVITY {
        @Override
        public BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> buildFunction(SystemModel systemModel) {
            return new MaxConnectivityFunction();
        }
    },
    SHORTEST_PATH {
        @Override
        public BiFunction<List<ProcessorResource>, List<Task>, ProcessorResource> buildFunction(SystemModel systemModel) {
            return new ShortestPathFunction(systemModel.getPaths(), systemModel.getTaskOnProcessorsMap());
        }
    };

    public abstract BiFunction<List<ProcessorResource>, List<Task>,
            ProcessorResource> buildFunction(SystemModel systemModel);

}
