package com.nightingale.view.modeller_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.modelling.GenerateTaskQueueCommand;
import com.nightingale.command.modelling.RefreshModellerData;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.graph.AcyclicDirectedGraph;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.DeadlineDifferenceConsumer;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.NodesAfterCurrentConsumer;
import com.nightingale.command.modelling.critical_path_functions.node_rank_consumers.TimeBeforeCurrentConsumer;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.view.view_components.modeller.GanttViewBuilder;
import com.nightingale.view.view_components.modeller.ModellerComboBoxBuilder;
import com.nightingale.view.view_components.modeller.ModellerConstants;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Nightingale on 09.03.14.
 */
@Singleton
public class ModellerMediator implements IModellerMediator {
    @Inject
    private IModellerView modellerView;
    @Inject
    private ICommandProvider commandProvider;

    private TextArea queueTextArea;

    private Map<String, String> queueMap;
    private Map<ModellerConstants.ScheduleType, SystemModel> systemModels;

    private ComboBox queueBox, scheduleBox;
    private ScrollPane ganttContainer;

    @Override
    public void refreshView() {
        RefreshModellerData command = commandProvider.get(RefreshModellerData.class);
        command.graph = DataManager.getTaskGraphModel().getAcyclicDirectedGraph();
        command.modellerMediator = this;

        command.setOnSucceeded(workerStateEvent -> {
            queueBox.getSelectionModel().select(null);
            queueTextArea.setText("");

            scheduleBox.getSelectionModel().select(null);
            ganttContainer.setContent(new Pane());

//
//            List<AcyclicDirectedGraph.Node> queue = (List<AcyclicDirectedGraph.Node>) workerStateEvent.getSource().getValue();
//            String newQueue = toString(queue);
//            queueMap.put(key, newQueue);
//            if (key.equals(queueBox.getSelectionModel().getSelectedItem()))
//                modellerView.getQueueTextArea().setText(newQueue);


        });
        command.start();
    }

    @Override
    public void initScheduleComboBox() {
        ganttContainer = modellerView.getGanttContainer();
        systemModels = new HashMap<>();
        scheduleBox = ModellerComboBoxBuilder.buildLoadComboBox();
        modellerView.setScheduleComboBox(scheduleBox);
        scheduleBox.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    Object selectedQueue = queueBox.getSelectionModel().getSelectedItem();
                    if (selectedQueue == null)
                        return;
                    ModellerConstants.ScheduleType scheduleType =
                            ModellerConstants.ScheduleType.get(selectedQueue.toString(), new_val.toString());

                    if (scheduleType != null && systemModels.containsKey(scheduleType))
                        ganttContainer.setContent(GanttViewBuilder.build(systemModels.get(scheduleType)));
                }
        );
    }

    @Override
    public void initQueueComboBox() {
        queueTextArea = modellerView.getQueueTextArea();
        queueMap = new HashMap<>();

        queueBox = ModellerComboBoxBuilder.buildQueueComboBox();
        modellerView.setQueueComboBox(queueBox);
        //      refreshQueues();
        queueBox.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    queueTextArea.setText(queueMap.get(new_val));

                    Object selectedLoading = scheduleBox.getSelectionModel().getSelectedItem();
                    if (selectedLoading== null || new_val == null)
                        return;
                    ModellerConstants.ScheduleType scheduleType =
                            ModellerConstants.ScheduleType.get(new_val.toString(), selectedLoading.toString());

                    if (scheduleType != null&& systemModels.containsKey(scheduleType))
                        ganttContainer.setContent(GanttViewBuilder.build(systemModels.get(scheduleType)));
                }
        );
    }

    @Override
    public Map<String, String> getQueueMap() {
        return queueMap;
    }

    @Override
    public Map<ModellerConstants.ScheduleType, SystemModel> getScheduleMap() {
        return systemModels;
    }

    @Override
    public void refreshQueues() {
        if (queueMap == null)
            return;
        AcyclicDirectedGraph acyclicDirectedGraph = DataManager.getTaskGraphModel().getAcyclicDirectedGraph();
        refreshQueue(acyclicDirectedGraph, new DeadlineDifferenceConsumer(), true, ModellerConstants.FIRST_QUEUE_ALGORITHM_TEXT);
        refreshQueue(acyclicDirectedGraph, new NodesAfterCurrentConsumer(), false, ModellerConstants.SECOND_QUEUE_ALGORITHM_TEXT);
        refreshQueue(acyclicDirectedGraph, new TimeBeforeCurrentConsumer(), true, ModellerConstants.THIRD_QUEUE_ALGORITHM_TEXT);


    }

    private void refreshQueue(AcyclicDirectedGraph graph, Consumer<AcyclicDirectedGraph> consumer, boolean useIncreaseOrder, String key) {
        GenerateTaskQueueCommand command = commandProvider.get(GenerateTaskQueueCommand.class);
        command.graph = graph;
        command.consumer = consumer;
        command.useIncreaseOrder = useIncreaseOrder;

        command.setOnSucceeded(workerStateEvent -> {
            List<AcyclicDirectedGraph.Node> queue = (List<AcyclicDirectedGraph.Node>) workerStateEvent.getSource().getValue();
            String newQueue = toString(queue);
            queueMap.put(key, newQueue);
            if (key.equals(queueBox.getSelectionModel().getSelectedItem()))
                modellerView.getQueueTextArea().setText(newQueue);


        });
        command.start();
    }

    private static String toString(List<AcyclicDirectedGraph.Node> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i).getName());
            if (i < list.size() - 1)
                builder.append(" -> ");
        }
        return builder.toString();
    }
}
