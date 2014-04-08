package com.nightingale.view.view_components.common;


import com.nightingale.view.config.Config;
import com.nightingale.view.utils.GridPosition;
import com.nightingale.view.utils.GridUtils;
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



    private static void setConstraints(GridPane template) {
        ColumnConstraints leftColumn = GridUtils.buildColumnConstraintsWithConstantWidth(Config.SYSTEM_MENU_BUTTON_SIZE * 2);
        ColumnConstraints rightColumn = GridUtils.buildColumnConstraintsWithConstantWidth(Config.SYSTEM_MENU_BUTTON_SIZE * 2);
        ColumnConstraints contentColumn = GridUtils.buildBindedColumnConstraints(template.widthProperty());

        RowConstraints headerRow = GridUtils.buildRowConstraintsWithConstantHeight(SYSTEM_MENU_BUTTON_SIZE * 2);
        RowConstraints contentRow = GridUtils.buildBindedRowConstraints(template.heightProperty());

        template.getColumnConstraints().setAll(leftColumn, contentColumn, rightColumn);
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
