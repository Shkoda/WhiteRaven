package com.nightingale.view.editor.tasks_editor_page.listeners;

import com.nightingale.model.tasks.elements.TaskLinkModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ConnectionWeightListener implements ChangeListener<String> {

    private final TaskLinkModel taskLinkModel;

    public ConnectionWeightListener(TaskLinkModel taskLinkModel) {
        this.taskLinkModel = taskLinkModel;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                taskLinkModel.setWeight(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
