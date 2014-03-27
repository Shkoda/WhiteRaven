package com.nightingale.command.schedule;

import com.nightingale.model.entities.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 27.03.2014.
 */
public class Paths {
    private Collection<ProcessorModel> vertexes;
    private Collection<ProcessorLinkModel> links;
    /**
     * shortest paths between mpp processors
     */
    private Set<Path> paths;

    public Paths(Graph<ProcessorModel, ProcessorLinkModel> mpp) {
        vertexes = mpp.getVertexes();
        links = mpp.getConnections();
        countPaths(mpp);
    }

    public Path getPath(ProcessorModel src, ProcessorModel dst) {
        for (Path path : paths)
            if (path.src.equals(src) && path.dst.equals(dst))
                return path;
        return null;
    }

    private void countPaths(Graph<ProcessorModel, ProcessorLinkModel> mpp) {
        paths = new HashSet<>();
        DijkstraAlgorithm<ProcessorModel, ProcessorLinkModel> dijkstra = new DijkstraAlgorithm<>(mpp);

        vertexes.stream().forEach(src -> {
            dijkstra.execute(src);
            paths.addAll(vertexes.stream()
                    .map(dst -> new Path(dijkstra.getPath(dst)))
                    .collect(Collectors.toList()));
        });
    }

    private ProcessorLinkModel getConnection(int firstId, int secondId) {
        for (ProcessorLinkModel linkModel : links)
            if ((linkModel.getFirstVertexId() == firstId && linkModel.getSecondVertexId() == secondId) ||
                    (linkModel.getFirstVertexId() == secondId && linkModel.getSecondVertexId() == firstId))
                return linkModel;
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        paths.stream().forEach(path -> builder.append(path).append("\n"));
        return builder.toString();
    }


    public class Path {
        public final ProcessorModel src, dst;
        public final List<ProcessorModel> queue;
        public final Map<Integer, ProcessorLinkModel> links;
        public final int length;

        public Path(List<ProcessorModel> queue) {
            this.queue = queue;
            src = queue.get(0);
            dst = queue.get(queue.size() - 1);
            links = new HashMap<>();
            for (int i = 0; i < queue.size() - 1; i++) {
                int srcId = queue.get(i).getId();
                int dstId = queue.get(i + 1).getId();
                links.put(srcId, getConnection(srcId, dstId));
            }
            length = links.size();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return !(dst != null ? !dst.equals(path.dst)
                    : path.dst != null) && !(src != null ? !src.equals(path.src) : path.src != null);
        }

        @Override
        public int hashCode() {
            int result = src != null ? src.hashCode() : 0;
            result = 31 * result + (dst != null ? dst.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < queue.size(); i++) {
                ProcessorModel src = queue.get(i);
                builder.append(src.getName());
                if (i < queue.size() - 1)
                    builder.append(" --").append(links.get(src.getId()).getWeight()).append("--> ");
            }
            return builder.toString();
        }
    }
}












































