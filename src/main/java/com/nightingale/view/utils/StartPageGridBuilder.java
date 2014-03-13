package com.nightingale.view.utils;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Created by Nightingale on 09.03.14.
 */
public class StartPageGridBuilder {
    public static GridPane get() {
        GridPane gridForLinks = new GridPane();
        ColumnConstraints firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(50);
        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(50);

        RowConstraints firstRow = new RowConstraints();
        firstRow.setPercentHeight(50);
        RowConstraints secondRow = new RowConstraints();
        secondRow.setPercentHeight(50);

        gridForLinks.getColumnConstraints().addAll(firstColumn, secondColumn);
        gridForLinks.getRowConstraints().addAll(firstRow, secondRow);

//        gridForLinks.setStyle("-fx-background-color: #daf4cd;-fx-border-color: #b03c5d; ");
//        gridForLinks.setGridLinesVisible(true);

        gridForLinks.setHgap(30);
        gridForLinks.setVgap(30);

        return gridForLinks;
    }
}
