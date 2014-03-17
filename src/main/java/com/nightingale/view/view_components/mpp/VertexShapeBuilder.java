package com.nightingale.view.view_components.mpp;

import com.nightingale.Main;
import com.nightingale.model.entities.GraphType;
import com.nightingale.model.entities.Vertex;
import com.nightingale.view.config.Config;
import com.nightingale.model.mpp.ProcessorModel;
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
public class VertexShapeBuilder {

    public final static ImagePattern PROCESSOR_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor.png")));
    public final static ImagePattern TASK_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/task.png")));


    public static Group build(final Vertex vertex, GraphType graphType) {

        double width, height;
        ImagePattern imagePattern;

        switch (graphType){
            case MPP:
                width = Config.PROCESSOR_ELEMENT_WIDTH;
                height = Config.PROCESSOR_ELEMENT_HEIGHT;
                imagePattern = PROCESSOR_IMAGE;
                break;
            case TASK:
                width = Config.TASK_ELEMENT_WIDTH;
                height = Config.TASK_ELEMENT_HEIGHT;
                imagePattern = TASK_IMAGE;
                break;
            default:
                return null;
        }

        final Shape rectangle = new Rectangle(0, 0, width, height);
        rectangle.setFill(imagePattern);

        final Group view = new Group();

        view.setTranslateX(vertex.getTranslateX());
        view.setTranslateY(vertex.getTranslateY());

        view.getChildren().addAll(rectangle, new Text(width - 5, height + 10, vertex.getName()));

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

        view.setId(String.valueOf(vertex.getId()));

        view.translateXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                vertex.setTranslateX(newValue.doubleValue());
            }
        });

        view.translateYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                vertex.setTranslateY(newValue.doubleValue());
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
