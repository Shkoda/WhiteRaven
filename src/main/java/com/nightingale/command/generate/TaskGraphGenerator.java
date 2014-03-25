package com.nightingale.command.generate;

import com.nightingale.model.entities.AcyclicDirectedGraph;

import java.util.Random;

/**
 * Created by Nightingale on 25.03.2014.
 */
public class TaskGraphGenerator {
    private static final Random RANDOM = new Random();

    public static AcyclicDirectedGraph generate(int minTaskWeight, int maxTaskWeight, int taskNumber, double connectivity) {
return null;
    }

    private static void addNVertexes(AcyclicDirectedGraph graph, int minTaskWeight, int maxTaskWeight, int taskNumber) {
        int delta = maxTaskWeight - minTaskWeight;
        int exclusiveMaxBound = maxTaskWeight + 1;
        for (int i = 0; i < taskNumber; i++) {
            int weight = RANDOM.nextInt(exclusiveMaxBound) - delta;
        }
    }
}
