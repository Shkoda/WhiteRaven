package com.nightingale.view.proscessor_editor_page.mpp;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;

/**
 * Created by Nightingale on 10.03.14.
 */
public interface IMppMediator {
    void init();

    void refresh();

    void setDragHandler(int processorId, Node node);

    Dimension2D getCanvasDimension();

}
