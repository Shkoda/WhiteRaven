package com.nightingale.view.view_components.editor;

import com.nightingale.model.entities.GraphType;
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
    public static Pane build(GraphType graphType){
        double width;
        double height;

        switch (graphType){
            case MPP:
                width = Config.MPP_CANVAS_WIDTH;
                height = Config.MPP_CANVAS_HEIGHT;
                break;
            case TASK:
                width = Config.TASK_CANVAS_WIDTH;
                height = Config.TASK_CANVAS_HEIGHT;
                break;
            default:
                return null;
        }
        Pane canvas = new Pane();
        canvas.setPrefSize(width, height);
        canvas.setMinSize(width, height);

        GridPane.setHalignment(canvas, HPos.CENTER);
        GridPane.setValignment(canvas, VPos.CENTER);

        canvas.setStyle("-fx-background-color: #ffffff;-fx-border-color: #000000; ");
        return canvas;
    }
}
