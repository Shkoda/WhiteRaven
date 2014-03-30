package com.nightingale.view.utils;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

/**
 * Created by Nightingale on 30.03.2014.
 */
public class GridUtils {

    public static RowConstraints buildRowConstraintsWithPercentage(double percents){
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(percents);
        return rowConstraints;
    }

    public static ColumnConstraints buildColumnConstraintsWithPercentage(double percents){
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(percents);
        return columnConstraints;
    }

    public static RowConstraints buildBindedRowConstraints(javafx.beans.property.ReadOnlyDoubleProperty heightProperty){
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.prefHeightProperty().bind(heightProperty);
        return rowConstraints;
    }

    public static ColumnConstraints buildBindedColumnConstraints(javafx.beans.property.ReadOnlyDoubleProperty widthProperty){
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.prefWidthProperty().bind(widthProperty);
        return columnConstraints;
    }

    public static RowConstraints buildRowConstraintsWithConstantHeight(double height){
        return setConstantRowHeight(new RowConstraints(), height);
    }

    public static ColumnConstraints buildColumnConstraintsWithConstantWidth(double width){
        return setConstantColumnWidth(new ColumnConstraints(), width);
    }

    public static RowConstraints setConstantRowHeight(RowConstraints rowConstraints, double height){
        rowConstraints.setMinHeight(height);
        rowConstraints.setPrefHeight(height);
        rowConstraints.setMaxHeight(height);
        return rowConstraints;
    }

    public static ColumnConstraints setConstantColumnWidth(ColumnConstraints columnConstraints, double size) {
        columnConstraints.setMaxWidth(size);
        columnConstraints.setMinWidth(size);
        columnConstraints.setPrefWidth(size);
        return columnConstraints;
    }


}
