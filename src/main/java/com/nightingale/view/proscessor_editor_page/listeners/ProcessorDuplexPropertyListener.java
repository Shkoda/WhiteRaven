package com.nightingale.view.proscessor_editor_page.listeners;

import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ProcessorDuplexPropertyListener  implements ChangeListener<Boolean> {

    private final ProcessorModel processorModel;

    public ProcessorDuplexPropertyListener(ProcessorModel processorModel) {
        this.processorModel = processorModel;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        processorModel.setFullDuplexEnabled(newValue);
    }
}