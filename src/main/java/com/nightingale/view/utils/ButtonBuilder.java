package com.nightingale.view.utils;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Created by Nightingale on 09.03.14.
 */
public class ButtonBuilder {

    public static Button createButton(String fxId, HPos hPos, VPos vPos) {
        Button button = new Button();
        button.setId(fxId);
        GridPane.setHalignment(button, hPos);
        GridPane.setValignment(button, vPos);
        return button;
    }

    public static Button createButton(String fxId, int size) {
        Button button = new Button();

        button.setMinSize(size, size);
        button.setPrefSize(size, size);
        button.setMaxSize(size, size);

        button.setId(fxId);
        GridPane.setHalignment(button, HPos.CENTER);
        return button;
    }

    public static ToggleButton createButton(String buttonId, ToggleGroup group, int size) {
        ToggleButton button = new ToggleButton();
        button.setId(buttonId);
        button.setPrefSize(size, size);
        button.setMinSize(size, size);
        button.setToggleGroup(group);
        return button;
    }
}
