package com.nightingale.view.view_components.modeller;

import javafx.scene.control.*;
import javafx.scene.text.*;


/**
 * Created by Nightingale on 20.03.14.
 */
public class ModellerQueueTextAreaBuilder {
    public static TextArea build() {
        TextArea textField = new TextArea("There will be queue...");
        textField.setEditable(false);
        textField.setFont(javafx.scene.text.Font.font("Segoe"));
        return textField;
    }
}
