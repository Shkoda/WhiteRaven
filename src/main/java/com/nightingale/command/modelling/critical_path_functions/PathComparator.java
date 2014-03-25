package com.nightingale.command.modelling.critical_path_functions;

import com.nightingale.model.entities.AcyclicDirectedGraph;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Nightingale on 23.03.2014.
 */
public class PathComparator implements Comparator<List<AcyclicDirectedGraph.Node>>{
    public static final Function<List<AcyclicDirectedGraph.Node>,Number> VERTEX_NUMBER_FUNCTION = new VertexNumberFunction();
    public static final Function<List<AcyclicDirectedGraph.Node>, Number> VERTEX_WEIGHT_FUNCTION = new VertexWeightFunction();

    private Function<List<AcyclicDirectedGraph.Node>, Number> pathRateFunction;

    public PathComparator(Function<List<AcyclicDirectedGraph.Node>, Number> pathRateFunction) {
        this.pathRateFunction = pathRateFunction;
    }

    @Override
    public int compare(List<AcyclicDirectedGraph.Node> o1, List<AcyclicDirectedGraph.Node> o2) {
        return compare(pathRateFunction.apply(o1), pathRateFunction.apply(o2));
    }

    private int compare(Number n1, Number n2){
        return Double.valueOf(n1.doubleValue()).compareTo(n2.doubleValue());
    }


    public static class VertexNumberFunction implements Function<List<AcyclicDirectedGraph.Node>, Number> {
        @Override
        public Integer apply(List<AcyclicDirectedGraph.Node> nodes) {
            return nodes == null ? 0 : nodes.size();

        }
    }

    public static class VertexWeightFunction implements Function<List<AcyclicDirectedGraph.Node>, Number> {
        @Override
        public Double apply(List<AcyclicDirectedGraph.Node> nodes) {
            return nodes == null || nodes.isEmpty() ?
                    0 :
                    nodes.parallelStream().mapToDouble(AcyclicDirectedGraph.Node::getWeight).sum();
        }
    }
}
