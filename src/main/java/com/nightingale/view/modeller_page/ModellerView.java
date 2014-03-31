package com.nightingale.view.modeller_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.model.entities.schedule.Task;
import com.nightingale.utils.Loggers;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorView;
import com.nightingale.view.statistics_page.IStatisticsView;
import com.nightingale.view.utils.GridPosition;
import com.nightingale.view.view_components.common.PageGridBuilder;
import com.nightingale.view.view_components.modeller.*;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


import java.util.*;

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
    @Inject
    private ICommandProvider commandProvider;

    private GridPane view;
    private BorderPane errorMessageView;

    private Pane currentView;

    private ComboBox<String> queueBox, loadBox;
    private GridPane queueGrid;
    private TextArea queueTextArea;
    private ScrollPane taskPane;
    private ScrollPane ganttContainer;

    @Override
    public Pane getView() {
        Loggers.debugLogger.debug("get modeller view");

//        CheckMppCommand command = commandProvider.get(CheckMppCommand.class);
//        command.setOnSucceeded(workerStateEvent -> {
//                    Boolean mppIsOk = (Boolean) workerStateEvent.getSource().getValue();
//                    Loggers.debugLogger.debug("mpp ok: " + mppIsOk);
//                    setCurrentView(mppIsOk);
//
//                }
//        );
//        command.start();
        setCurrentView(true);
        return currentView;
    }

    private void setCurrentView(boolean isMppOk) {
        if (currentView == null)
            initView();
        currentView = isMppOk ? refreshView() : errorMessageView;
    }

    private GridPane refreshView() {
        refreshTaskGraphSnapshot();
        refreshMppView();
   //     modellerMediator.refreshQueues();
        modellerMediator.refreshView();
        return view;
    }

    private void initView() {
        view = ModellerPageGridBuilder.build();

//        loadBox = ModellerComboBoxBuilder.buildLoadComboBox();
//        addComboBox(loadBox, ModellerConstants.SELECT_LOAD_ALGORITHM_POSITION);


        queueGrid = ModellerQueueGridBuilder.build();
        view.add(queueGrid, OUEUE_PANE_POSITION.columnNumber, OUEUE_PANE_POSITION.rowNumber);

        queueTextArea = ModellerQueueTextAreaBuilder.build();
        queueGrid.add(queueTextArea, OUEUE_POSITION.columnNumber, OUEUE_POSITION.rowNumber);

        ganttContainer = new ScrollPane();
        view.add(ganttContainer, MPP_PANE_POSITION.columnNumber, MPP_PANE_POSITION.rowNumber);

        modellerMediator.initQueueComboBox();
        modellerMediator.initScheduleComboBox();

        errorMessageView = new BorderPane();
        errorMessageView.setCenter(new Text("Your MPP is not correct." +
                "\nIt should contain at least one processors." +
                "\nProcessor graph should be fully connected."));

    }

    @Override
    public ScrollPane getGanttContainer() {
        return ganttContainer;
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

    @Override
    public void setScheduleComboBox(ComboBox scheduleComboBox) {
        this.loadBox = scheduleComboBox;
        addComboBox(loadBox, ModellerConstants.SELECT_LOAD_ALGORITHM_POSITION);
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

//        Graph<ProcessorModel, ProcessorLinkModel> mppModel = DataManager.getMppModel();
//        mppTableView = new TableView();
//
//        for (ProcessorModel processorModel : mppModel.getVertexes())
//            mppTableView.getColumns().add(new TableColumn<>(processorModel.getName()));
//
//        for (ProcessorLinkModel linkModel : mppModel.getConnections())
//            mppTableView.getColumns().add(new TableColumn<>(linkModel.getName()));
//
//        ObservableList<SystemMoment> data =
//                FXCollections.observableArrayList(
//                        new SystemMoment(1, new HashMap<>(), new HashMap<>()),
//                        new SystemMoment(2, new HashMap<>(), new HashMap<>()),
//                        new SystemMoment(3, new HashMap<>(), new HashMap<>())
//                );
//
//      mppTableView.getItems().add(null);

//        List<AcyclicDirectedGraph.Node> queue = DataManager.getTaskGraphModel().getAcyclicDirectedGraph().getTaskQueue(new NodesAfterCurrentConsumer(), false);
//
//        List<Task> convertedTasks = Task.convert(queue);
//        System.out.println(convertedTasks);
//
//        SystemModel systemModel = new SystemModel(DataManager.getMppModel());
//
//        systemModel.loadTasks(convertedTasks, systemModel.SHORTEST_PATH_FUNCTION);
//
//        ganttContainer.setContent(GanttViewBuilder.build(systemModel));


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
