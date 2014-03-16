package com.nightingale.view.editor.tasks_editor_page.listeners;

import com.nightingale.model.tasks.elements.TaskModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class TaskWeightChangeListener implements ChangeListener<String> {

    private final TaskModel taskModel;

    public TaskWeightChangeListener(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                taskModel.setWeight(Integer.valueOf(newValue));
            } catch (Exception ignored) {

            }

    }
}
