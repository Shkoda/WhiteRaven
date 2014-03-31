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
public class TimeBeforeCurrentConsumer implements Consumer<AcyclicDirectedGraph> {

    @Override
    public void accept(AcyclicDirectedGraph acyclicDirectedGraph) {
        Function<List<AcyclicDirectedGraph.Node>, Number> vertexWeightFunction = PathComparator.VERTEX_WEIGHT_FUNCTION;
        acyclicDirectedGraph
                .getNodes()
                .parallelStream()
                .forEach(node -> consumeNode(node,  vertexWeightFunction));
    }

    private void consumeNode(AcyclicDirectedGraph.Node node,  Function<List<AcyclicDirectedGraph.Node>, Number> vertexWeightFunction) {
        double timeUp = vertexWeightFunction
                .apply(CriticalPath.find(node, vertexWeightFunction, CriticalPath.Direction.UP))
                .doubleValue();
        node.setRating(timeUp );
        Loggers.debugLogger.debug("node #" + node.getId() + " timeUp=" + timeUp + " rating=" + node.getRating());
    }
}
