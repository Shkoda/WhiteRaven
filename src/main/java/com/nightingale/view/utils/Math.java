package com.nightingale.view.utils;

import javafx.scene.shape.Line;

/**
 * Created by Nightingale on 19.03.14.
 */
public class Math {
    public static double middleCoordinate(double first, double second) {
        return java.lang.Math.min(first, second) + java.lang.Math.abs(first - second) / 2;
    }

    public static double lineArrowRotation(Line line){
        double x1 = line.getStartX();
        double y1 = line.getStartY();
        double x2 = line.getEndX();
        double y2 = line.getEndY();

      return  java.lang.Math.atan2(y2 - y1, x2 - x1) * 180 / java.lang.Math.PI - 90;
    }
}
