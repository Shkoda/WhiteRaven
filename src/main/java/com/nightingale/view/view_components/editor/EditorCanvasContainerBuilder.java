package com.nightingale.view.view_components.editor;

import javafx.scene.control.ScrollPane;

/**
 * Created by Nightingale on 16.03.14.
 */
public class EditorCanvasContainerBuilder {
    public static ScrollPane build() {
        ScrollPane canvasContainer = new ScrollPane();
        canvasContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasContainer.setStyle("-fx-border-color: rgb(204, 204, 204); ");
        return canvasContainer;
    }
}
