package com.nightingale.view.modeller_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.Graph;
import com.nightingale.model.mpp.ProcessorLinkModel;
import com.nightingale.model.mpp.ProcessorModel;
import com.nightingale.model.mpp.SystemMoment;
import com.nightingale.utils.Loggers;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorView;
import com.nightingale.view.statistics_page.IStatisticsView;
import com.nightingale.view.statistics_page.StatisticsView;
import com.nightingale.view.editor.tasks_editor_page.TasksEditorView;
import com.nightingale.view.utils.GridPosition;
import com.nightingale.view.view_components.common.PageGridBuilder;
import com.nightingale.view.view_components.modeller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;

import static com.nightingale.view.view_components.common.PageGridBuilder.WORK_PANE_POSITION;
import static com.nightingale.view.view_components.modeller.ModellerConstants.*;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class ModellerView implements IModellerView {
    @Inject
    public ITasksEditorView previousPage;
    @Inject
    public IStatisticsView nextPage;
    @Inject
    private IModellerMediator modellerMediator;
    private GridPane view;
    private ComboBox<String> queueBox, loadBox;
    private GridPane queueGrid;
    private TextArea queueTextArea;
    private ScrollPane taskPane;
    private ScrollPane mppScrollPane;
    private TableView mppTableView;
    private String queueAlgorithm2, queueAlgorithm6, queueAlgorithm16;

    @Override
    public Pane getView() {
        Loggers.debugLogger.debug("get modeller view");
        return view == null ? initView() : refreshView();

    }

    private GridPane refreshView() {
        refreshTaskGraphSnapshot();
        refreshMppView();
        modellerMediator.refreshQueues();
        return view;
    }

    private GridPane initView() {
        view = ModellerPageGridBuilder.build();

        loadBox = ModellerComboBoxBuilder.buildLoadComboBox();
        addComboBox(loadBox, ModellerConstants.SELECT_LOAD_ALGORITHM_POSITION);


        queueGrid = ModellerQueueGridBuilder.build();
        view.add(queueGrid, OUEUE_PANE_POSITION.columnNumber, OUEUE_PANE_POSITION.rowNumber);

        queueTextArea = ModellerQueueTextAreaBuilder.build();
        queueGrid.add(queueTextArea, OUEUE_POSITION.columnNumber, OUEUE_POSITION.rowNumber);

        mppScrollPane = new ScrollPane();
        view.add(mppScrollPane, MPP_PANE_POSITION.columnNumber, MPP_PANE_POSITION.rowNumber);

        modellerMediator.initQueueComboBox();

        return refreshView();
    }

    @Override
    public TextArea getQueueTextArea() {
        return queueTextArea;
    }

    @Override
    public void setQueueComboBox(ComboBox queueComboBox) {
        this.queueBox = queueComboBox;
        addComboBox(queueBox, ModellerConstants.SELECT_QUEUE_ALGORITHM_POSITION);
    }

    private void refreshTaskGraphSnapshot() {
        Pane graphView = previousPage.getGraphView();
        if (graphView == null || graphView.getWidth() == 0 || graphView.getHeight() == 0)
            return;
        WritableImage writableImage = new WritableImage((int) graphView.getWidth(), (int) graphView.getHeight());
        taskPane = new ScrollPane();
        taskPane.setContent(new ImageView(previousPage.getGraphView().snapshot(new SnapshotParameters(), writableImage)));
        PageGridBuilder.clearCell(queueGrid, TASK_GRAPH_POSITION);
        queueGrid.add(taskPane, TASK_GRAPH_POSITION.columnNumber, TASK_GRAPH_POSITION.rowNumber);
    }

    private void refreshMppView() {
        Graph<ProcessorModel, ProcessorLinkModel> mppModel = DataManager.getMppModel();
        mppTableView = new TableView();

        for (ProcessorModel processorModel : mppModel.getVertexes())
            mppTableView.getColumns().add(new TableColumn<>(processorModel.getName()));

        for (ProcessorLinkModel linkModel : mppModel.getConnections())
            mppTableView.getColumns().add(new TableColumn<>(linkModel.getName()));

        ObservableList<SystemMoment> data =
                FXCollections.observableArrayList(
                        new SystemMoment(1, new HashMap<>(), new HashMap<>()),
                        new SystemMoment(2, new HashMap<>(), new HashMap<>()),
                        new SystemMoment(3, new HashMap<>(), new HashMap<>())
                );

        mppScrollPane.setContent(mppTableView);
    }

    private void addComboBox(ComboBox comboBox, GridPosition gridPosition) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(comboBox);
        view.add(borderPane, gridPosition.columnNumber, gridPosition.rowNumber);
    }

    @Override
    public String getName() {
        return Config.MODELLER_PAGE_NAME;
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
        return nextPage;
    }


}
