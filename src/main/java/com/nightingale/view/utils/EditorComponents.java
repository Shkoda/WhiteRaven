package com.nightingale.view.utils;

import com.nightingale.vo.ProcessorVO;
import com.sun.javafx.animation.transition.Position2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.Map;

import static com.nightingale.view.config.Config.*;

/**
 * Created by Nightingale on 09.03.14.
 */
public class EditorComponents {

    public static final GridPosition TOOLBAR_POSITION = new GridPosition(0, 2);
    public static final GridPosition CANVAS_POSITION = new GridPosition(0, 1);
    public static final GridPosition INFO_BAR_POSITION = new GridPosition(0, 0);
    public static final DropShadow SELECTION_EFFECT;
    public static final DropShadow START_LINK_EFFECT;

    static {
        SELECTION_EFFECT = new DropShadow();
        SELECTION_EFFECT.setRadius(5);
        SELECTION_EFFECT.setColor(Color.GREEN);

        START_LINK_EFFECT = new DropShadow();
        START_LINK_EFFECT.setRadius(5);
        START_LINK_EFFECT.setColor(Color.ORANGE);
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

    public static ToggleButton createToolButton(String buttonId, ToggleGroup group, int size, String tooltipText) {
        ToggleButton button = new ToggleButton();
        button.setId(buttonId);
        button.setPrefSize(size, size);
        button.setMinSize(size, size);
        button.setToggleGroup(group);

        Tooltip tooltip = new Tooltip();
        tooltip.setText(tooltipText);

        button.setTooltip(tooltip);

        return button;
    }

    public static Tuple<Position2D, Position2D> getBestLineEnds(Node firstNode, Node secondNode) {
        double firstCenterX = firstNode.getTranslateX() + firstNode.getLayoutBounds().getWidth() / 2;
        double firstCenterY = firstNode.getTranslateY() + firstNode.getLayoutBounds().getHeight() / 2;

        double secondCenterX = secondNode.getTranslateX() + firstNode.getLayoutBounds().getWidth() / 2;
        double secondCenterY = secondNode.getTranslateY() + firstNode.getLayoutBounds().getHeight() / 2;

        Position2D firstGoodPoint = new Position2D();
        firstGoodPoint.x = firstCenterX;
        firstGoodPoint.y = firstCenterY;

        Position2D secondGoodPoint = new Position2D();
        secondGoodPoint.x = secondCenterX;
        secondGoodPoint.y = secondCenterY;

//        Line line = new Line(firstCenterX, firstCenterY, secondCenterX, secondCenterY);
//
//        Shape firstShape =  new Rectangle(firstNode.getLayoutX(), firstNode.getLayoutY(), firstNode.getLayoutBounds().getWidth(), firstNode.getLayoutBounds().getHeight());
//        Shape secondShape =  new Rectangle(secondNode.getLayoutX(), secondNode.getLayoutY(), secondNode.getLayoutBounds().getWidth(), secondNode.getLayoutBounds().getHeight());
//
//        Shape firstIntersection = Shape.intersect(line, firstShape);
//        Shape secondIntersection = Shape.intersect(line, secondShape);
//
//        Position2D firstGoodPoint = new Position2D();
//        firstGoodPoint.x = firstIntersection.getLayoutX();
//        firstGoodPoint.y = firstIntersection.getLayoutY();
//
//        Position2D secondGoodPoint = new Position2D();
//        secondGoodPoint.x = secondIntersection.getLayoutX();
//        secondGoodPoint.y = secondIntersection.getLayoutY();
//
        return new Tuple<>(firstGoodPoint, secondGoodPoint);
    }
}
