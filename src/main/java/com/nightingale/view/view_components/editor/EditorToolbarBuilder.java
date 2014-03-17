package com.nightingale.view.view_components.editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Created by Nightingale on 16.03.14.
 */
public class EditorToolbarBuilder {
    public static Pane build(ButtonBase... buttons) {
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();

        HBox box = new HBox();
        box.setPadding(new Insets(8));
        box.setAlignment(Pos.CENTER);
        box.setSpacing(40);

        for (ButtonBase buttonBase : buttons)
            if (buttonBase != null)
                box.getChildren().add(buttonBase);
        toolBar.getItems().addAll(box);
        borderPane.setCenter(box);

        borderPane.setStyle("-fx-border-color: transparent; ");

        return borderPane;
    }
}
