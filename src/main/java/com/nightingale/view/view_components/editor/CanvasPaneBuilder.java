package com.nightingale.view.view_components.editor;

import com.nightingale.view.config.Config;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;

/**
 * Created by Nightingale on 16.03.14.
 */
public class CanvasPaneBuilder {
    public static Pane build(){
      Pane  canvas = PaneBuilder.create()
                .prefHeight(Config.CANVAS_HEIGHT)
                .prefWidth(Config.CANVAS_WIDTH)
                .maxHeight(Config.CANVAS_HEIGHT)
                .maxWidth(Config.CANVAS_WIDTH)
                .build();

        GridPane.setHalignment(canvas, HPos.CENTER);
        GridPane.setValignment(canvas, VPos.CENTER);

        canvas.setStyle("-fx-background-color: #ffffff;-fx-border-color: #000000; ");
        return canvas;
    }
}
