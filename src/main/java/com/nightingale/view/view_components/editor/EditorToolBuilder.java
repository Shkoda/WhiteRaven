package com.nightingale.view.view_components.editor;

import com.nightingale.view.view_components.common.ButtonBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

/**
 * Created by Nightingale on 16.03.14.
 */
public class EditorToolBuilder {
    public static ToggleButton build(String buttonId, ToggleGroup group, int size, String tooltipText) {
        ToggleButton button = new ToggleButton();
        button.setId(buttonId);
        button.setPrefSize(size, size);
        button.setMinSize(size, size);
        button.setToggleGroup(group);

        Tooltip tooltip = new Tooltip();
        tooltip.setText(tooltipText);

        button.setTooltip(tooltip);

        return button;
    }

    public static Button build(String fxId, int size, String tooltipText) {
        return ButtonBuilder.createButton(fxId, size, tooltipText);
    }
}
