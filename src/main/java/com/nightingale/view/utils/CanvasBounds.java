package com.nightingale.view.utils;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 12.03.14.
 */
public class CanvasBounds {
    public static Point2D getInBoundsCoordinate(double x, double y, Node node, Pane container) {
        double newX = toBounds(x, 0, container.widthProperty().get() - node.getLayoutBounds().getWidth());
        double newY = toBounds(y, 0, container.heightProperty().get() - node.getLayoutBounds().getHeight());
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
