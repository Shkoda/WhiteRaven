package com.nightingale.view.modeller_page;

import com.google.inject.Inject;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.statistics_page.StatisticsView;
import com.nightingale.view.tasks_editor_page.TasksEditorView;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
public class ModellerView implements IModellerView {
    @Inject
    public TasksEditorView tasksEditorView;
    @Inject
    public StatisticsView statisticsView;

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
        return tasksEditorView;
    }

    @Override
    public ViewablePage getNextPage() {
        return statisticsView;
    }

    @Override
    public Pane getView() {
        return new Pane();
    }
}
