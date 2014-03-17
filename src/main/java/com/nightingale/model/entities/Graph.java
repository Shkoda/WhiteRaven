package com.nightingale.model.entities;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    public Graph(Class<V> vertexClass, Class<C> connectionClass) {
        this.vertexClass = vertexClass;
        this.connectionClass = connectionClass;
        vertexes = new HashMap<>();
        connections = new HashMap<>();
    }


    @SuppressWarnings("unchecked")
    public V addVertex() {
        int id = vertexIdGenerator.incrementAndGet();
        try {
            Vertex vertex = vertexClass.newInstance();
            vertex.update(id);
            vertexes.put(id, (V) vertex);
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
    }


    @SuppressWarnings("unchecked")
    public C linkVertexes(int firstVertexId, int secondVertexId) {
        int id = connectionIdGenerator.incrementAndGet();
        try {
            Connection connection = connectionClass.newInstance();
            connection.update(id,firstVertexId,secondVertexId);
            connections.put(id, (C) connection);
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


    public void removeConnection(int connectionId) {
        connections.remove(connectionId);
    }


    public Collection<V> getVertexes() {
        return vertexes.values();
    }

    public Collection<C> getConnections() {
        return connections.values();
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
