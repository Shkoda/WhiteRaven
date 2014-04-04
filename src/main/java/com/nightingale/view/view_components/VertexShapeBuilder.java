package com.nightingale.view.view_components;

import com.nightingale.Main;
import com.nightingale.model.entities.graph.GraphType;
import com.nightingale.model.entities.graph.Vertex;
import com.nightingale.utils.Tuple;
import com.nightingale.view.config.Config;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Nightingale on 10.03.14.
 */
public class VertexShapeBuilder {

    public final static ImagePattern PROCESSOR_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/processor.png")));
    public final static ImagePattern TASK_IMAGE = new ImagePattern(new Image(Main.class.getResourceAsStream("/image/elements/task_empty.png")));

    private static final List<Point2D> PROCESSOR_CONNECTION_POINTS, TASK_CONNECTION_POINTS;


    public static Group build(final Vertex vertex, GraphType graphType) {

        double width, height;
        ImagePattern imagePattern;

        switch (graphType) {
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


        view.setId(String.valueOf(vertex.getId()));
        view.translateXProperty().addListener((observableValue, oldValue, newValue) -> vertex.setTranslateX(newValue.doubleValue()));
        view.translateYProperty().addListener((observableValue, oldValue, newValue) -> vertex.setTranslateY(newValue.doubleValue()));

        if (graphType == GraphType.TASK) {
            Text weightText = new Text(String.valueOf((int)vertex.getWeight()));
            weightText.setX(15);
            weightText.setY(25);
            vertex.addObserver((o, arg) -> weightText.setText(String.valueOf((int)(double)arg)));
            view.getChildren().add(weightText);
        }

        view.setOnMouseEntered(me -> ((Node) me.getSource()).setCursor(Cursor.HAND));
        view.setOnMouseExited(me -> ((Node) me.getSource()).setCursor(Cursor.DEFAULT));


        return view;
    }

    public static Tuple<Point2D, Point2D> getConnectionPoints(GraphType shapeType, Node firstNode, Node secondNode) {
        List<Point2D> fistShifted;
        List<Point2D> secondShifted;
        switch (shapeType) {
            case MPP:
                fistShifted = shiftConnectionPoints(PROCESSOR_CONNECTION_POINTS, firstNode.getTranslateX(), firstNode.getTranslateY());
                secondShifted = shiftConnectionPoints(PROCESSOR_CONNECTION_POINTS, secondNode.getTranslateX(), secondNode.getTranslateY());
                break;
            case TASK:
                fistShifted = shiftConnectionPoints(TASK_CONNECTION_POINTS, firstNode.getTranslateX(), firstNode.getTranslateY());
                secondShifted = shiftConnectionPoints(TASK_CONNECTION_POINTS, secondNode.getTranslateX(), secondNode.getTranslateY());
                break;
            default:
                return null;
        }
        return getClosest(fistShifted, secondShifted);
    }

    private static Tuple<Point2D, Point2D> getClosest(List<Point2D> firsts, List<Point2D> seconds) {
        Tuple<Point2D, Point2D> closest = new Tuple<>(firsts.get(0), seconds.get(0));
        double minDistance = squareDistance(closest._1, closest._2);
        for (Point2D first : firsts)
            for (Point2D second : seconds) {
                double distance = squareDistance(first, second);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = new Tuple<>(first, second);
                }
            }
        return closest;
    }

    private static double squareDistance(Point2D first, Point2D second) {
        return (first.getX() - second.getX()) * (first.getX() - second.getX()) + (first.getY() - second.getY()) * (first.getY() - second.getY());
    }

    private static List<Point2D> shiftConnectionPoints(List<Point2D> original, double nodeX, double nodeY) {
        List<Point2D> shifted = new ArrayList<>();
        for (Point2D originalPoint : original)
            shifted.add(new Point2D(originalPoint.getX() + nodeX, originalPoint.getY() + nodeY));
        return shifted;
    }

    public static Point2D getCentralPoint(Node processorNode) {
        double centerX = processorNode.getTranslateX() + Config.PROCESSOR_ELEMENT_WIDTH / 2;
        double centerY = processorNode.getTranslateY() + Config.PROCESSOR_ELEMENT_HEIGHT / 2;
        return new Point2D(centerX, centerY);
    }

    public static Point2D getInBoundsCoordinate(double x, double y, Node shape, Pane container) {
        double newX = toBounds(x, 0, container.widthProperty().get() - shape.getLayoutBounds().getWidth());
        double newY = toBounds(y, 0, container.heightProperty().get() - shape.getLayoutBounds().getHeight());
        return new Point2D(newX, newY);
    }


    private static double toBounds(double coordinate, double minValue, double maxValue) {
        if (coordinate < minValue)
            return minValue;
        if (coordinate > maxValue)
            return maxValue;
        return coordinate;

    }

    static {
        PROCESSOR_CONNECTION_POINTS = new ArrayList<>();
        double stepX = ((double) Config.PROCESSOR_ELEMENT_WIDTH) / Config.PROCESSOR_CONNECTION_POINTS_PER_SIDE;
        double stepY = ((double) Config.PROCESSOR_ELEMENT_HEIGHT) / Config.PROCESSOR_CONNECTION_POINTS_PER_SIDE;
        for (int i = 0; i < 4; i++) {
            PROCESSOR_CONNECTION_POINTS.add(new Point2D(stepX * i, 0));
            PROCESSOR_CONNECTION_POINTS.add(new Point2D(stepX * i, Config.PROCESSOR_ELEMENT_HEIGHT));
            PROCESSOR_CONNECTION_POINTS.add(new Point2D(0, stepY * i));
            PROCESSOR_CONNECTION_POINTS.add(new Point2D(Config.PROCESSOR_ELEMENT_WIDTH, stepY * i));
        }


        TASK_CONNECTION_POINTS = new ArrayList<>();
        double degrees = 360.0 / Config.TASK_CONNECTION_POINTS_NUMBER;
        double radius = Config.TASK_ELEMENT_HEIGHT / 2;
        for (int i = 0; i < Config.TASK_CONNECTION_POINTS_NUMBER; i++) {
            TASK_CONNECTION_POINTS.add(new Point2D(radius * Math.sin(degrees * i) + radius, radius * Math.cos(degrees * i) + radius));
        }

    }
}
