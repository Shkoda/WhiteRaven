package com.nightingale.view.editor.proscessor_editor_page.listeners;

import com.nightingale.model.mpp.elements.ProcessorLinkModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ProcessorLinkWeightListener implements ChangeListener<String> {

    private final ProcessorLinkModel processorLinkModel;

    public ProcessorLinkWeightListener(ProcessorLinkModel processorLinkModel) {
        this.processorLinkModel = processorLinkModel;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                processorLinkModel.setWeight(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
