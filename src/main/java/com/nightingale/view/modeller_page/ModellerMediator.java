package com.nightingale.view.modeller_page;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nightingale.application.guice.ICommandProvider;
import com.nightingale.command.modelling.RefreshModellerData;
import com.nightingale.model.DataManager;
import com.nightingale.model.entities.schedule.SystemModel;
import com.nightingale.utils.Loggers;
import com.nightingale.view.view_components.modeller.GanttViewBuilder;
import com.nightingale.view.view_components.modeller.ModellerComboBoxBuilder;
import com.nightingale.view.view_components.modeller.ModellerConstants;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

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
    private Map<ModellerConstants.ScheduleDescription, SystemModel> systemModels;

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
                    Loggers.debugLogger.debug(queueBox.getSelectionModel().getSelectedItem()+" "+queueMap.get(queueBox.getSelectionModel().getSelectedItem().toString()));
                    ModellerConstants.ScheduleDescription scheduleDescription =
                            ModellerConstants.ScheduleDescription.get(selectedQueue.toString(), new_val.toString());

                    if (scheduleDescription != null && systemModels.containsKey(scheduleDescription)){
                        SystemModel systemModel = systemModels.get(scheduleDescription);
                        System.out.println("\n"+scheduleDescription+"\n"+selectedQueue.toString()+"\n"+systemModel+"\n");
                        ganttContainer.setContent(GanttViewBuilder.build(systemModel));
                    }
                }
        );
    }

    @Override
    public void initQueueComboBox() {
        queueTextArea = modellerView.getQueueTextArea();
        queueMap = new HashMap<>();

        queueBox = ModellerComboBoxBuilder.buildQueueComboBox();
        modellerView.setQueueComboBox(queueBox);
        queueBox.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    Loggers.debugLogger.debug(new_val+" "+queueMap.get(new_val.toString()));
                    queueTextArea.setText(queueMap.get(new_val.toString()));

                    Object selectedLoading = scheduleBox.getSelectionModel().getSelectedItem();
                    if (selectedLoading == null || new_val == null)
                        return;
                    ModellerConstants.ScheduleDescription scheduleType =
                            ModellerConstants.ScheduleDescription.get(new_val.toString(), selectedLoading.toString());

                    if (scheduleType != null && systemModels.containsKey(scheduleType)){
                        SystemModel systemModel = systemModels.get(scheduleType);
                        System.out.println("\n"+scheduleType+"\n"+new_val.toString()+"\n"+systemModel+"\n");
                        ganttContainer.setContent(GanttViewBuilder.build(systemModel));
                    }
                }
        );
    }

    @Override
    public Map<String, String> getQueueMap() {
        return queueMap;
    }

    @Override
    public Map<ModellerConstants.ScheduleDescription, SystemModel> getScheduleMap() {
        return systemModels;
    }

}
