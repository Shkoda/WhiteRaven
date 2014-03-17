package com.nightingale.view.editor.common.listeners;

import com.nightingale.model.entities.Vertex;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class VertexWeightChangeListener implements ChangeListener<String>{

    private final Vertex vertex;

    public VertexWeightChangeListener(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                vertex.setWeight(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
