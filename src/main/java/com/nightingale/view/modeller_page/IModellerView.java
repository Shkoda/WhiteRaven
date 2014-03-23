package com.nightingale.view.modeller_page;

import com.nightingale.view.ViewablePage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface IModellerView extends ViewablePage {

    void setQueueComboBox(ComboBox queueComboBox);

    TextArea getQueueTextArea();
}
