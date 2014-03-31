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

public class NodesAfterCurrentConsumer implements Consumer<AcyclicDirectedGraph> {
    @Override
    public void accept(AcyclicDirectedGraph acyclicDirectedGraph) {
        Function<List<AcyclicDirectedGraph.Node>, Number> vertexNumberFunction = PathComparator.VERTEX_NUMBER_FUNCTION;

        acyclicDirectedGraph
                .getNodes()
                .parallelStream()
                .forEach(node -> consumeNode(node,  vertexNumberFunction));
    }

    private void consumeNode(AcyclicDirectedGraph.Node node,  Function<List<AcyclicDirectedGraph.Node>, Number> vertexNumberFunction) {
        double vertexNumberDown = vertexNumberFunction
                .apply(CriticalPath.find(node, vertexNumberFunction, CriticalPath.Direction.DOWN))
                .doubleValue();
        node.setRating(vertexNumberDown );
        Loggers.debugLogger.debug("node #" + node.getId() + " down=" + vertexNumberDown + " rating=" + node.getRating());
    }
}
