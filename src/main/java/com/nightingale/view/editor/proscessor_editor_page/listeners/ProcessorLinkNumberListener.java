package com.nightingale.view.editor.proscessor_editor_page.listeners;

import com.nightingale.model.entities.graph.Informative;
import com.nightingale.model.mpp.ProcessorModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 30.03.2014.
 */
public class ProcessorLinkNumberListener implements ChangeListener<String>{
    private final ProcessorModel processorModel;

    public ProcessorLinkNumberListener(ProcessorModel processorModel) {
        this.processorModel = processorModel;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                processorModel.setPhysicalLinkNumber(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
