package com.nightingale.command.modelling.critical_path_functions;

import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.DeadlineDifferenceConsumer;
import com.nightingale.model.tasks.TaskModel;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Nightingale on 23.03.2014.
 */
public class CriticalPath {

    public static List<AcyclicDirectedGraph.Node> find(AcyclicDirectedGraph.Node node, Function<List<AcyclicDirectedGraph.Node>, Number> pathRateFunction,
                                                       Direction direction) {
        List<AcyclicDirectedGraph.Node> nodes = find(node, new PathComparator(pathRateFunction), direction,
                new ArrayList<>(), null);
        nodes.remove(node);
        return nodes;
    }

    public static List<AcyclicDirectedGraph.Node> find(AcyclicDirectedGraph graph, Function<List<AcyclicDirectedGraph.Node>, Number> pathRateFunction,
                                                       Direction direction) {
        Map<Double, List<AcyclicDirectedGraph.Node>> paths = new HashMap<>();
        graph.getRoots().forEach(root -> {
            List<AcyclicDirectedGraph.Node> criticalPath = find(root, new PathComparator(pathRateFunction), direction,
                    new ArrayList<>(), null);
            paths.put(pathRateFunction.apply(criticalPath).doubleValue(), criticalPath);
        });

        return paths.get(paths.keySet().parallelStream().max(Double::compare).get());
    }

    private static List<AcyclicDirectedGraph.Node> find(AcyclicDirectedGraph.Node currentNode, Comparator<List<AcyclicDirectedGraph.Node>> comparator,
                                                        Direction direction,
                                                        List<AcyclicDirectedGraph.Node> existingPath,
                                                        List<AcyclicDirectedGraph.Node> criticalPath) {
        existingPath.add(currentNode);
        List<AcyclicDirectedGraph.Node> targetList = direction == Direction.UP ?
                currentNode.getParents() : currentNode.getChildren();

        if (targetList.isEmpty()) {
            return comparator.compare(existingPath, criticalPath) > 0 ? existingPath : criticalPath;
        }

        for (AcyclicDirectedGraph.Node nextNode : targetList) {
            ArrayList<AcyclicDirectedGraph.Node> path = new ArrayList<>(existingPath);
            List<AcyclicDirectedGraph.Node> newCriticalPath = find(nextNode, comparator, direction, path, criticalPath);
            if (comparator.compare(newCriticalPath, criticalPath) > 0)
                criticalPath = newCriticalPath;
        }
        return criticalPath;
    }

    public enum Direction {
        UP, DOWN
    }
}
