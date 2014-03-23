package com.nightingale.view.utils;

import com.nightingale.model.entities.Vertex;
import com.nightingale.utils.Loggers;
import javafx.scene.Node;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Nightingale on 20.03.2014.
 */
public class AcyclicDirectedGraph implements Serializable {
    private Map<Integer, Node> roots = new HashMap<>();
    private Set<Integer> ids = new HashSet<>();


    public boolean addVertex(int id) {
        if (ids.contains(id))
            return false;
        ids.add(id);
        Node node = new Node(id);
        roots.put(id, node);
        return true;
    }

    public boolean removeVertex(int id) {
        Node removeNode = search(id);
        if (removeNode == null)
            return false;
        for (Node child : removeNode.children)
            removeParent(child, removeNode);
        for (Node parent : removeNode.parents)
            parent.children.remove(removeNode);
        if (roots.containsKey(id))
            roots.remove(id);
        return true;
    }

    private void removeParent(Node child, Node parent) {
        child.parents.remove(parent);
        if (child.parents.isEmpty())
            roots.put(child.id, child);
    }


    public boolean addLink(int parentId, int childId) {
        Node parent = search(parentId);
        Node child = search(childId);
        Loggers.debugLogger.debug("parent: " + parent);
        Loggers.debugLogger.debug("child: " + child);
        if (parent == null || child == null || areConnected(child, parentId))
            return false;
        if (isRoot(childId))
            roots.remove(childId);
        parent.children.add(child);
        child.parents.add(parent);
        return true;
    }

    public boolean removeLink(int parentId, int childId) {
        Node parent = search(parentId);
        Node child = search(childId);
        if (parent == null || child == null || !parent.children.contains(child))
            return false;
        parent.children.remove(child);
        removeParent(child, parent);
        return true;
    }

    private boolean isRoot(int nodeId) {
        return roots.containsKey(nodeId);
    }


    private boolean areConnected(Node node, int childId) {
        for (Node child : node.children) {
            if (child.id == childId || areConnected(child, childId))
                return true;
        }
        return false;
    }


    private Node search(int id) {
        for (Node root : roots.values()) {
            Node node = search(id, root);
            if (node != null)
                return node;
        }
        return null;
    }

    private Node search(int id, Node startNode) {
        if (startNode.id == id)
            return startNode;
        for (Node child : startNode.children) {
            if (child.id == id)
                return child;
            Node node = search(id, child);
            if (node != null)
                return node;
        }
        return null;
    }

    private class Node implements Serializable {
        int id;
        List<Node> parents;
        List<Node> children;

        

        private Node(int id) {
            this.id = id;
            parents = new ArrayList<>();
            children = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node{id=").append(id).append(", parents=[");
            for (Node parent : parents)
                builder.append(parent.id).append(" ");
            builder.append("], children=[");
            for (Node child : children)
                builder.append(child.id).append(" ");
            builder.append("]}");
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (id != node.id) return false;
            if (children != null ? !children.equals(node.children) : node.children != null) return false;
            if (parents != null ? !parents.equals(node.parents) : node.parents != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (parents != null ? parents.hashCode() : 0);
            result = 31 * result + (children != null ? children.hashCode() : 0);
            return result;
        }
    }
}
