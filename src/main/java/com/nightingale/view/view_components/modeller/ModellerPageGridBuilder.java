package com.nightingale.view.view_components.modeller;

import com.nightingale.view.view_components.common.ConstantSizeSetter;
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

        ColumnConstraints firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(50);
        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(50);

        RowConstraints contentRow = new RowConstraints();
        contentRow.prefHeightProperty().bind(modellerGridPane.heightProperty());

        RowConstraints toolRow = new RowConstraints();
        ConstantSizeSetter.setConstantHeight(toolRow, TOOL_HEIGHT * 3 / 2);

        modellerGridPane.getColumnConstraints().addAll(firstColumn, secondColumn);
        modellerGridPane.getRowConstraints().addAll(contentRow, toolRow);


        modellerGridPane.setHgap(10);
        modellerGridPane.setVgap(10);

        return modellerGridPane;
    }


}
