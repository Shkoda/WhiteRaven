package com.nightingale.view.editor.common.listeners;

import com.nightingale.model.entities.graph.Informative;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class WeightChangeListener implements ChangeListener<String>{

    private final Informative informative;

    public WeightChangeListener(Informative informative) {
        this.informative = informative;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                informative.setWeight(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
