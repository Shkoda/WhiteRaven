package com.nightingale.model.functions;

import com.nightingale.model.entities.AcyclicDirectedGraph;
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
        List<AcyclicDirectedGraph.Node> path = paths.get(paths.keySet().parallelStream().max(Double::compare).get());
        printIds(path);
        return path;
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
            //  path.add(nextNode);
            List<AcyclicDirectedGraph.Node> newCriticalPath = find(nextNode, comparator, direction, path, criticalPath);
            if (comparator.compare(newCriticalPath, criticalPath) > 0)
                criticalPath = newCriticalPath;
        }

        return criticalPath;
    }

    public enum Direction {
        UP, DOWN
    }

    public static void main(String[] args) {
        AcyclicDirectedGraph graph = new AcyclicDirectedGraph();
        graph.addVertex(new TaskModel().update(1));
        graph.addVertex(new TaskModel().update(2));
        graph.addVertex(new TaskModel().update(3));
        graph.addVertex(new TaskModel().update(4));
        graph.addVertex(new TaskModel().update(5));
        graph.addVertex(new TaskModel().update(6));
        graph.addVertex(new TaskModel().update(7));
        graph.addVertex(new TaskModel().update(8));

        graph.addLink(1, 2);
        graph.addLink(2, 4);
        graph.addLink(2, 5);
        graph.addLink(1, 3);
        graph.addLink(3, 6);
        graph.addLink(6, 7);
        graph.addLink(5, 7);
        graph.addLink(8, 3);

//        AcyclicDirectedGraph.Node node = graph.search(3);
//        Comparator<List<AcyclicDirectedGraph.Node>> comparator = new PathComparator(PathComparator.VERTEX_NUMBER_FUNCTION);
//
//        List<AcyclicDirectedGraph.Node> nodes = CriticalPath.find(graph, PathComparator.VERTEX_NUMBER_FUNCTION, Direction.DOWN);
        //     printNodes(nodes);

        new DeadlineDifferenceConsumer().accept(graph);
        printNodes(graph.getNodes());

    }

    private static void printNodes(Collection<AcyclicDirectedGraph.Node> nodes) {
        System.out.println("-------------------------");
        nodes.forEach(n -> System.out.println(n.getId() + " -> " + n.getRating()));
        System.out.println();
    }


    private static void printIds(Collection<AcyclicDirectedGraph.Node> nodes) {
        System.out.println("-------------------------");
        nodes.forEach(n -> System.out.print(n.getId() + " "));
        System.out.println();
    }
}
