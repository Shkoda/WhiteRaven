package com.nightingale.model.entities.graph;

import com.nightingale.utils.Loggers;
import com.nightingale.view.utils.WeightedQuickUnionUF;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 16.03.14.
 */
public class Graph<V extends Vertex, C extends Connection> implements Serializable {
    private AtomicInteger vertexIdGenerator = new AtomicInteger(0);
    private AtomicInteger connectionIdGenerator = new AtomicInteger(0);

    private Map<Integer, V> vertexes;
    private Map<Integer, C> connections;

    private Class<V> vertexClass;
    private Class<C> connectionClass;

    private boolean acyclic;
    private AcyclicDirectedGraph acyclicDirectedGraph;

    public Graph(Class<V> vertexClass, Class<C> connectionClass, boolean acyclic) {
        this.vertexClass = vertexClass;
        this.connectionClass = connectionClass;
        vertexes = new HashMap<>();
        connections = new HashMap<>();
        this.acyclic = acyclic;
        acyclicDirectedGraph = new AcyclicDirectedGraph();
    }

    public V getVertex(int id) {
        return vertexes.get(id);
    }

    public int getVertexNumber() {
        return vertexes.size();
    }

    public double getConnectivity() {
        double vertexSumWeight = vertexes.values().parallelStream().mapToDouble(V::getWeight).sum();
        double linkSumWeight = connections.values().parallelStream().mapToDouble(C::getWeight).sum();
        return vertexSumWeight / (vertexSumWeight + linkSumWeight);
    }

    public boolean isEmpty() {
        return vertexes.isEmpty();
    }

    public boolean isConnectedGraph() {
        if (vertexes.isEmpty())
            return false;
        Integer maxId = vertexes.keySet().stream().max(Integer::compare).get();

        WeightedQuickUnionUF unionUF = new WeightedQuickUnionUF(maxId + 1);

        connections.values().stream().forEach(c -> unionUF.union(c.firstVertexId, c.secondVertexId));

        return vertexes.values().stream()
                .filter(v -> !unionUF.connected(maxId, v.id))
                .collect(Collectors.toList())
                .size() == 0;
    }


    @SuppressWarnings("unchecked")
    public V addVertex() {
        int id = vertexIdGenerator.incrementAndGet();
        try {
            Vertex vertex = vertexClass.newInstance();
            vertex.update(id);
            vertexes.put(id, (V) vertex);
            if (acyclic)
                acyclicDirectedGraph.addVertex(vertex);
            return (V) vertex;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeVertex(int vertexId) {
        vertexes.remove(vertexId);
        List<Integer> connectedLinks = new ArrayList<>();
        for (Map.Entry<Integer, C> kv : connections.entrySet()) {
            Connection connection = kv.getValue();
            if (connection.getFirstVertexId() == vertexId || connection.getSecondVertexId() == vertexId)
                connectedLinks.add(kv.getKey());
        }
        connections.keySet().removeAll(connectedLinks);
        if (acyclic)
            acyclicDirectedGraph.removeVertex(vertexId);
    }


    @SuppressWarnings("unchecked")
    public C linkVertexes(int firstVertexId, int secondVertexId) {
        if (vertexes.get(firstVertexId) == null)
            throw new NullPointerException("Vertex " + firstVertexId + " doesn't exist!");
        if (vertexes.get(secondVertexId) == null)
            throw new NullPointerException("Vertex " + secondVertexId + " doesn't exist!");

        int id = connectionIdGenerator.incrementAndGet();
        try {
            if (acyclic && !acyclicDirectedGraph.isConnectionAllowed(firstVertexId, secondVertexId)) {
                Loggers.debugLogger.debug("acyclic && !acyclicDirectedGraph.isConnectionAllowed(firstVertexId, secondVertexId)");
                return null;
            }



//            if (acyclic) {
//                boolean linkingSuccessful = acyclicDirectedGraph.addLink(firstVertexId, secondVertexId);
//                if (!linkingSuccessful)
//                    return null;
//            }
            Connection connection = connectionClass.newInstance();
            connection.update(id, firstVertexId, secondVertexId);
            connections.put(id, (C) connection);
            if (acyclic)
                acyclicDirectedGraph.addLink(connection);
            return (C) connection;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean areConnected(int firstVertexId, int secondVertexId) {
        for (Connection connection : connections.values())
            if (connection.getFirstVertexId() == firstVertexId && connection.getSecondVertexId() == secondVertexId ||
                    connection.getSecondVertexId() == firstVertexId && connection.getFirstVertexId() == secondVertexId)
                return true;
        return false;
    }

    public C getRandomConnection() {
        List<Integer> ids = new ArrayList<>(connections.keySet());
        return connections.get(ids.get((int) (Math.random() * ids.size())));
    }

    public C getConnection(int srcId, int dstId) {
        List<C> link = connections.values().stream()
                .filter(c -> c.getFirstVertexId() == srcId && c.getSecondVertexId() == dstId)
                .collect(Collectors.toList());
        return link.isEmpty() ? null : link.get(0);
    }

    public void removeConnection(int connectionId) {
        C removed = connections.remove(connectionId);
        if (acyclic && removed != null)
            acyclicDirectedGraph.removeLink(removed.firstVertexId, removed.secondVertexId);
    }

    public AcyclicDirectedGraph getAcyclicDirectedGraph() {
        return acyclicDirectedGraph;
    }

    public Collection<V> getVertexes() {
        return vertexes.values();
    }

    public Collection<C> getConnections() {
        return connections.values();
    }

    public Map<Integer, V> getVertexIdMap() {
        return vertexes;
    }


    public int getMaxVertexId() {
        return vertexIdGenerator.get();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertexIdGenerator=" + vertexIdGenerator +
                ", connectionIdGenerator=" + connectionIdGenerator +
                ", vertexes=" + vertexes +
                ", connections=" + connections +
                ", vertexClass=" + vertexClass +
                ", connectionClass=" + connectionClass +
                '}';
    }
}
