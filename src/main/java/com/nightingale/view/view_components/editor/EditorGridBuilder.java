package com.nightingale.view.view_components.editor;

import com.nightingale.view.utils.GridUtils;
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

        RowConstraints statusRow = GridUtils.buildRowConstraintsWithConstantHeight(STATUS_HEADER_HEIGHT);
        RowConstraints contentRow = GridUtils.buildBindedRowConstraints(editorGrid.heightProperty());
        RowConstraints toolRow = GridUtils.buildRowConstraintsWithConstantHeight(TOOL_HEIGHT * 3 / 2);

        ColumnConstraints column = GridUtils.buildBindedColumnConstraints(editorGrid.widthProperty());

        editorGrid.getRowConstraints().setAll(statusRow, contentRow, toolRow);
        editorGrid.getColumnConstraints().setAll(column);

        return editorGrid;
    }
}
