package com.nightingale.view.view_components.mpp;

import com.nightingale.model.entities.Connection;
import com.nightingale.model.entities.GraphType;
import com.nightingale.view.config.Config;
import com.nightingale.model.mpp.ProcessorLinkModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 13.03.14.
 */
public class LinkShapeBuilder {

    public static Group build(final Connection connection, Node firstNode, Node secondNode, GraphType graphType) {
        final Line line = new Line(connection.getTranslateX1(), connection.getTranslateY1(), connection.getTranslateX2(), connection.getTranslateY2());
        Group view = new Group();
        view.setId(String.valueOf(connection.getId()));
        final Text name = new Text(average(line.getStartX(), line.getEndX()), average(line.getStartY(), line.getEndY()), connection.getName());
        name.setStyle("-fx-opacity: 0.5");

        bindLinkName(line, name);
        bindLineEndsToVO(line, connection);
        bindLineEndsToNodes(line, firstNode, secondNode);

        view.getChildren().addAll(line, name);
        return view;
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


    private static void bindLineEndsToNodes(final Line line, Node firstProcessorNode, Node secondProcessorNode) {
        firstProcessorNode.translateXProperty()
                .addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            line.setStartX(newValue.doubleValue() + Config.PROCESSOR_ELEMENT_WIDTH / 2);}
        });

        firstProcessorNode.translateYProperty()
                .addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            line.setStartY(newValue.doubleValue() + Config.PROCESSOR_ELEMENT_HEIGHT / 2);}
        });

        secondProcessorNode.translateXProperty()
                .addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            line.setEndX(newValue.doubleValue() + Config.PROCESSOR_ELEMENT_WIDTH / 2);}
        });

        secondProcessorNode.translateYProperty()
                .addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            line.setEndY(newValue.doubleValue() + Config.PROCESSOR_ELEMENT_HEIGHT / 2);}
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
