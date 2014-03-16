package com.nightingale.view.utils;

import com.nightingale.view.proscessor_editor_page.listeners.ProcessorDuplexPropertyListener;
import com.nightingale.view.proscessor_editor_page.listeners.ProcessorIOPropertyChangeListener;
import com.nightingale.view.proscessor_editor_page.listeners.ProcessorPerformanceChangeListener;
import com.nightingale.vo.ProcessorVO;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.regex.Pattern;

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
        private static final Pattern INPUT_PATTERN = Pattern.compile("\\d+\\.\\d+");

        private ToolBar toolBar;
        private TextField performanceTextField;
        private CheckBox isIOProcessor, fullDuplexEnabled;

        private ProcessorPerformanceChangeListener performanceChangeListener;
        private ProcessorIOPropertyChangeListener ioPropertyChangeListener;
        private ProcessorDuplexPropertyListener duplexPropertyListener;

        public ProcessorInfoPane() {
            performanceTextField = new TextField();
            performanceTextField.setPrefWidth(50);
//            performanceTextField.textProperty().addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                    if (! INPUT_PATTERN.matcher(newValue).matches()) {
//                       performanceTextField. setText(oldValue);
//                    }
//                }
//            });

            performanceTextField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    try {
                        Double.valueOf(performanceTextField.getText());
                    } catch (Exception e) {
                        keyEvent.consume();
                    }
                }
            });

            //    performanceTextField.setPrefWidth(30);

            isIOProcessor = new CheckBox("has IO");
            fullDuplexEnabled = new CheckBox("Fullduplex enabled");

            toolBar = new ToolBar();
            toolBar.getItems().addAll(new Text("Processor performance: "), performanceTextField, isIOProcessor, fullDuplexEnabled);
            toolBar.setStyle("-fx-border-color:transparent;-fx-background-color: transparent ");
        }


        public void setParams(ProcessorVO processorVO) {
            performanceTextField.setText(String.valueOf(processorVO.getPerformance()));
            isIOProcessor.setSelected(processorVO.isHasIO());
            fullDuplexEnabled.setSelected(processorVO.isFullDuplexEnabled());
        }

        public void bindParams(final ProcessorVO processorVO) {
            performanceChangeListener = new ProcessorPerformanceChangeListener(processorVO);
            ioPropertyChangeListener = new ProcessorIOPropertyChangeListener(processorVO);
            duplexPropertyListener = new ProcessorDuplexPropertyListener(processorVO);

            performanceTextField.textProperty().addListener(performanceChangeListener);
            isIOProcessor.selectedProperty().addListener(ioPropertyChangeListener);
            fullDuplexEnabled.selectedProperty().addListener(duplexPropertyListener);
        }

        public void unbindParams() {
            if (performanceChangeListener == null || ioPropertyChangeListener == null || fullDuplexEnabled == null) {
                return;
            }

            //    performanceTextField.textProperty().removeListener(performanceChangeListener);
            isIOProcessor.selectedProperty().removeListener(ioPropertyChangeListener);
            fullDuplexEnabled.selectedProperty().removeListener(duplexPropertyListener);
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

    public static Tuple<Point2D, Point2D> getBestLineEnds(Node firstNode, Node secondNode) {
        double firstCenterX = firstNode.getTranslateX() + firstNode.getLayoutBounds().getWidth() / 2;
        double firstCenterY = firstNode.getTranslateY() + firstNode.getLayoutBounds().getHeight() / 2;

        double secondCenterX = secondNode.getTranslateX() + firstNode.getLayoutBounds().getWidth() / 2;
        double secondCenterY = secondNode.getTranslateY() + firstNode.getLayoutBounds().getHeight() / 2;

        return new Tuple<>(new Point2D(firstCenterX, firstCenterY), new Point2D(secondCenterX, secondCenterY));
    }
}
