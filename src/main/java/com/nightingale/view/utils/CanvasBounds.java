package com.nightingale.view.utils;

import com.sun.javafx.animation.transition.Position2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 12.03.14.
 */
public class CanvasBounds {
    public static Position2D getInBoundsCoordinate(double x, double y, Node node, Pane container) {
        Position2D inBoundsPosition = new Position2D();
        inBoundsPosition.x = toBounds(x, 0, container.widthProperty().get() - node.getLayoutBounds().getWidth());
        inBoundsPosition.y = toBounds(y, 0, container.heightProperty().get() - node.getLayoutBounds().getHeight());
        return inBoundsPosition;
    }


    private static double toBounds(double coordinate, double minValue, double maxValue) {
        if (coordinate < minValue)
            return minValue;
        if (coordinate > maxValue)
            return maxValue;
        return coordinate;

    }


}
