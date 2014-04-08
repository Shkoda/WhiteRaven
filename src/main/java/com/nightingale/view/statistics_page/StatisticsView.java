package com.nightingale.view.statistics_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.entities.statistics.ExperimentResult;
import com.nightingale.utils.Loggers;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.modeller_page.IModellerView;
import com.nightingale.view.utils.GridUtils;
import com.nightingale.view.view_components.editor.EditorGridBuilder;
import com.nightingale.view.view_components.editor.EditorToolBuilder;
import com.nightingale.view.view_components.statistics.StatisticsTable;
import com.nightingale.view.view_components.statistics.StatisticsToolBar;
import com.sun.org.glassfish.external.statistics.Statistic;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import static com.nightingale.view.view_components.editor.EditorConstants.CANVAS_POSITION;
import static com.nightingale.view.view_components.editor.EditorConstants.INFO_BAR_POSITION;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class StatisticsView implements IStatisticsView {
    @Inject
    public IModellerView previousPage;
    @Inject
    private IStatisticsMediator statisticsMediator;

    private GridPane view;
    private ToolBar toolBar;
    private Pane infoContainer;
    private Button refreshStatisticsButton;
    private StatisticsToolBar statisticsToolBar;

    private BorderPane waitInfoPane, defaultInfoPane;


    @Override
    public Pane getView() {
        Loggers.debugLogger.debug("get StatisticsView");
        return view == null ? initView() : view;
    }

    private GridPane initView() {
        view = EditorGridBuilder.build();

        statisticsToolBar = new StatisticsToolBar();

        infoContainer = new Pane();
        infoContainer.setStyle("-fx-background-color:  #f7f7f7; -fx-border-color: #bababa");
        infoContainer.getChildren().setAll(statisticsToolBar.getToolBar());

        view.add(infoContainer, INFO_BAR_POSITION.columnNumber, INFO_BAR_POSITION.rowNumber);

        refreshStatisticsButton = EditorToolBuilder.build("RefreshButton", Config.EDITOR_BUTTON_SIZE, "Refresh statistics");
        view.add(refreshStatisticsButton, 0, 2);

        waitInfoPane = new BorderPane();
        waitInfoPane.setCenter(new Label("Please wait..."));

        defaultInfoPane = new BorderPane();
        defaultInfoPane.setCenter(new Label("   Build correct MPP\nand press refresh button"));

        view.add(defaultInfoPane, CANVAS_POSITION.columnNumber, CANVAS_POSITION.rowNumber);


        statisticsMediator.initFieldValidators();
        statisticsMediator.initSubmitButton();
        return view;
    }

    public void setStatistics(ObservableList<ExperimentResult> statistics) {
        GridUtils.clearCell(view, CANVAS_POSITION);
        javafx.scene.Node showNode = statistics != null ? StatisticsTable.build(statistics) : waitInfoPane;
        view.add(showNode, CANVAS_POSITION.columnNumber, CANVAS_POSITION.rowNumber);
    }


    @Override
    public TextField getMinTaskNumberField() {
        return statisticsToolBar.minTaskNumberField;
    }

    @Override
    public TextField getMaxTaskNumberField() {
        return statisticsToolBar.maxTaskNumberField;
    }

    @Override
    public TextField getVertexNumberGrowStep() {
        return statisticsToolBar.vertexNumberGrowStep;
    }

    @Override
    public TextField getMinTaskConnectivityField() {
        return statisticsToolBar.minTaskConnectivityField;
    }

    @Override
    public TextField getMaxTaskConnectivityField() {
        return statisticsToolBar.maxTaskConnectivityField;
    }

    @Override
    public TextField getConnectivityGrowStep() {
        return statisticsToolBar.connectivityGrowStep;
    }

    @Override
    public TextField getMinTaskWeightField() {
        return statisticsToolBar.minTaskWeightField;
    }

    @Override
    public TextField getMaxTaskWeightField() {
        return statisticsToolBar.maxTaskWeightField;
    }

    @Override
    public TextField getGraphNumber() {
        return statisticsToolBar.graphNumber;
    }

    @Override
    public Button getRefreshButton() {
        return refreshStatisticsButton;
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
