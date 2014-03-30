package com.nightingale.view.view_components.startpage;

import com.nightingale.view.utils.GridUtils;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Created by Nightingale on 09.03.14.
 */
public class StartPageGridBuilder {
    public static GridPane build() {
        GridPane gridForLinks = new GridPane();
        ColumnConstraints firstColumn = GridUtils.buildColumnConstraintsWithPercentage(50);
        ColumnConstraints secondColumn = GridUtils.buildColumnConstraintsWithPercentage(50);

        RowConstraints firstRow = GridUtils.buildRowConstraintsWithPercentage(50);
        RowConstraints secondRow = GridUtils.buildRowConstraintsWithPercentage(50);

        gridForLinks.getColumnConstraints().addAll(firstColumn, secondColumn);
        gridForLinks.getRowConstraints().addAll(firstRow, secondRow);

        gridForLinks.setHgap(30);
        gridForLinks.setVgap(30);

        return gridForLinks;
    }
}
