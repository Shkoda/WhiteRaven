package com.nightingale.view.editor.common.listeners;

import com.nightingale.model.entities.graph.Connection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Nightingale on 16.03.14.
 */
public class ConnectionWeightListener implements ChangeListener<String> {

    private final Connection connection;

    public ConnectionWeightListener(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue != null && !newValue.equals(""))
            try {
                connection.setWeight(Integer.valueOf(newValue));
            }catch (Exception ignored){

            }

    }
}
