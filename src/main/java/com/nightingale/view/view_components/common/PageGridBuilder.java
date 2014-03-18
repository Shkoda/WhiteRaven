package com.nightingale.view.view_components.common;


import com.nightingale.view.config.Config;
import com.nightingale.view.utils.GridPosition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

import static com.nightingale.view.config.Config.*;

/**
 * Created by Nightingale on 17.02.14.
 */
public class PageGridBuilder {

    public static final GridPosition WORK_PANE_POSITION = new GridPosition(1, 1);
    public static final GridPosition BACK_BUTTON_POSITION = new GridPosition(0, 0);
    public static final GridPosition PREVIOUS_BUTTON_POSITION = new GridPosition(0, 1);
    public static final GridPosition NEXT_BUTTON_POSITION = new GridPosition(2, 1);
    public static final GridPosition SETTINGS_BUTTON_POSITION = new GridPosition(2, 0);
    public static final GridPosition NAME_POSITION = new GridPosition(1, 0);

    public static GridPane getTemplate(String name) {
        GridPane gridPane = new GridPane();
        setConstraints(gridPane);
        setAnchors(gridPane);
        setPageName(gridPane, name);

        return gridPane;
    }

    public static void clearCell(GridPane gridPane, GridPosition gridPosition) {
        List<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (node == null)
                continue;
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeColumn = GridPane.getColumnIndex(node);
            if (nodeRow != null && nodeColumn != null && nodeRow == gridPosition.rowNumber && nodeColumn == gridPosition.columnNumber) {
                gridPane.getChildren().remove(node);
                return;
            }
        }
    }


    private static void setConstraints(GridPane template) {
        ColumnConstraints leftColumn = new ColumnConstraints();
        ConstantSizeSetter.setConstantWidth(leftColumn, Config.SYSTEM_MENU_BUTTON_SIZE * 2);

        ColumnConstraints rightColumn = new ColumnConstraints();
        ConstantSizeSetter.setConstantWidth(rightColumn, SYSTEM_MENU_BUTTON_SIZE * 2);

        final ColumnConstraints contentColumn = new ColumnConstraints();
        contentColumn.prefWidthProperty().bind(template.widthProperty());

        template.getColumnConstraints().setAll(leftColumn, contentColumn, rightColumn);

        RowConstraints headerRow = new RowConstraints();
        ConstantSizeSetter.setConstantHeight(headerRow, SYSTEM_MENU_BUTTON_SIZE * 2);

        RowConstraints contentRow = new RowConstraints();
        contentRow.prefHeightProperty().bind(template.heightProperty());

        template.getRowConstraints().setAll(headerRow, contentRow);
    }

    private static void setPageName(GridPane template, String name) {
        template.add(new Label(name), NAME_POSITION.columnNumber, NAME_POSITION.rowNumber);
    }

    private static void setAnchors(GridPane gridPane) {
        AnchorPane.setTopAnchor(gridPane, ANCHOR_OFFSET_WORK_AREA);
        AnchorPane.setRightAnchor(gridPane, ANCHOR_OFFSET_WORK_AREA);
        AnchorPane.setBottomAnchor(gridPane, ANCHOR_OFFSET_WORK_AREA);
        AnchorPane.setLeftAnchor(gridPane, ANCHOR_OFFSET_WORK_AREA);
    }

}
