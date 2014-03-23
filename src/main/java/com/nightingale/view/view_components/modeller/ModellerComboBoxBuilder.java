package com.nightingale.view.view_components.modeller;

import javafx.scene.control.ComboBox;
import static  com.nightingale.view.view_components.modeller.ModellerConstants.*;
/**
 * Created by Nightingale on 19.03.14.
 */
public class ModellerComboBoxBuilder {


    public static ComboBox<String> buildQueueComboBox(){
        ComboBox<String> queueBox = new ComboBox<>();
        queueBox.getItems().addAll(FIRST_QUEUE_ALGORITHM_TEXT,
                SECOND_QUEUE_ALGORITHM_TEXT,
                THIRD_QUEUE_ALGORITHM_TEXT);
        queueBox.setPromptText(QUEUE_PROMT_TEXT);
        return queueBox;
    }

    public static ComboBox<String> buildLoadComboBox(){
        ComboBox<String> loadBox = new ComboBox<>();
        loadBox.getItems().addAll(FIRST_LOADING_ALGORITHM_TEXT,
                SECOND_LOADING_ALGORITHM_TEXT);
        loadBox.setPromptText(LOADING_PROMT_TEXT);
        return loadBox;
    }

}
