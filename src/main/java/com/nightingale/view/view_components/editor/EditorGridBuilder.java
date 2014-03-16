package com.nightingale.view.view_components.editor;

import com.nightingale.view.view_components.common.ConstantSizeSetter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static com.nightingale.view.config.Config.STATUS_HEADER_HEIGHT;
import static com.nightingale.view.config.Config.TOOL_HEIGHT;

/**
 * Created by Nightingale on 16.03.14.
 */
public class EditorGridBuilder {
    public static GridPane build() {
        GridPane editorGrid = new GridPane();

        RowConstraints statusRow = new RowConstraints();
        ConstantSizeSetter.setConstantHeight(statusRow, STATUS_HEADER_HEIGHT);

        RowConstraints contentRow = new RowConstraints();
        contentRow.prefHeightProperty().bind(editorGrid.heightProperty());

        RowConstraints toolRow = new RowConstraints();
        ConstantSizeSetter.setConstantHeight(toolRow, TOOL_HEIGHT * 3 / 2);

        editorGrid.getRowConstraints().setAll(statusRow, contentRow, toolRow);

        ColumnConstraints column = new ColumnConstraints();
        column.prefWidthProperty().bind(editorGrid.widthProperty());

        editorGrid.getColumnConstraints().setAll(column);

        return editorGrid;
    }
}
