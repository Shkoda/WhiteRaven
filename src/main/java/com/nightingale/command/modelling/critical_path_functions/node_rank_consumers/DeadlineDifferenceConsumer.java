package com.nightingale.command.modelling.critical_path_functions.node_rank_consumers;

import com.nightingale.command.modelling.critical_path_functions.CriticalPath;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.command.modelling.critical_path_functions.PathComparator;
import com.nightingale.utils.Loggers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Nightingale on 23.03.2014.
 */
public class DeadlineDifferenceConsumer implements Consumer<AcyclicDirectedGraph> {

    @Override
    public void accept(AcyclicDirectedGraph acyclicDirectedGraph) {
        Function<List<AcyclicDirectedGraph.Node>, Number> vertexWeightFunction = PathComparator.VERTEX_WEIGHT_FUNCTION;

        double graphCriticalPathWeight = vertexWeightFunction
                .apply(CriticalPath.find(acyclicDirectedGraph, vertexWeightFunction))
                .doubleValue();

        Loggers.debugLogger.debug("critical path weight: " + graphCriticalPathWeight);
        acyclicDirectedGraph
                .getNodes()
                .parallelStream()
                .forEach(node -> consumeNode(node, graphCriticalPathWeight, vertexWeightFunction));
    }

    private void consumeNode(AcyclicDirectedGraph.Node node, double criticalPathWeight, Function<List<AcyclicDirectedGraph.Node>, Number> vertexWeightFunction) {
        double weightUp = vertexWeightFunction
                .apply(CriticalPath.find(node, vertexWeightFunction, CriticalPath.Direction.UP))
                .doubleValue();

        double weightDown = vertexWeightFunction
                .apply(CriticalPath.find(node, vertexWeightFunction, CriticalPath.Direction.DOWN))
                .doubleValue()+ node.getWeight();

        node.setRating(criticalPathWeight - weightDown - weightUp);//+weightUp
        Loggers.debugLogger.debug("------------>>>>>>>  node #" + node.getId() + " up=" + weightUp + " down=" + weightDown + " rating=" + node.getRating());
    }
}
