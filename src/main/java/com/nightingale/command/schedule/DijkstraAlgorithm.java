package com.nightingale.command.schedule;

import com.nightingale.model.entities.Connection;
import com.nightingale.model.entities.Graph;
import com.nightingale.model.entities.Vertex;

import java.util.*;

public class DijkstraAlgorithm<V extends Vertex, C extends Connection> {

    private final List<V> nodes;
    private final List<C> edges;
    private Set<V> settledNodes;
    private Set<V> unSettledNodes;
    private Map<V, V> predecessors;
    private Map<V, Double> distance;
    private Map<Integer, V> vertexIdMap;

    public DijkstraAlgorithm(Graph<V, C> graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<>(graph.getVertexes());
        this.edges = new ArrayList<>(graph.getConnections());
        vertexIdMap = new HashMap<>(graph.getVertexIdMap());
    }

    public void execute(V source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            V node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(V node) {
        List<V> adjacentNodes = getNeighbors(node);
        adjacentNodes.stream()
                .filter(target -> getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target))
                .forEach(target -> {
                    distance.put(target, getShortestDistance(node) + getDistance(node, target));
                    predecessors.put(target, node);
                    unSettledNodes.add(target);
                });

    }

    private double getDistance(V src, V target) {
        for (C edge : edges) {
            if (isConnectingEdge(edge, src, target))
                return edge.getWeight();
        }
        throw new RuntimeException("Should not happen");
    }

    private boolean isConnectingEdge(C edge, V src, V dst) {
        return ((edge.getFirstVertexId() == src.getId() && edge.getSecondVertexId() == dst.getId())
                || (edge.getFirstVertexId() == dst.getId() && edge.getSecondVertexId() == src.getId()));
    }

    private List<V> getNeighbors(V node) {
        List<V> neighbors = new ArrayList<>();
        for (C edge : edges) {
            if (edge.getFirstVertexId() == node.getId() && !isSettled(vertexIdMap.get(edge.getSecondVertexId())))
                neighbors.add(vertexIdMap.get(edge.getSecondVertexId()));
            else   if (edge.getSecondVertexId() == node.getId() && !isSettled(vertexIdMap.get(edge.getFirstVertexId())))
                neighbors.add(vertexIdMap.get(edge.getFirstVertexId()));
        }
        return neighbors;
    }

    private V getMinimum(Set<V> vertexes) {
        V minimum = null;
        for (V vertex : vertexes) {
            minimum = (minimum == null || getShortestDistance(vertex) < getShortestDistance(minimum)) ?
                    vertex : minimum;
        }
        return minimum;
    }

    private boolean isSettled(V vertex) {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(V destination) {
        return distance.get(destination) == null ? Double.MAX_VALUE : distance.get(destination);
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public List<V> getPath(V target) {
        List<V> path = new ArrayList<>();
        V step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            path.add(target);
            return path;
         //   return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }


}