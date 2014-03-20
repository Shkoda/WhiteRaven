package com.nightingale.view.view_components.modeller;

import com.nightingale.view.view_components.common.ConstantSizeSetter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static com.nightingale.view.config.Config.TOOL_HEIGHT;

/**
 * Created by Nightingale on 19.03.14.
 */
public class ModellerQueueGridBuilder {
    public static GridPane build(){
        GridPane queueGridPane = new GridPane();
        queueGridPane.setGridLinesVisible(true);

        ColumnConstraints column = new ColumnConstraints();
        column.prefWidthProperty().bind(queueGridPane.widthProperty());

        RowConstraints contentRow = new RowConstraints();
        contentRow.prefHeightProperty().bind(queueGridPane.heightProperty());

        RowConstraints toolRow = new RowConstraints();
        ConstantSizeSetter.setConstantHeight(toolRow, TOOL_HEIGHT * 3 / 2);

        queueGridPane.getColumnConstraints().addAll(column);
        queueGridPane.getRowConstraints().addAll(contentRow, toolRow);

        queueGridPane.setHgap(10);
        queueGridPane.setVgap(10);

        return queueGridPane;
    }
}
