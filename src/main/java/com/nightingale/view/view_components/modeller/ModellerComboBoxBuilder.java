package com.nightingale.view.view_components.modeller;

import javafx.scene.control.ComboBox;
import static  com.nightingale.view.view_components.modeller.ModellerConstants.*;
/**
 * Created by Nightingale on 19.03.14.
 */
public class ModellerComboBoxBuilder {


    public static ComboBox<String> buildQueueComboBox(){
        ComboBox<String> queueBox = new ComboBox<>();
        queueBox.getItems().addAll(QueueType.QUEUE_2.text,
                QueueType.QUEUE_6.text,
                QueueType.QUEUE_16.text);
        queueBox.setPromptText(QUEUE_PROMT_TEXT);
        return queueBox;
    }

    public static ComboBox<String> buildLoadComboBox(){
        ComboBox<String> loadBox = new ComboBox<>();
        loadBox.getItems().addAll(LoadingType.ALGORITHM_3.text,
                LoadingType.ALGORITHM_5.text);
        loadBox.setPromptText(LOADING_PROMT_TEXT);
        return loadBox;
    }

}
