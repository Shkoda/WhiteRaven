package com.nightingale.view;

import javafx.scene.layout.Pane;

import java.awt.*;

/**
 * Created by Nightingale on 09.03.14.
 */
public interface ViewablePage {
    String getName();
    boolean isNavigationVisible();
    ViewablePage getPreviousPage();
    ViewablePage getNextPage();
    Pane getView();

}
