package com.nightingale.view.editor.proscessor_editor_page.listeners;

import com.nightingale.model.mpp.elements.ProcessorModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ProcessorPerformanceChangeListener implements ChangeListener<String>{

    private final ProcessorModel processorModel;

    public ProcessorPerformanceChangeListener(ProcessorModel processorModel) {
        this.processorModel = processorModel;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                processorModel.setWeight(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
