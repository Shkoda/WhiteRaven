package com.nightingale.view.utils;

import com.nightingale.vo.ProcessorVO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.nightingale.view.config.Config.*;

/**
 * Created by Nightingale on 09.03.14.
 */
public class EditorComponents {

    public static final GridPosition TOOLBAR_POSITION = new GridPosition(0, 2);
    public static final GridPosition CANVAS_POSITION = new GridPosition(0, 1);
    public static final GridPosition INFO_BAR_POSITION = new GridPosition(0, 0);
    public static final DropShadow SELECTION_EFFECT;

    static {
        SELECTION_EFFECT = new DropShadow();
        SELECTION_EFFECT.setRadius(5);
        SELECTION_EFFECT.setColor(Color.GREEN);
    }


    public static GridPane buildTemplateGrid() {
        GridPane editorGrid = new GridPane();

        RowConstraints statusRow = new RowConstraints();
        Utils.setConstantHeight(statusRow, STATUS_HEADER_HEIGHT);

        RowConstraints contentRow = new RowConstraints();
        contentRow.prefHeightProperty().bind(editorGrid.heightProperty());

        RowConstraints toolRow = new RowConstraints();
        Utils.setConstantHeight(toolRow, TOOL_HEIGHT * 3 / 2);

        editorGrid.getRowConstraints().setAll(statusRow, contentRow, toolRow);

        ColumnConstraints column = new ColumnConstraints();
        column.prefWidthProperty().bind(editorGrid.widthProperty());

        editorGrid.getColumnConstraints().setAll(column);
        //  editorGrid.setGridLinesVisible(true);

        return editorGrid;
    }

    public static Pane buildToolBar(ButtonBase... buttons) {
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox box = new HBox();
        box.setPadding(new Insets(8));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);


        box.getChildren().setAll(buttons);
        toolBar.getItems().addAll(box);
        borderPane.setCenter(box);

        borderPane.setStyle("-fx-border-color: transparent; ");

        return borderPane;
    }

    public static ScrollPane buildCanvasContainer() {
        ScrollPane canvasContainer = new ScrollPane();
        canvasContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasContainer.setStyle("-fx-border-color: rgb(204, 204, 204); ");
        return canvasContainer;
    }

    public static class ProcessorInfoPane {
        private ToolBar toolBar;
        private TextField nameTextArea;
        private CheckBox isIOProcessor, fullDuplexEnabled;

        public ProcessorInfoPane() {
            nameTextArea = new TextField();
            //    nameTextArea.setPrefWidth(30);

            isIOProcessor = new CheckBox("has IO:");
            fullDuplexEnabled = new CheckBox("Fullduplex enabled: ");

            toolBar = new ToolBar();
            toolBar.getItems().addAll(new Text("Processor name: "), nameTextArea, isIOProcessor, fullDuplexEnabled);
            toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");
        }


        public void setParams(ProcessorVO processorVO) {
            nameTextArea.setText(processorVO.getName());
            isIOProcessor.setSelected(processorVO.isHasIO());
            fullDuplexEnabled.setSelected(processorVO.isFullDuplexEnabled());
        }

        public ToolBar getToolBar() {
            return toolBar;
        }
    }
}
