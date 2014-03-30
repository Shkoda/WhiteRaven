package com.nightingale.view.view_components.modeller;

import com.nightingale.view.utils.GridUtils;
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

        ColumnConstraints column = GridUtils.buildBindedColumnConstraints(queueGridPane.widthProperty());

        RowConstraints contentRow = GridUtils.buildBindedRowConstraints(queueGridPane.heightProperty());
        RowConstraints toolRow = GridUtils.buildRowConstraintsWithConstantHeight(TOOL_HEIGHT * 3 / 2);

        queueGridPane.getColumnConstraints().addAll(column);
        queueGridPane.getRowConstraints().addAll(contentRow, toolRow);

        queueGridPane.setHgap(10);
        queueGridPane.setVgap(10);

        return queueGridPane;
    }
}
