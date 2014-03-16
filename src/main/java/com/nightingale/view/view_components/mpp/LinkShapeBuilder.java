package com.nightingale.view.view_components.mpp;

import com.nightingale.view.config.Config;
import com.nightingale.model.mpp.elements.ProcessorLinkModel;
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

    public static Group build(final ProcessorLinkModel linkVO, Node firstProcessorNode, Node secondProcessorNode) {
        final Line line = new Line(linkVO.getTranslateX1(), linkVO.getTranslateY1(), linkVO.getTranslateX2(), linkVO.getTranslateY2());
        Group view = new Group();
        view.setId(String.valueOf(linkVO.getId()));
        final Text name = new Text(average(line.getStartX(), line.getEndX()), average(line.getStartY(), line.getEndY()), linkVO.getName());
        name.setStyle("-fx-opacity: 0.5");

        bindLinkName(line, name);
        bindLineEndsToVO(line, linkVO);
        bindLineEndsToNodes(line, firstProcessorNode, secondProcessorNode);

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

    private static void bindLineEndsToVO(Line line, final ProcessorLinkModel linkVO) {
        line.startXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                linkVO.setTranslateX1(newValue.doubleValue());

            }
        });

        line.startYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                linkVO.setTranslateY1(newValue.doubleValue());

            }
        });


        line.endXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                linkVO.setTranslateX2(newValue.doubleValue());

            }
        });

        line.endYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                linkVO.setTranslateY2(newValue.doubleValue());

            }
        });
    }

    private static double average(double first, double second) {
        return Math.min(first, second) + Math.abs(first - second) / 2;

    }
}
