package com.nightingale.model.entities.graph;

import com.nightingale.view.view_components.modeller.ModellerConstants;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 20.03.2014.
 */
public class AcyclicDirectedGraph implements Serializable {
    private Map<Integer, Node> roots = new HashMap<>();
    private Map<Integer, Node> ids = new HashMap<>();
    private Map<Integer, List<Connection>> outerConnections = new HashMap<>();
    private static final transient Comparator<Node> increaseRatingComparator = (o1, o2) -> ((Double) o1.getRating()).compareTo(o2.getRating());
    private static final transient Comparator<Node> decreaseRatingComparator = (o1, o2) -> (-(((Double) o1.getRating()).compareTo(o2.getRating())));


    public Collection<Node> getRoots() {
        return roots.values();
    }

    public Collection<Node> getNodes() {
        return ids.values();
    }

    public boolean isEmpty() {
        return roots.isEmpty();
    }

    public boolean addVertex(Vertex vertex) {
        if (ids.keySet().contains(vertex.id))
            return false;
        Node node = new Node(vertex);
        ids.put(vertex.id, node);
        roots.put(vertex.id, node);
        return true;
    }

    public Vertex getVertex(int id) {
        Node search = search(id);
        return search == null ? null : search.vertex;
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

    public boolean isConnectionAllowed(int parentId, int childId) {
        Node parent = ids.get(parentId);
        Node child = ids.get(childId);
        return !(parent == null || child == null || edgeExist(parent, child) || isParent(child, parentId) || parent == child);
    }

    public boolean addLink(Connection connection) {
        //    Node parent = search(parentId);
        //   Node child = search(childId);

        //   if (parent == null || child == null || edgeExist(parent, child) || isParent(child, parentId) || parent == child)
        //      return false;
        Node parent = ids.get(connection.firstVertexId);
        Node child = ids.get(connection.secondVertexId);

        if (isRoot(child.id))
            roots.remove(child.id);
        parent.children.add(child);
        child.parents.add(parent);
        outerConnections.putIfAbsent(parent.id, new ArrayList<>());
        outerConnections.get(parent.id).add(connection);
        return true;
    }

    public Connection getConnection(int srcId, int dstId) {
        return outerConnections.get(srcId).stream()
                .filter(c -> c.getSecondVertexId() == dstId)
                .collect(Collectors.toList()).get(0);
    }

    public boolean addLink(int parentId, int childId) {
        //    Node parent = search(parentId);
        //   Node child = search(childId);

        //   if (parent == null || child == null || edgeExist(parent, child) || isParent(child, parentId) || parent == child)
        //      return false;
        Node parent = ids.get(parentId);
        Node child = ids.get(childId);

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

    private boolean edgeExist(Node parent, Node child) {
        return parent.children.contains(child);
    }


    private boolean isParent(Node parent, int childId) {
        for (Node child : parent.children) {
            if (child.id == childId || isParent(child, childId))
                return true;
        }
        return false;
    }


    public Node search(int id) {
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

    public List<Node> getTaskQueue(ModellerConstants.QueueType queueType) {
        queueType.taskRankConsumer.accept(this);
        List<Node> nodes = new ArrayList<>(ids.values());
        Comparator<AcyclicDirectedGraph.Node> comparator = queueType.taskSortingIncreaseOrder ?
                increaseRatingComparator : decreaseRatingComparator;
        nodes.sort(comparator);
        checkParentDependencies(nodes);
        return nodes;
    }

    private void checkParentDependencies(List<Node> queue) {
        for (int i = 0; i < queue.size(); i++) {
            Node current = queue.get(i);
            if (current.parents.isEmpty())      //if hasn't parents dependencies are always OK
                continue;
            List<Node> alreadyLoaded = queue.subList(0, i);
            List<Node> notLoadedParents = current.parents.stream()
                    .filter(parent -> !alreadyLoaded.contains(parent)).collect(Collectors.toList());
            if (notLoadedParents.isEmpty())     //if all parents were loaded continue
                continue;

            int maxParentIndex = notLoadedParents.stream().mapToInt(queue::indexOf).max().getAsInt();
            shift(queue, i, maxParentIndex);
            i = -1;
        }

    }

    private void shift(List<Node> list, int firstIndex, int secondIndex) {
        Node first = list.get(firstIndex);
        for (int i = firstIndex+1; i<=secondIndex; i++)
            list.set(i-1, list.get(i));
        list.set(secondIndex, first);
//        list.set(firstIndex, list.get(secondIndex));
//        list.set(secondIndex, first);
    }

    public class Node implements Serializable {
        int id;
        Vertex vertex;
        List<Node> parents;
        List<Node> children;

        double rating;

        private Node(Vertex vertex) {
            this.id = vertex.id;
            this.vertex = vertex;
            parents = new ArrayList<>();
            children = new ArrayList<>();
        }

        public String getName() {
            return vertex.getName();
        }

        public double getWeight() {
            return vertex.getWeight();
        }

        public int getId() {
            return id;
        }

        public List<Node> getParents() {
            return parents;
        }

        public List<Integer> getParentsIds() {
            return parents.stream().map(Node::getId).collect(Collectors.toList());
        }

        public List<Node> getChildren() {
            return children;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node{id=").append(id)
                    .append(", rating=").append(rating)
                    .append(", parents=[");
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

            return true;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
