package com.nightingale.view.editor.common;

import javafx.scene.Node;

/**
 * Created by Nightingale on 16.03.14.
 */
public interface GraphMediator {
    void init();

    void refresh();

    void setDragHandler(Node node);
}
