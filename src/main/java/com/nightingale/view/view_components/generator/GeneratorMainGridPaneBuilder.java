package com.nightingale.view.view_components.generator;

import com.nightingale.view.utils.GridPosition;
import com.nightingale.view.view_components.common.ConstantSizeSetter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static com.nightingale.view.config.Config.TOOL_HEIGHT;

/**
 * Created by Nightingale on 24.03.2014.
 */
public class GeneratorMainGridPaneBuilder {
    public static final GridPosition CONTENT_POSITION = new GridPosition(0, 0);
    public static final GridPosition BUTTON_POSITION = new GridPosition(0, 1);

    public static GridPane build() {
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent");

        ColumnConstraints column = new ColumnConstraints();
        column.prefWidthProperty().bind(gridPane.widthProperty());


        RowConstraints contentRow = new RowConstraints();
        contentRow.prefHeightProperty().bind(gridPane.heightProperty());

        RowConstraints toolRow = new RowConstraints();
        ConstantSizeSetter.setConstantHeight(toolRow, TOOL_HEIGHT * 3 / 2);

        gridPane.getColumnConstraints().addAll(column);
        gridPane.getRowConstraints().addAll(contentRow, toolRow);


        return gridPane;
    }
}
