package com.nightingale.view.view_components;

import com.nightingale.model.entities.Connection;
import com.nightingale.model.entities.GraphType;
import com.nightingale.utils.Tuple;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 13.03.14.
 */
public class LinkShapeBuilder {

    public static Group build(final Connection connection, Node firstNode, Node secondNode, GraphType graphType) {
        switch (graphType) {
            case MPP:
                return buildMppLink(connection, firstNode, secondNode);
            case TASK:
                return buildTaskLink(connection, firstNode, secondNode);
            default:
                return null;
        }

    }

    private static Group buildMppLink(final Connection connection, Node firstNode, Node secondNode) {
        final Line line = new Line(connection.getTranslateX1(), connection.getTranslateY1(), connection.getTranslateX2(), connection.getTranslateY2());
        Group view = new Group();
        view.setId(String.valueOf(connection.getId()));
        final Text name = new Text(average(line.getStartX(), line.getEndX()), average(line.getStartY(), line.getEndY()), connection.getName());
        name.setStyle("-fx-opacity: 0.5");



        bindLineEndsToNodes(line, null, firstNode, secondNode);
        bindLineEndsToVO(line, connection);
        bindLinkName(line, name);

        view.getChildren().addAll(line, name);
        return view;
    }

    private static Group buildTaskLink(final Connection connection, Node firstNode, Node secondNode) {
        Group view = new Group();
        view.setId(String.valueOf(connection.getId()));

        Tuple<Point2D, Point2D> ends = VertexShapeBuilder.getConnectionPoints(GraphType.TASK, firstNode, secondNode);
        final Line line = new Line(ends._1.getX(), ends._1.getY(), ends._2.getX(), ends._2.getY());

        final Text name = new Text(average(line.getStartX(), line.getEndX()), average(line.getStartY(), line.getEndY()), connection.getName());
        name.setStyle("-fx-opacity: 0.5");

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(
                0.0, 5.0,
                -5.0, -5.0,
                5.0, -5.0);

        setArrowRotation(arrow, line);
        setArrowPosition(arrow, line);


        bindLinkName(line, name);
        bindLineEndsToVO(line, connection);
        bindLineEndsToNodes(line, arrow, firstNode, secondNode);

        view.getChildren().addAll(line, arrow, name);
        return view;
    }

    private static void setArrowRotation(Polygon arrow, Line line) {
        if (arrow == null)
            return;
        double x1 = line.getStartX();
        double y1 = line.getStartY();
        double x2 = line.getEndX();
        double y2 = line.getEndY();

        double angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        arrow.setRotate((angle - 90));
    }

    private static void setArrowPosition(Polygon arrow, Line line) {
        if (arrow == null)
            return;
        arrow.setTranslateX(line.getEndX());
        arrow.setTranslateY(line.getEndY());
    }

    private static void bindLinkName(final Line line, final Text name) {
        line.startXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                name.setX(average(newValue.doubleValue(), line.getEndX()));
            }
        });

        line.startYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                name.setY(average(newValue.doubleValue(), line.getEndY()));
            }
        });

        line.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                name.setX(average(newValue.doubleValue(), line.getStartX()));
            }
        });

        line.endYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                name.setY(average(newValue.doubleValue(), line.getStartY()));
            }
        });
    }

    private static void setLineEnds(Line line, Tuple<Point2D, Point2D> ends) {
        line.setStartX(ends._1.getX());
        line.setStartY(ends._1.getY());
        line.setEndX(ends._2.getX());
        line.setEndY(ends._2.getY());
    }

    private static void resetLineAndArrowPosition(final Line line, final Polygon arrow, final Node firstProcessorNode, final Node secondProcessorNode){
        Tuple<Point2D, Point2D> ends = VertexShapeBuilder.getConnectionPoints(GraphType.TASK, firstProcessorNode, secondProcessorNode);
        setLineEnds(line, ends);
        setArrowPosition(arrow, line);
        setArrowRotation(arrow, line);
    }

    private static void bindLineEndsToNodes(final Line line, final Polygon arrow, final Node firstProcessorNode, final Node secondProcessorNode) {
        firstProcessorNode.translateXProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                      resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                    }
                });

        firstProcessorNode.translateYProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                    }
                });

        secondProcessorNode.translateXProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                    }
                });

        secondProcessorNode.translateYProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                        resetLineAndArrowPosition(line, arrow, firstProcessorNode, secondProcessorNode);
                    }
                });
    }

    private static void bindLineEndsToVO(Line line, final Connection connection) {
        line.startXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                connection.setTranslateX1(newValue.doubleValue());

            }
        });

        line.startYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                connection.setTranslateY1(newValue.doubleValue());

            }
        });


        line.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                connection.setTranslateX2(newValue.doubleValue());

            }
        });

        line.endYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                connection.setTranslateY2(newValue.doubleValue());
            }
        });
    }

    private static double average(double first, double second) {
        return Math.min(first, second) + Math.abs(first - second) / 2;

    }
}
