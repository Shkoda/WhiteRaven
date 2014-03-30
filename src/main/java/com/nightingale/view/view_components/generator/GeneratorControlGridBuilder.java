package com.nightingale.view.view_components.generator;

import com.nightingale.view.utils.GridPosition;
import com.nightingale.view.utils.GridUtils;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Created by Nightingale on 24.03.2014.
 */
public class GeneratorControlGridBuilder {

    public static final GridPosition MIN_TASK_WEIGHT_LABEL = new GridPosition(0, 0);
    public static final GridPosition MAX_TASK_WEIGHT_LABEL = new GridPosition(0, 1);
    public static final GridPosition TASK_NUMBER_LABEL = new GridPosition(0, 2);
    public static final GridPosition CONNECTIVITY_LABEL = new GridPosition(0, 3);

    public static final GridPosition MIN_TASK_WEIGHT_FIELD = new GridPosition(1, 0);
    public static final GridPosition MAX_TASK_WEIGHT_FIELD = new GridPosition(1, 1);
    public static final GridPosition TASK_NUMBER_FIELD = new GridPosition(1, 2);
    public static final GridPosition CONNECTIVITY_FIELD = new GridPosition(1, 3);

    public static GridPane build() {
        GridPane gridPane = new GridPane();

        ColumnConstraints firstColumn = GridUtils.buildColumnConstraintsWithPercentage(75);
        ColumnConstraints secondColumn = GridUtils.buildColumnConstraintsWithPercentage(25);

        gridPane.getColumnConstraints().addAll(firstColumn, secondColumn);

        for (int i = 0; i < 4; i++)
            gridPane.getRowConstraints().add(GridUtils.buildRowConstraintsWithPercentage(25));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }
}
