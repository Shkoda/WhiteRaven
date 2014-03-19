package com.nightingale.view.view_components;

import com.nightingale.model.entities.Connection;
import com.nightingale.model.entities.GraphType;
import com.nightingale.utils.Tuple;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

import static com.nightingale.view.utils.Math.*;

/**
 * Created by Nightingale on 13.03.14.
 */
public class LinkShapeBuilder {

    private static final List<Double> ARROW_POINTS = Arrays.asList(
            0.0, 5.0,
            -5.0, -5.0,
            5.0, -5.0
    );

    public static Group build(final Connection connection, Node firstNode, Node secondNode, GraphType graphType) {
        Group view = new Group();
        view.setId(String.valueOf(connection.getId()));

        Tuple<Point2D, Point2D> ends = VertexShapeBuilder.getConnectionPoints(graphType, firstNode, secondNode);
        final Line line = new Line(ends._1.getX(), ends._1.getY(), ends._2.getX(), ends._2.getY());

        final Text name = new Text(middleCoordinate(line.getStartX(), line.getEndX()), middleCoordinate(line.getStartY(), line.getEndY()), connection.getName());
        name.setStyle("-fx-opacity: 0.5");

        view.getChildren().addAll(line, name);

        Polygon arrow = null;
        if (graphType == GraphType.TASK) {
            arrow = new Polygon();
            arrow.getPoints().addAll(ARROW_POINTS);

            setArrowRotation(arrow, line);
            setArrowPosition(arrow, line);

            view.getChildren().add(arrow);
        }

        bindLinkName(line, name);
        bindLineEndsToModel(line, connection);
        bindLineEndsToNodes(line, arrow, firstNode, secondNode);

        return view;
    }

    private static void bindLinkName(final Line line, final Text name) {
        line.startXProperty().addListener((observableValue, oldValue, newValue) -> {
            name.setX(middleCoordinate(newValue.doubleValue(), line.getEndX()));
        });
        line.startYProperty().addListener((observableValue, oldValue, newValue) -> {
            name.setY(middleCoordinate(newValue.doubleValue(), line.getEndY()));
        });
        line.endXProperty().addListener((observableValue, oldValue, newValue) -> {
            name.setX(middleCoordinate(newValue.doubleValue(), line.getStartX()));
        });
        line.endYProperty().addListener((observableValue, oldValue, newValue) -> {
            name.setY(middleCoordinate(newValue.doubleValue(), line.getStartY()));
        });
    }

    private static void bindLineEndsToNodes(final Line line, final Polygon arrow, final Node firstProcessorNode, final Node secondProcessorNode) {
        firstProcessorNode.translateXProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                });
        firstProcessorNode.translateYProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                });
        secondProcessorNode.translateXProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                });
        secondProcessorNode.translateYProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                });
    }

    private static void bindLineEndsToModel(Line line, final Connection connection) {
        line.startXProperty().addListener((observableValue, oldValue, newValue) -> {
            connection.setTranslateX1(newValue.doubleValue());
        });
        line.startYProperty().addListener((observableValue, oldValue, newValue) -> {
            connection.setTranslateY1(newValue.doubleValue());
        });
        line.endXProperty().addListener((observableValue, oldValue, newValue) -> {
            connection.setTranslateX2(newValue.doubleValue());
        });
        line.endYProperty().addListener((observableValue, oldValue, newValue) -> {
            connection.setTranslateY2(newValue.doubleValue());
        });
    }


    private static void resetLineAndArrowPosition(final Line line, final Polygon arrow, final Node firstProcessorNode, final Node secondProcessorNode) {
        Tuple<Point2D, Point2D> ends = VertexShapeBuilder.getConnectionPoints(GraphType.TASK, firstProcessorNode, secondProcessorNode);
        setLineEnds(line, ends);
        setArrowPosition(arrow, line);
        setArrowRotation(arrow, line);
    }


    private static void setLineEnds(Line line, Tuple<Point2D, Point2D> ends) {
        line.setStartX(ends._1.getX());
        line.setStartY(ends._1.getY());
        line.setEndX(ends._2.getX());
        line.setEndY(ends._2.getY());
    }

    private static void setArrowRotation(Polygon arrow, Line line) {
        if (arrow == null)
            return;
        arrow.setRotate(lineArrowRotation(line));
    }

    private static void setArrowPosition(Polygon arrow, Line line) {
        if (arrow == null)
            return;
        arrow.setTranslateX(line.getEndX());
        arrow.setTranslateY(line.getEndY());
    }

}
