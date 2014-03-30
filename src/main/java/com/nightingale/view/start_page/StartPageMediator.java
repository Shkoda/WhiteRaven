package com.nightingale.view.start_page;

import com.google.inject.Inject;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.editor.proscessor_editor_page.IProcessorEditorView;
import com.nightingale.view.editor.tasks_editor_page.ITasksEditorView;
import com.nightingale.view.main_page.IMainView;
import com.nightingale.view.modeller_page.IModellerView;
import com.nightingale.view.modeller_page.ModellerView;
import com.nightingale.view.editor.proscessor_editor_page.ProcessorEditorView;
import com.nightingale.view.statistics_page.IStatisticsView;
import com.nightingale.view.statistics_page.StatisticsView;
import com.nightingale.view.editor.tasks_editor_page.TasksEditorView;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


/**
 * Created by Nightingale on 09.03.14.
 */
public class StartPageMediator implements IStartPageMediator {
    @Inject
    public IMainView mainView;
    @Inject
    public IStartPageView startPageView;
    @Inject
    public IProcessorEditorView processorEditor;
    @Inject
    public ITasksEditorView tasksEditor;
    @Inject
    public IModellerView modellerView;
    @Inject
    public IStatisticsView statisticsView;


    @Override
    public void init() {
        setClickHandler(startPageView.getProcessorEditorLink(), processorEditor);
        setClickHandler(startPageView.getTaskEditorLink(), tasksEditor);
        setClickHandler(startPageView.getModellerLink(), modellerView);
        setClickHandler(startPageView.getStatisticsLink(), statisticsView);
    }

    private void setClickHandler(Button button, final ViewablePage goToPage) {
        button.setOnMouseClicked(me -> {
            mainView.show(goToPage);
        });
    }
}
