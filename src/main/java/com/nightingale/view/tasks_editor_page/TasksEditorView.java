package com.nightingale.view.tasks_editor_page;

import com.google.inject.Inject;
import com.nightingale.view.ViewablePage;
import com.nightingale.view.config.Config;
import com.nightingale.view.modeller_page.ModellerView;
import com.nightingale.view.proscessor_editor_page.ProcessorEditorView;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 09.03.14.
 */
public class TasksEditorView implements ITasksEditorView {
    @Inject
    public ProcessorEditorView processorEditorView;
    @Inject
    public ModellerView modellerView;

    @Override
    public Pane getView() {
        return new Pane();
    }

    @Override
    public String getName() {
        return Config.TASK_EDITOR_PAGE_NAME;
    }

    @Override
    public ViewablePage getPreviousPage() {
        return processorEditorView;
    }

    @Override
    public ViewablePage getNextPage() {
        return modellerView;
    }

    @Override
    public boolean isNavigationVisible() {
        return true;
    }
}
