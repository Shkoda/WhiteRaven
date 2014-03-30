package com.nightingale.view.view_components.modeller;

import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.model.entities.schedule.resourse.LinkResource;
import com.nightingale.model.entities.schedule.resourse.ProcessorResource;
import com.nightingale.view.utils.GridPosition;
import com.nightingale.view.utils.GridUtils;
import com.nightingale.view.view_components.common.PageGridBuilder;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.*;

/**
 * Created by Nightingale on 28.03.2014.
 */
public class ScheduleGridBuilder {
    public static final String GANTT_GRID = "GanttGrid";

    public static final GanttFXIds TASK = new GanttFXIds("GanttTaskTopCell", "GanttTaskMiddleCell", "GanttTaskBottomCell", "GanttTaskMonoCell");
    public static final GanttFXIds TRANSMIT = new GanttFXIds("GanttTransmittTopCell", "GanttTransmittMiddleCell", "GanttTransmittBottomCell", "GanttTransmittMonoCell");
    public static final GanttFXIds REVERSE_TRANSMIT = new GanttFXIds("GanttReverseTransmittTopCell", "GanttReverseTransmittMiddleCell",
            "GanttReverseTransmittBottomCell", "GanttReverseTransmittMonoCell");


    public static final String GANTT_EMPTY_CELL = "GanttEmptyCell";

    public static final int PROCESSOR_COLUMN_WIDTH = 40;
    public static final int LINK_COLUMN_WIDTH = 60;
    public static final int ROW_HEIGHT = 25;

    public static GridPane build(SystemModel systemModel) {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setId(GANTT_GRID);

        int rowNumber = systemModel.getDuration() + 1;

        initRows(gridPane, rowNumber);
        initColumns(gridPane, rowNumber, systemModel);
        return gridPane;
    }

    //---------------- rows initialization --------------------

    private static void initRows(GridPane gridPane, int rowNumber) {
        for (int i = 0; i < rowNumber; i++)
            gridPane.getRowConstraints().add(GridUtils.buildRowConstraintsWithConstantHeight(ROW_HEIGHT));
    }

    //---------------- columns initialization ------------------

    private static void initColumns(GridPane gridPane, int rowNumber, SystemModel systemModel) {
        addColumns(gridPane, 1 + systemModel.processorTimes.size(), PROCESSOR_COLUMN_WIDTH);
        addColumns(gridPane, systemModel.linkTimes.size(), LINK_COLUMN_WIDTH);
        initTimeColumn(gridPane, rowNumber);
        initProcessorColumns(gridPane, new ArrayList<>(systemModel.processorTimes.values()));
        initLinkColumns(gridPane, rowNumber, systemModel);
    }

    private static void addColumns(GridPane gridPane, int number, int width) {
        for (int i = 0; i < number; i++)
            gridPane.getColumnConstraints().add(GridUtils.buildColumnConstraintsWithConstantWidth(width));
    }

    private static void initTimeColumn(GridPane gridPane, int rowNumber) {
        for (int i = 1; i < rowNumber; i++) {
            fillCell(gridPane, 0, i, new Text(String.valueOf(i - 1)), GANTT_EMPTY_CELL);
        }
    }

    private static void initProcessorColumns(GridPane gridPane, List<ProcessorResource> processors) {
        int columnNumberOffset = 1;     //because of time column
        int processorNumber = processors.size();
        for (int i = 0; i < processorNumber; i++) {
            ProcessorResource processorResource = processors.get(i);
            int columnNumber = i + columnNumberOffset;
            fillCell(gridPane, columnNumber, 0, new Text(processorResource.name), GANTT_EMPTY_CELL);//header

            processorResource.executedTasks.stream().forEach(task -> {
                for (int taskTick = task.getStartTime(); taskTick <= task.getFinishTime(); taskTick++) {
                    String fxId = selectGanttNodeFxId(TASK, task.getStartTime(), task.getFinishTime(), taskTick);
                    fillCell(gridPane, columnNumber, taskTick + 1, new Text("T" + task.id), fxId);
                }
            });
        }
    }

    private static void initLinkColumns(GridPane gridPane, int rowNumber, SystemModel systemModel) {
        int columnNumberOffset = 1 + systemModel.processorTimes.size();     //time column + processors columns
        List<LinkResource> links = new ArrayList<>(systemModel.linkTimes.values());
        int linkNumber = links.size();
        for (int i = 0; i < linkNumber; i++) {
            LinkResource linkResource = links.get(i);
            fillCell(gridPane, i + columnNumberOffset, 0, new Text(linkResource.name), GANTT_EMPTY_CELL);//header

            for (int tickTime = 0; tickTime < rowNumber; tickTime++)    //empty cells
                fillCell(gridPane, i + columnNumberOffset, tickTime + 1, buildLinkTickGrid(), GANTT_EMPTY_CELL);
        }

        for (int i = 0; i < links.size(); i++) {
            LinkResource link = links.get(i);
            addTransmissions(gridPane, link.t1TransmittedTasks, i + columnNumberOffset, 0, TRANSMIT);
            addTransmissions(gridPane, link.t2TransmittedTasks, i + columnNumberOffset, 1, REVERSE_TRANSMIT);
        }

    }


    private static void addTransmissions(GridPane gridPane, Map<Task, LinkResource.TransmissionDescription> tasks,
                                         int gridColumn, int cellColumn, GanttFXIds fxIds) {
        for (Map.Entry<Task, LinkResource.TransmissionDescription> kv : tasks.entrySet()) {
            Task task = kv.getKey();
            LinkResource.TransmissionDescription description = kv.getValue();

            for (int tickTime = description.startTime; tickTime <= description.finishTime; tickTime++) {
                String fxId = selectGanttNodeFxId(fxIds, description.startTime, description.finishTime, tickTime);
                GridPane currentCell = getLinkGrid(gridPane, gridColumn, tickTime + 1);
                fillCell(currentCell, cellColumn, 0, new Text("T" + task.id), fxId);
            }
        }
    }

    private static GridPane buildLinkTickGrid() {
        GridPane transmitGrid = new GridPane();
        for (int col = 0; col < 2; col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(50);
            transmitGrid.getColumnConstraints().add(columnConstraints);
        }

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        transmitGrid.getRowConstraints().add(rowConstraints);
        return transmitGrid;
    }

    private static void fillCell(GridPane gridPane, int column, int row, Node node, String fxId) {
        BorderPane pane = new BorderPane();
        pane.setId(fxId);
        pane.setCenter(node);
        gridPane.add(pane, column, row);
    }

    private static String selectGanttNodeFxId(GanttFXIds ids, int startTick, int finishTick, int currentTick) {
        return finishTick - startTick == 0 ? ids.MONO : //duration ==1
                currentTick == startTick ? ids.TOP :
                        currentTick == finishTick ? ids.BOTTOM :
                                ids.MIDDLE;
    }

    private static GridPane getLinkGrid(GridPane gridPane, int column, int row) {
        return (GridPane) ((BorderPane) PageGridBuilder
                .getCellNode(gridPane, new GridPosition(column, row)))
                .getCenter();
    }

    public static class GanttFXIds {
        public final String MONO, TOP, MIDDLE, BOTTOM;
        public GanttFXIds(String TOP, String MIDDLE, String BOTTOM, String MONO) {
            this.MONO = MONO;
            this.TOP = TOP;
            this.MIDDLE = MIDDLE;
            this.BOTTOM = BOTTOM;
        }
    }
}
