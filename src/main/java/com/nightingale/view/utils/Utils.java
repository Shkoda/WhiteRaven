package com.nightingale.view.utils;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

/**
 * Created by Nightingale on 20.02.14.
 */
public class Utils {

    public static void setConstantHeight(RowConstraints rowConstraints, int height){
        rowConstraints.setMinHeight(height);
        rowConstraints.setPrefHeight(height);
        rowConstraints.setMaxHeight(height);
    }

    public static void setConstantWidth(ColumnConstraints columnConstraints, int size) {
        columnConstraints.setMaxWidth(size);
        columnConstraints.setMinWidth(size);
        columnConstraints.setPrefWidth(size);
    }

}
