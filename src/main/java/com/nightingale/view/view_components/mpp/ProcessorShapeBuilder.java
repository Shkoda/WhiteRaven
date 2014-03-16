package com.nightingale.view.view_components.mpp;

import com.nightingale.Main;
import com.nightingale.view.config.Config;
import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by Nightingale on 10.03.14.
 */
public class ProcessorShapeBuilder {

    public final static ImagePattern DEFAULT_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor.png")));
    public final static ImagePattern SELECTED_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor-selected.png")));
    public final static ImagePattern BAD_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor-bad.png")));


    public static Group build(final ProcessorModel processorModel) {
        final Shape rectangle = new Rectangle(0, 0, Config.PROCESSOR_ELEMENT_WIDTH, Config.PROCESSOR_ELEMENT_WIDTH);
        rectangle.setFill(DEFAULT_IMAGE);

        final Group view = new Group();

        view.setTranslateX(processorModel.getTranslateX());
        view.setTranslateY(processorModel.getTranslateY());

        view.getChildren().addAll(rectangle, new Text(Config.PROCESSOR_ELEMENT_WIDTH - 5, Config.PROCESSOR_ELEMENT_HEIGHT + 10, processorModel.getName()));

        view.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ((Node) me.getSource()).setCursor(Cursor.HAND);
            }
        });

        view.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                ((Node) me.getSource()).setCursor(Cursor.DEFAULT);
            }
        });

        view.setId(String.valueOf(processorModel.getId()));

        view.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                processorModel.setTranslateX(newValue.doubleValue());
            }
        });

        view.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                processorModel.setTranslateY(newValue.doubleValue());
            }
        });
        return view;
    }

    public static Point2D getCentralPoint(Node processorNode){
        double centerX = processorNode.getTranslateX() + Config.PROCESSOR_ELEMENT_WIDTH / 2;
        double centerY = processorNode.getTranslateY() + Config.PROCESSOR_ELEMENT_HEIGHT / 2;
        return new Point2D(centerX, centerY);
    }

    public static Point2D getInBoundsCoordinate(double x, double y, Node processorShape, Pane container) {
        double newX = toBounds(x, 0, container.widthProperty().get() - processorShape.getLayoutBounds().getWidth());
        double newY = toBounds(y, 0, container.heightProperty().get() - processorShape.getLayoutBounds().getHeight());
        return new Point2D(newX, newY);
    }


    private static double toBounds(double coordinate, double minValue, double maxValue) {
        if (coordinate < minValue)
            return minValue;
        if (coordinate > maxValue)
            return maxValue;
        return coordinate;

    }
}
