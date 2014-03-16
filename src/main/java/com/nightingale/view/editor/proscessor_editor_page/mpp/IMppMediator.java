package com.nightingale.view.editor.proscessor_editor_page.mpp;

import javafx.scene.Node;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface IMppMediator {
    void init();

    void refresh();

    void setDragHandler(Node node);
}
