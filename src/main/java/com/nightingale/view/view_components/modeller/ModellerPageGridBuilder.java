package com.nightingale.view.view_components.modeller;

import com.nightingale.view.utils.GridUtils;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static com.nightingale.view.config.Config.TOOL_HEIGHT;

/**
 * Created by Nightingale on 19.03.14.
 */
public class ModellerPageGridBuilder {
    public static GridPane build(){
        GridPane modellerGridPane = new GridPane();
        modellerGridPane.setGridLinesVisible(true);

        ColumnConstraints firstColumn = GridUtils.buildColumnConstraintsWithPercentage(50);
        ColumnConstraints secondColumn =GridUtils.buildColumnConstraintsWithPercentage(50);

        RowConstraints contentRow =GridUtils.buildBindedRowConstraints(modellerGridPane.heightProperty());
        RowConstraints toolRow = GridUtils.buildRowConstraintsWithConstantHeight(TOOL_HEIGHT * 3 / 2);

        modellerGridPane.getColumnConstraints().addAll(firstColumn, secondColumn);
        modellerGridPane.getRowConstraints().addAll(contentRow, toolRow);

        modellerGridPane.setHgap(10);
        modellerGridPane.setVgap(10);

        return modellerGridPane;
    }


}
