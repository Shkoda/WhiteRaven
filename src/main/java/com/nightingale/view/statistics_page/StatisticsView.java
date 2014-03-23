package com.nightingale.view.statistics_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.utils.Loggers;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.modeller_page.IModellerView;
import com.nightingale.view.view_components.editor.EditorGridBuilder;
import com.nightingale.view.view_components.editor.EditorToolBuilder;
import com.nightingale.view.view_components.statistics.StatisticsBarChart;
import com.nightingale.view.view_components.statistics.StatisticsToolBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.nightingale.view.view_components.editor.EditorConstants.INFO_BAR_POSITION;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class StatisticsView implements IStatisticsView {
    @Inject
    public IModellerView previousPage;

    private GridPane view;
    private ToolBar toolBar;
    private Pane infoContainer;
    private Pane chartContainer;
    private Button statisticsButton;
    private StatisticsBarChart statisticsBarChart;

    final static String TASK_QUEUE_2_PLANNING_3 = "Task queue - (2), Planning - (3)";
    final static String TASK_QUEUE_6_PLANNING_3 = "Task queue - (6), Planning - (3)";
    final static String TASK_QUEUE_16_PLANNING_3 = "Task queue - (16), Planning - (3)";
    final static String TASK_QUEUE_2_PLANNING_5 = "Task queue - (2), Planning - (5)";
    final static String TASK_QUEUE_6_PLANNING_5 = "Task queue - (6), Planning - (5)";
    final static String TASK_QUEUE_16_PLANNING_5 = "Task queue - (16), Planning - (5)";

    final static String ACCELERATION_FACTOR = "Acceleration";
    final static String EFFICIENCY_RATIO = "Efficiency";
    final static String SCHEDULING_ALGORITHM_EFFICIENCY_RATIO = "Scheduling algorithm";

    @Override
    public Pane getView() {
        Loggers.debugLogger.debug("get StatisticsView");
        return view == null ? initView() : view;
    }

    private GridPane initView() {
        view = EditorGridBuilder.build();

        StatisticsToolBar statisticsToolBar = new StatisticsToolBar();

        infoContainer = new Pane();
        infoContainer.setStyle("-fx-background-color:  #f7f7f7; -fx-border-color: #bababa");

        infoContainer.getChildren().setAll(statisticsToolBar.getToolBar());

        view.add(infoContainer, INFO_BAR_POSITION.columnNumber, INFO_BAR_POSITION.rowNumber);

        statisticsBarChart = new StatisticsBarChart();
        view.add(statisticsBarChart.getContainer(), 0, 1);

        statisticsButton = EditorToolBuilder.build("RefreshButton", Config.EDITOR_BUTTON_SIZE, "Refresh statistics");
        view.add(statisticsButton, 0, 2);

        return view;
    }



    @Override
    public String getName() {
        return Config.STATISTICS_PAGE_NAME;
    }

    @Override
    public boolean isNavigationVisible() {
        return true;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return previousPage;
    }

    @Override
    public ViewablePage getNextPage() {
        return null;
    }
}
